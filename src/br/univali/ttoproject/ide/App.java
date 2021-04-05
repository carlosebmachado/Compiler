package br.univali.ttoproject.ide;

import br.univali.ttoproject.compiler.Compiler;
import br.univali.ttoproject.ide.components.*;
import br.univali.ttoproject.ide.components.MenuBar;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;
import javax.swing.text.Utilities;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.function.Supplier;

public class App extends JFrame {

    private JPanel panelMain;

    private final JTextArea taEdit;
    private final JTextArea taConsole;
    private final JLabel lblLnCol;

    private FileTTO file;

    private boolean newFile = true;
    private boolean savedFile = true;

    private boolean compiled = false;
    private boolean running = false;

    private boolean allowConsoleInput = false;
    private int allowedCaretPosition;
    private int initialCaretPosition;

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException |
                InstantiationException |
                IllegalAccessException |
                UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(App.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        /* Create and display the form */
        EventQueue.invokeLater(() -> {
            try {
                new App();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public App() {
        // Inicialização
        file = new FileTTO();

        // Interface
        setTitle("Compiler");
        setIconImage(Toolkit.getDefaultToolkit().getImage(App.class.getResource("/img/icon.png")));
        setContentPane(panelMain);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setBounds(0, 0, 800, 450);
        setMinimumSize(new Dimension(400, 225));
        setLocationRelativeTo(null);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                fExit();
            }
        });

        // Components

        // menu methods
        Supplier<?>[] menuMethods = {
                this::fNew, this::fOpen, this::fSave, this::fSaveAs, this::fSettings, this::fExit, this::fCut,
                this::fCopy, this::fPaste, this::fCompile, this::fRun, this::fAbout, this::fHelp
        };

        // menu bar
        setJMenuBar(new MenuBar(menuMethods));
        // tool bar
        add(new ToolBar(menuMethods), BorderLayout.NORTH);
        // status bar
        var statusBar = new StatusBar();
        lblLnCol = new JLabel("Ln 1, Col 1");
        statusBar.add(lblLnCol);
        panelMain.add(statusBar, BorderLayout.SOUTH);

        JSplitPane splitPane = new JSplitPane();
        splitPane.setResizeWeight(0.8);
        splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
        panelMain.add(splitPane, BorderLayout.CENTER);

        taConsole = new JTextArea();
        taConsole.setTabSize(4);
        taConsole.setFocusTraversalKeysEnabled(false);
        taConsole.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                cHandleKey(e);
            }
        });
        JScrollPane scpConsole = new JScrollPane(taConsole);
        splitPane.setRightComponent(scpConsole);

        taEdit = new JTextArea();
        taEdit.setTabSize(4);
        taEdit.setFont(new Font("Consolas", Font.PLAIN, 14));
        taEdit.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                updateFile();
            }
        });
        taEdit.addCaretListener(e -> updateLCLabel());

        JScrollPane scpEdit = new JScrollPane(taEdit);
        splitPane.setLeftComponent(scpEdit);

        TextLineNumber tln = new TextLineNumber(taEdit);
        scpEdit.setRowHeaderView(tln);
        scpEdit.setViewportView(taEdit);

        updateSettings();

        setVisible(true);
    }

    /*******************************************************************************************************************
     * Actions
     ******************************************************************************************************************/

    public boolean fNew() {
        if (cancelSaveFileOp()) return false;

        file = new FileTTO();
        taEdit.setText("");
        resetControlVars();
        newFile = true;
        setTitle("Compiler");

        return true;
    }

    public boolean fOpen() {
        if (cancelSaveFileOp()) return false;

        var fullPath = getFilePath(false);
        if (fullPath.equals("")) return false;

        file = new FileTTO(fullPath);
        resetControlVars();
        setTitle("Compiler - " + file.getName());
        taEdit.setText(file.load());

        return true;
    }

    public boolean fSave() {
        if (newFile) {
            return fSaveAs();
        } else if (!savedFile) {
            resetFileVars();
            setTitle(getTitle().substring(0, getTitle().length() - 2));
            file.save(taEdit.getText());
        }
        return true;
    }

    public boolean fSaveAs() {
        if (!savedFile) {
            var fullPath = getFilePath(true);
            if (fullPath.equals("")) return false;

            file = new FileTTO(fullPath);
            resetFileVars();
            setTitle("Compiler - " + file.getName());
            file.save(taEdit.getText());
        }
        return true;
    }

    public boolean fSettings() {
        new Settings(this);

        return true;
    }

    public boolean fExit() {
        if (savedFile) {
            System.exit(0);
        } else if (verifySaveFile()) {
            System.exit(0);
        }

        return true;
    }

    public boolean fCut() {
        taEdit.cut();

        return true;
    }

    public boolean fCopy() {
        taEdit.copy();

        return true;
    }

    public boolean fPaste() {
        taEdit.paste();

        return true;
    }

    public boolean fCompile() {
        if (taEdit.getText().isEmpty()) {
            JOptionPane.showMessageDialog(
                    null,
                    "Your file is empty.",
                    "Warning",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (!fSave()) return false;

        compiled = true;
        taConsole.setText(new Compiler().build(new StringReader(taEdit.getText())));

        return true;
    }

    public boolean fRun() {
        if (!compiled) {
            JOptionPane.showMessageDialog(
                    null,
                    "Please, compile your file before running.",
                    "Warning",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        running = true;

        return true;
    }

    public boolean fAbout() {
        JOptionPane.showMessageDialog(
                null,
                "Authors: Carlos E. B. Machado, Herikc Brecher and Bruno F. Francisco.",
                "About",
                JOptionPane.INFORMATION_MESSAGE);

        return true;
    }

    public boolean fHelp() {
        new ShowHelp();

        return true;
    }

    /*******************************************************************************************************************
     * UI controls
     ******************************************************************************************************************/
    private void cHandleKey(KeyEvent e) {
        if (allowConsoleInput) {
            cUserInput(e);
        } else {
            e.consume();
        }
    }

    private void cInit() {
        allowConsoleInput = true;
        allowedCaretPosition = taConsole.getCaretPosition();
        initialCaretPosition = allowedCaretPosition;
    }

    private void cStop() {
        allowConsoleInput = false;
    }

    private void cReset() {
        taConsole.setText("");
    }

    private void cAddContent(String content) {
        taConsole.setText(taConsole.getText() + content);
    }

    private void cUserInput(KeyEvent e) {
        taConsole.requestFocusInWindow();

        var keyChar = e.getKeyChar();
        var curCaretPosition = taConsole.getCaretPosition();

        if (keyChar == '\b') {
            if (curCaretPosition > initialCaretPosition) {
                allowedCaretPosition--;
            } else {
                e.consume();
            }
            return;
        }
        if (keyChar == '\n') {
            cStop();
            return;
        }
        if (keyChar == '\t') {
            e.consume();
            return;
        }

        if (allowedCaretPosition != curCaretPosition) {
            e.consume();
        } else {
            allowedCaretPosition++;
        }
    }

    private void updateLCLabel() {
        int caretPos = taEdit.getCaretPosition();
        int rows = caretPos == 0 ? 1 : 0;
        int cols;

        for (int offset = caretPos; offset > 0; ) {
            try {
                offset = Utilities.getRowStart(taEdit, offset) - 1;
            } catch (BadLocationException e1) {
                e1.printStackTrace();
            }
            rows++;
        }

        int offset = 0;
        try {
            offset = Utilities.getRowStart(taEdit, caretPos);
        } catch (BadLocationException e1) {
            e1.printStackTrace();
        }
        cols = caretPos - offset + 1;

        lblLnCol.setText("Ln " + rows + ", Col " + cols);
    }

    private void updateFile() {
        if (savedFile) {
            setTitle(getTitle() + "*");
        }
        savedFile = false;
    }

    /*******************************************************************************************************************
     * Auxiliary functions
     ******************************************************************************************************************/

    public void updateSettings() {
        taEdit.setTabSize(Settings.TAB_SIZE);
    }

    public void resetControlVars() {
        resetFileVars();
        compiled = false;
        running = false;
    }

    public void resetFileVars() {
        newFile = false;
        savedFile = true;
    }

    public boolean verifySaveFile() {
        int result = JOptionPane.showConfirmDialog(
                null,
                "Would you like to save the file?",
                "Save",
                JOptionPane.YES_NO_CANCEL_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            return fSave();
        }
        return result != JOptionPane.CANCEL_OPTION;
    }

    public boolean cancelSaveFileOp() {
        if (!savedFile) {
            return !verifySaveFile();
        }
        return false;
    }

    public String getFilePath(boolean save) {
        var fullPath = "";
        var optionString = save ? "save" : "open";
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Specify a file to " + optionString);
        fileChooser.setFileFilter(new FileNameExtensionFilter("2021.1 Files", "tto", "2021.1"));
        if (save) {
            fileChooser.setSelectedFile(new File(file.getName()));
        }

        int userSelection;
        boolean existisVerDone = false;

        do {
            if (save) {
                userSelection = fileChooser.showSaveDialog(null);
            } else {
                userSelection = fileChooser.showOpenDialog(null);
            }

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                if (save) {
                    if (fileToSave.exists()) {
                        int result = JOptionPane.showConfirmDialog(
                                null,
                                "A file with that name already exists. Would you like to overwrite?",
                                "Overwrite",
                                JOptionPane.YES_NO_OPTION);
                        if (result == JOptionPane.YES_OPTION) {
                            existisVerDone = true;
                        }
                    } else {
                        existisVerDone = true;
                    }
                } else {
                    existisVerDone = true;
                }
                fullPath = fileToSave.getAbsolutePath();
            } else {
                return "";
            }
        } while (!existisVerDone);

        if (fullPath.length() >= 4 && !fullPath.endsWith(".tto")) {
            fullPath += ".tto";
        } else if (fullPath.length() < 4) {
            fullPath += ".tto";
        }

        return fullPath;
    }
}
