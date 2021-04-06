package br.univali.ttoproject.ide;

import br.univali.ttoproject.compiler.Compiler;
import br.univali.ttoproject.ide.components.*;
import br.univali.ttoproject.ide.components.Console;
import br.univali.ttoproject.ide.components.MenuBar;
import br.univali.ttoproject.ide.util.Debug;

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
    private final Console console;
    private final JLabel lblLnCol;
    private final JLabel lblTabSize;

    private FileTTO file;

    private boolean newFile = true;
    private boolean savedFile = true;

    private boolean compiled = false;
    private boolean running = false;

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
                mExit();
            }
        });

        // Adding components

        // menu methods
        Supplier<?>[] menuMethods = {
                this::mNew, this::mOpen, this::mSave, this::mSaveAs, this::mSettings, this::mExit, this::mCut,
                this::mCopy, this::mPaste, this::mCompile, this::mRun, this::mAbout, this::mHelp};

        // menu bar
        setJMenuBar(new MenuBar(menuMethods));
        // tool bar
        add(new ToolBar(menuMethods), BorderLayout.NORTH);
        // status bar
        var statusBar = new StatusBar();
        lblLnCol = new JLabel("Ln 1, Col 1");
        lblTabSize = new JLabel(SettingsForm.TAB_SIZE + " spaces");
        statusBar.add(lblLnCol);
        statusBar.add(lblTabSize);
        panelMain.add(statusBar, BorderLayout.SOUTH);
        // split pane (up: editor down: console)
        var splitPane = new JSplitPane();
        splitPane.setResizeWeight(0.8);
        splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
        panelMain.add(splitPane, BorderLayout.CENTER);
        // console
        console = new Console(this::getUserInput);
        var scpConsole = new JScrollPane(console);
        splitPane.setRightComponent(scpConsole);
        // editor
        taEdit = new JTextArea();
        taEdit.setTabSize(4);
        if (System.getProperty("os.name").substring(0, 3).equalsIgnoreCase("win")) {
            taEdit.setFont(new Font("Consolas", Font.PLAIN, 14));
        } else {
            taEdit.setFont(new Font("FreeMono", Font.PLAIN, 15));
        }
        taEdit.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                updateFileEdit();
            }
        });
        taEdit.addCaretListener(e -> updateLCLabel());

        var scpEdit = new JScrollPane(taEdit);
        splitPane.setLeftComponent(scpEdit);

        var tln = new TextLineNumber(taEdit);
        scpEdit.setRowHeaderView(tln);
        scpEdit.setViewportView(taEdit);

        updateSettings();

        setVisible(true);
    }

    /*******************************************************************************************************************
     * Actions
     ******************************************************************************************************************/

    public boolean mNew() {
        if (cancelSaveFileOp()) return false;

        file = new FileTTO();
        taEdit.setText("");
        resetControlVars();
        newFile = true;
        setTitle("Compiler");

        return true;
    }

    public boolean mOpen() {
        if (cancelSaveFileOp()) return false;

        var fullPath = getFilePath(false);
        if (fullPath.equals("")) return false;

        file = new FileTTO(fullPath);
        resetControlVars();
        setTitle("Compiler - " + file.getName());
        taEdit.setText(file.load());

        return true;
    }

    public boolean mSave() {
        if (newFile) {
            return mSaveAs();
        } else if (!savedFile) {
            resetFileVars();
            setTitle(getTitle().substring(0, getTitle().length() - 2));
            file.save(taEdit.getText());
        }
        return true;
    }

    public boolean mSaveAs() {
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

    public boolean mSettings() {
        new SettingsForm(this);

        return true;
    }

    public boolean mExit() {
        if (savedFile) {
            System.exit(0);
        } else if (verifySaveFile()) {
            System.exit(0);
        }

        return true;
    }

    public boolean mCut() {
        taEdit.cut();

        return true;
    }

    public boolean mCopy() {
        taEdit.copy();

        return true;
    }

    public boolean mPaste() {
        taEdit.paste();

        return true;
    }

    public boolean mCompile() {
        if (taEdit.getText().isEmpty()) {
            JOptionPane.showMessageDialog(
                    null,
                    "Your file is empty.",
                    "Warning",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (!mSave()) return false;

        compiled = true;
        console.setText(new Compiler().build(new StringReader(taEdit.getText())));

        return true;
    }

    public boolean mRun() {
//        if (!compiled) {
//            JOptionPane.showMessageDialog(
//                    null,
//                    "Please, compile your file before running.",
//                    "Warning",
//                    JOptionPane.ERROR_MESSAGE);
//            return false;
//        }
//        running = true;

        console.initDataEntry("Digite: ");

        return true;
    }

    public boolean mAbout() {
        JOptionPane.showMessageDialog(
                null,
                "Authors: Carlos E. B. Machado, Herikc Brecher and Bruno F. Francisco.",
                "About",
                JOptionPane.INFORMATION_MESSAGE);

        return true;
    }

    public boolean mHelp() {
        new HelpForm();

        return true;
    }

    /*******************************************************************************************************************
     * UI controls
     ******************************************************************************************************************/
    private void updateLCLabel() {
        int caretPos = taEdit.getCaretPosition();
        long rows;
        long cols;

        rows = taEdit.getText().substring(0, caretPos).chars().filter(ch -> ch == '\n').count() + 1;

        int offset = 0;
        try {
            offset = Utilities.getRowStart(taEdit, caretPos);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
        cols = caretPos - offset + 1;

        lblLnCol.setText("Ln " + rows + ", Col " + cols);
    }

    private void updateFileEdit() {
        if (savedFile) {
            setTitle(getTitle() + "*");
        }
        savedFile = false;
    }

    /*******************************************************************************************************************
     * Auxiliary functions
     ******************************************************************************************************************/

    public void getUserInput(String entry) {
        Debug.print(entry);
    }

    public void updateSettings() {
        taEdit.setTabSize(SettingsForm.TAB_SIZE);
        lblTabSize.setText(SettingsForm.TAB_SIZE + " spaces");
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
            return mSave();
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
