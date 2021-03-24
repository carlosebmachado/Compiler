package br.univali.ttoproject.ide;

import br.univali.ttoproject.compiler.Compiler;
import br.univali.ttoproject.ide.components.ShowHelp;
import br.univali.ttoproject.ide.components.TextLineNumber;
import br.univali.ttoproject.ide.core.FileTTO;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;
import javax.swing.text.Utilities;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.StringReader;

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
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu mnFile = new JMenu("File");
        menuBar.add(mnFile);

        JMenuItem mntmNew = new JMenuItem("New");
        mntmNew.addActionListener(e -> fNew());
        mntmNew.setIcon(new ImageIcon(App.class.getResource("/img/New File.png")));
        mnFile.add(mntmNew);

        JMenuItem mntmOpen = new JMenuItem("Open");
        mntmOpen.addActionListener(e -> fOpen());
        mntmOpen.setIcon(new ImageIcon(App.class.getResource("/img/Open Project.png")));
        mnFile.add(mntmOpen);

        JMenuItem mntmSave = new JMenuItem("Save");
        mntmSave.addActionListener(e -> fSave());
        mntmSave.setIcon(new ImageIcon(App.class.getResource("/img/Save File.png")));
        mnFile.add(mntmSave);

        JMenuItem mntmSaveAs = new JMenuItem("Save as...");
        mntmSaveAs.addActionListener(e -> fSaveAs());
        mnFile.add(mntmSaveAs);

        JMenuItem mntmExit = new JMenuItem("Exit");
        mntmExit.addActionListener(e -> fExit());
        mnFile.add(mntmExit);

        JMenu mnEdit = new JMenu("Edit");
        menuBar.add(mnEdit);

        JMenuItem mntmCut = new JMenuItem("Cut");
        mntmCut.addActionListener(e -> fCut());
        mntmCut.setIcon(new ImageIcon(App.class.getResource("/img/Cut.PNG")));
        mnEdit.add(mntmCut);

        JMenuItem mntmCopy = new JMenuItem("Copy");
        mntmCopy.addActionListener(e -> fCopy());
        mntmCopy.setIcon(new ImageIcon(App.class.getResource("/img/Copy.PNG")));
        mnEdit.add(mntmCopy);

        JMenuItem mntmPaste = new JMenuItem("Paste");
        mntmPaste.addActionListener(e -> fPaste());
        mntmPaste.setIcon(new ImageIcon(App.class.getResource("/img/Paste.png")));
        mnEdit.add(mntmPaste);

        JMenu mnBuild = new JMenu("Build");
        menuBar.add(mnBuild);

        JMenuItem mntmCompile = new JMenuItem("Compile");
        mntmCompile.addActionListener(e -> fCompile());
        mntmCompile.setIcon(new ImageIcon(App.class.getResource("/img/Cog.png")));
        mnBuild.add(mntmCompile);

        JMenuItem mntmRun = new JMenuItem("Run");
        mntmRun.addActionListener(e -> fRun());
        mntmRun.setIcon(new ImageIcon(App.class.getResource("/img/Run .PNG")));
        mnBuild.add(mntmRun);

        JMenu mnAbout = new JMenu("Help");
        menuBar.add(mnAbout);

        JMenuItem mntmNewMenuItem = new JMenuItem("Show help");
        mntmNewMenuItem.addActionListener(e -> fHelp());
        mnAbout.add(mntmNewMenuItem);

        JMenuItem mntmNewMenuItem_1 = new JMenuItem("About");
        mntmNewMenuItem_1.addActionListener(e -> fAbout());
        mnAbout.add(mntmNewMenuItem_1);


        JToolBar toolBar = new JToolBar();
        panelMain.add(toolBar, BorderLayout.NORTH);

        JButton btnNew = new JButton("");
        btnNew.addActionListener(e -> fNew());
        btnNew.setIcon(new ImageIcon(App.class.getResource("/img/New File.png")));
        toolBar.add(btnNew);

        JButton btnOpen = new JButton("");
        btnOpen.addActionListener(e -> fOpen());
        btnOpen.setIcon(new ImageIcon(App.class.getResource("/img/Open Project.png")));
        toolBar.add(btnOpen);

        JButton btnSave = new JButton("");
        btnSave.addActionListener(e -> fSave());
        btnSave.setIcon(new ImageIcon(App.class.getResource("/img/Save File.png")));
        toolBar.add(btnSave);

        JSeparator separator = new JSeparator();
        separator.setMaximumSize(new Dimension(4, 32767));
        separator.setOrientation(SwingConstants.VERTICAL);
        toolBar.add(separator);

        JButton btnCut = new JButton("");
        btnCut.addActionListener(e -> fCut());
        btnCut.setIcon(new ImageIcon(App.class.getResource("/img/Cut.PNG")));
        toolBar.add(btnCut);

        JButton btnCopy = new JButton("");
        btnCopy.addActionListener(e -> fCopy());
        btnCopy.setIcon(new ImageIcon(App.class.getResource("/img/Copy.PNG")));
        toolBar.add(btnCopy);

        JButton btnPaste = new JButton("");
        btnPaste.addActionListener(e -> fPaste());
        btnPaste.setIcon(new ImageIcon(App.class.getResource("/img/Paste.png")));
        toolBar.add(btnPaste);

        JSeparator separator_1 = new JSeparator();
        separator_1.setOrientation(SwingConstants.VERTICAL);
        separator_1.setMaximumSize(new Dimension(4, 32767));
        toolBar.add(separator_1);

        JButton btnBuild = new JButton("");
        btnBuild.addActionListener(e -> fCompile());
        btnBuild.setIcon(new ImageIcon(App.class.getResource("/img/Cog.png")));
        toolBar.add(btnBuild);

        JButton btnRun = new JButton("");
        btnRun.addActionListener(e -> fRun());
        btnRun.setIcon(new ImageIcon(App.class.getResource("/img/Run .PNG")));
        toolBar.add(btnRun);

        JPanel panelStatusBar = new JPanel();
        panelStatusBar.setMinimumSize(new Dimension(10, 16));
        panelMain.add(panelStatusBar, BorderLayout.SOUTH);
        panelStatusBar.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));

        JSeparator separator_2 = new JSeparator();
        separator_2.setPreferredSize(new Dimension(5, 14));
        separator_2.setMaximumSize(new Dimension(5, 32767));
        separator_2.setOrientation(SwingConstants.VERTICAL);
        panelStatusBar.add(separator_2);

        lblLnCol = new JLabel("Ln 1, Col 1");
        panelStatusBar.add(lblLnCol);

        JSplitPane splitPane = new JSplitPane();
        splitPane.setResizeWeight(0.8);
        splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
        panelMain.add(splitPane, BorderLayout.CENTER);

        taConsole = new JTextArea();
        taConsole.setTabSize(4);
        taConsole.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                cHandleKey(e);
                //e.consume();
            }
            @Override
            public void keyTyped(KeyEvent e) {
                cHandleKey(e);
                //e.consume();
            }
        });
        JScrollPane scrollPaneConsole = new JScrollPane(taConsole);
        splitPane.setRightComponent(scrollPaneConsole);

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

        JScrollPane scrollPane = new JScrollPane(taEdit);
        splitPane.setLeftComponent(scrollPane);

        TextLineNumber tln = new TextLineNumber(taEdit);
        scrollPane.setRowHeaderView(tln);
        scrollPane.setViewportView(taEdit);

        setVisible(true);
    }

    /*******************************************************************************************************************
     * Actions
     ******************************************************************************************************************/

    public void fNew() {
        if (cancelSaveFileOp()) return;

        file = new FileTTO();
        taEdit.setText("");
        resetControlVars();
        newFile = true;
        setTitle("Compiler");
    }

    public void fOpen() {
        if (cancelSaveFileOp()) return;

        var fullPath = getFilePath(false);
        if (fullPath.equals("")) return;

        file = new FileTTO(fullPath);
        resetControlVars();
        setTitle("Compiler - " + file.getName());
        taEdit.setText(file.load());
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
        var fullPath = getFilePath(true);
        if (fullPath.equals("")) return false;

        file = new FileTTO(fullPath);
        resetFileVars();
        setTitle("Compiler - " + file.getName());
        file.save(taEdit.getText());
        return true;
    }

    public void fExit() {
        if (savedFile) {
            System.exit(0);
        } else if (verifySaveFile()) {
            System.exit(0);
        }
    }

    public void fCut() {
        taEdit.cut();
    }

    public void fCopy() {
        taEdit.copy();
    }

    public void fPaste() {
        taEdit.paste();
    }

    public void fCompile() {
        if (taEdit.getText().isEmpty()) {
            JOptionPane.showMessageDialog(
                    null,
                    "Your file is empty.",
                    "Warning",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!fSave()) return;

        compiled = true;
        taConsole.setText(new Compiler().build(new StringReader(taEdit.getText())));

        // test
//        cAddContent("\nDigite: ");
//        allowConsoleInput = true;
//        allowedCaretPosition = taConsole.getCaretPosition();
    }

    public void fRun() {
        if (!compiled) {
            JOptionPane.showMessageDialog(
                    null,
                    "Please, compile your file before running.",
                    "Warning",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        running = true;
    }

    public void fAbout() {
        JOptionPane.showMessageDialog(
                null,
                "Authors: Carlos E. B. Machado, Herikc Brecher and Bruno.",
                "About",
                JOptionPane.INFORMATION_MESSAGE);
    }

    public void fHelp() {
        new ShowHelp();
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

    private void cReset() {
        taConsole.setText("");
    }

    private void cAddContent(String content) {
        taConsole.setText(taConsole.getText() + content);
    }

    private void cUserInput(KeyEvent e) {
        //taConsole.grabFocus();
        taConsole.requestFocusInWindow();

        var curCaretPosition = taConsole.getCaretPosition();

        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            allowConsoleInput = false;
        } else if (allowedCaretPosition != curCaretPosition){
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
