package br.univali.ttoproject.ide;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.Utilities;

import br.univali.ttoproject.compiler.Compiler;
import br.univali.ttoproject.ide.components.ShowHelp;
import br.univali.ttoproject.ide.components.TextLineNumber;
import br.univali.ttoproject.ide.core.FileTTO;

import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JMenu;
import java.io.File;
import java.io.StringReader;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JToolBar;
import javax.swing.ImageIcon;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JLabel;
import java.awt.FlowLayout;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JFileChooser;
import javax.swing.JTextPane;
import javax.swing.JScrollPane;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.event.CaretListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.event.CaretEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Font;
import java.awt.Toolkit;

public class App extends JFrame {

	private static final long serialVersionUID = 5107264097912675169L;

	private JPanel contentPane;
	private JTextArea textArea;

	private FileTTO file;
	private JLabel lblLnCol;

	private boolean newFile = true;
	private boolean savedFile = true;

	private boolean compiled = false;
	private boolean running = false;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel(
                UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(App.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(App.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(App.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(App.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        /* Create and display the form */
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					App app = new App();
					app.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
    }

	/**
	 * Create the frame.
	 */
	public App() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(App.class.getResource("/img/icon.png")));
		// Inicialização

		file = new FileTTO();

		// Interface

		setTitle("Compiler");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(0, 0, 800, 450);
		setMinimumSize(new Dimension(400, 225));
		setLocationRelativeTo(null);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if (savedFile)
					System.exit(0);
				else if (verifySaveFile())
					System.exit(0);
			}
		});

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		JMenuItem mntmNew = new JMenuItem("New");
		mntmNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fNew();
			}
		});
		mntmNew.setIcon(new ImageIcon(App.class.getResource("/img/New File.png")));
		mnFile.add(mntmNew);

		JMenuItem mntmOpen = new JMenuItem("Open");
		mntmOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fOpen();
			}
		});
		mntmOpen.setIcon(new ImageIcon(App.class.getResource("/img/Open Project.png")));
		mnFile.add(mntmOpen);

		JMenuItem mntmSave = new JMenuItem("Save");
		mntmSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fSave();
			}
		});
		mntmSave.setIcon(new ImageIcon(App.class.getResource("/img/Save File.png")));
		mnFile.add(mntmSave);

		JMenuItem mntmSaveAs = new JMenuItem("Save as...");
		mntmSaveAs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fSaveAs();
			}
		});
		mnFile.add(mntmSaveAs);

		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (savedFile)
					System.exit(0);
				else if (verifySaveFile())
					System.exit(0);
			}
		});
		mnFile.add(mntmExit);

		JMenu mnEdit = new JMenu("Edit");
		menuBar.add(mnEdit);

		JMenuItem mntmCut = new JMenuItem("Cut");
		mntmCut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fCut();
			}
		});
		mntmCut.setIcon(new ImageIcon(App.class.getResource("/img/Cut.PNG")));
		mnEdit.add(mntmCut);

		JMenuItem mntmCopy = new JMenuItem("Copy");
		mntmCopy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fCopy();
			}
		});
		mntmCopy.setIcon(new ImageIcon(App.class.getResource("/img/Copy.PNG")));
		mnEdit.add(mntmCopy);

		JMenuItem mntmPaste = new JMenuItem("Paste");
		mntmPaste.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fPaste();
			}
		});
		mntmPaste.setIcon(new ImageIcon(App.class.getResource("/img/Paste.png")));
		mnEdit.add(mntmPaste);

		JMenu mnBuild = new JMenu("Build");
		menuBar.add(mnBuild);

		JMenuItem mntmCompile = new JMenuItem("Compile");
		mntmCompile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fCompile();
			}
		});
		mntmCompile.setIcon(new ImageIcon(App.class.getResource("/img/Cog.png")));
		mnBuild.add(mntmCompile);

		JMenuItem mntmRun = new JMenuItem("Run");
		mntmRun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fRun();
			}
		});
		mntmRun.setIcon(new ImageIcon(App.class.getResource("/img/Run .PNG")));
		mnBuild.add(mntmRun);

		JMenu mnAbout = new JMenu("Help");
		menuBar.add(mnAbout);

		JMenuItem mntmNewMenuItem = new JMenuItem("Show help");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fHelp();
			}
		});
		mnAbout.add(mntmNewMenuItem);

		JMenuItem mntmNewMenuItem_1 = new JMenuItem("About");
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fAbout();
			}
		});
		mnAbout.add(mntmNewMenuItem_1);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JToolBar toolBar = new JToolBar();
		contentPane.add(toolBar, BorderLayout.NORTH);

		JButton btnNew = new JButton("");
		btnNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fNew();
			}
		});
		btnNew.setIcon(new ImageIcon(App.class.getResource("/img/New File.png")));
		toolBar.add(btnNew);

		JButton btnOpen = new JButton("");
		btnOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fOpen();
			}
		});
		btnOpen.setIcon(new ImageIcon(App.class.getResource("/img/Open Project.png")));
		toolBar.add(btnOpen);

		JButton btnSave = new JButton("");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fSave();
			}
		});
		btnSave.setIcon(new ImageIcon(App.class.getResource("/img/Save File.png")));
		toolBar.add(btnSave);

		JSeparator separator = new JSeparator();
		separator.setMaximumSize(new Dimension(4, 32767));
		separator.setOrientation(SwingConstants.VERTICAL);
		toolBar.add(separator);

		JButton btnCut = new JButton("");
		btnCut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fCut();
			}
		});
		btnCut.setIcon(new ImageIcon(App.class.getResource("/img/Cut.PNG")));
		toolBar.add(btnCut);

		JButton btnCopy = new JButton("");
		btnCopy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fCopy();
			}
		});
		btnCopy.setIcon(new ImageIcon(App.class.getResource("/img/Copy.PNG")));
		toolBar.add(btnCopy);

		JButton btnPaste = new JButton("");
		btnPaste.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fPaste();
			}
		});
		btnPaste.setIcon(new ImageIcon(App.class.getResource("/img/Paste.png")));
		toolBar.add(btnPaste);

		JSeparator separator_1 = new JSeparator();
		separator_1.setOrientation(SwingConstants.VERTICAL);
		separator_1.setMaximumSize(new Dimension(4, 32767));
		toolBar.add(separator_1);

		JButton btnBuild = new JButton("");
		btnBuild.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fCompile();
			}
		});
		btnBuild.setIcon(new ImageIcon(App.class.getResource("/img/Cog.png")));
		toolBar.add(btnBuild);

		JButton btnRun = new JButton("");
		btnRun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fRun();
			}
		});
		btnRun.setIcon(new ImageIcon(App.class.getResource("/img/Run .PNG")));
		toolBar.add(btnRun);

		JPanel panelStatusBar = new JPanel();
		panelStatusBar.setMinimumSize(new Dimension(10, 16));
		contentPane.add(panelStatusBar, BorderLayout.SOUTH);
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
		contentPane.add(splitPane, BorderLayout.CENTER);

		JTextPane epConsole = new JTextPane();
		epConsole.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (!running)
					e.consume();
			}
		});
		splitPane.setRightComponent(epConsole);

		textArea = new JTextArea();
		textArea.setTabSize(4);
		textArea.setFont(new Font("Consolas", Font.PLAIN, 14));
		// textArea.setFocusTraversalKeysEnabled(false);
		textArea.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				updateFile();
			}

			@Override
			public void keyPressed(KeyEvent e) {
			}
		});
		textArea.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent e) {
				updateLCLabel();
			}
		});

		JScrollPane scrollPane = new JScrollPane(textArea);
		splitPane.setLeftComponent(scrollPane);

//		TextLineNumber tln = new TextLineNumber(textArea);
//		scrollPane.setRowHeaderView(tln);
//		scrollPane.setViewportView(textArea);
	}

	/**********************************************************************************************************************************
	 * Actions
	 *********************************************************************************************************************************/

	public void fNew() {
		if (!savedFile) {
			if (!verifySaveFile()) {
				return;
			}
		}
		file = new FileTTO();
		textArea.setText("");
		resetControlVars();
		newFile = true;
		setTitle("Compiler");
	}

	public void fOpen() {
		if (!savedFile) {
			if (!verifySaveFile()) {
				return;
			}
		}
		var fullPath = getFilePath(false);
		if (fullPath.equals(""))
			return;
		file = new FileTTO(fullPath);
		resetControlVars();
		setTitle("Compiler - " + file.getName());
		textArea.setText(file.load());
	}

	public void fSave() {
		if (newFile) {
			fSaveAs();
		} else if (!savedFile) {
			resetFileVars();
			setTitle(getTitle().substring(0, getTitle().length() - 2));
			file.save(textArea.getText());
		}
	}

	public void fSaveAs() {
		var fullPath = getFilePath(true);
		if (fullPath.equals(""))
			return;

		file = new FileTTO(fullPath);
		resetFileVars();
		setTitle("Compiler - " + file.getName());
		file.save(textArea.getText());
	}

	public void fCut() {
		textArea.cut();
	}

	public void fCopy() {
		textArea.copy();
	}

	public void fPaste() {
		textArea.paste();
	}

	public void fCompile() {
		if (textArea.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Your file is empty.", "Warning", JOptionPane.OK_OPTION);
			return;
		}
		compiled = true;
		Compiler.build(new StringReader(textArea.getText()));
	}

	public void fRun() {
		if (!compiled) {
			JOptionPane.showMessageDialog(null, "Please, compile your file before running.", "Warning",
					JOptionPane.OK_OPTION);
			return;
		}
		running = true;
	}

	public void fAbout() {
		JOptionPane.showMessageDialog(null, "Authors: Carlos E. B. Machado and Herikc Brecher.", "About",
				JOptionPane.INFORMATION_MESSAGE);
	}

	public void fHelp() {
//		String text = "<html>"
//				    + "╔═ File <br/>"
//					+ "║    ╠═ New: Create a new 2021.1 file. <br/>"
//					+ "║    ╠═ Open: Open a 2021.1 file. <br/>"
//					+ "║    ╠═ Save: <br/>"
//					+ "║    ╠═ Save as: <br/>"
//					+ "║    ╚═ Exit: <br/>"
//				    + "╠═ Edit <br/>"
//					+ "║    ╠═ Cut: <br/>"
//					+ "║    ╠═ Copy: <br/>"
//					+ "║    ╚═ Paste: <br/>"
//				    + "╠═ Build <br/>"
//					+ "║    ╠═ Compile: <br/>"
//					+ "║    ╚═ Run: <br/>"
//					+ "</html>";
//		var label = new JLabel(text);
//		label.setFont(new Font("Consolas", Font.PLAIN, 11));
//		JOptionPane.showMessageDialog(null, label, "Help", JOptionPane.QUESTION_MESSAGE);
		new ShowHelp();
	}

	/**********************************************************************************************************************************
	 * UI controls
	 *********************************************************************************************************************************/

	private void updateLCLabel() {
		int caretPos = textArea.getCaretPosition();
		int rows = caretPos == 0 ? 1 : 0;
		int cols = 0;

		// JOptionPane.showInternalMessageDialog(null, caretPos);

		for (int offset = caretPos; offset > 0;) {
			try {
				offset = Utilities.getRowStart(textArea, offset) - 1;
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
			rows++;
		}

		int offset = 0;
		try {
			offset = Utilities.getRowStart(textArea, caretPos);
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

	/**********************************************************************************************************************************
	 * Auxiliary functions
	 *********************************************************************************************************************************/

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
		int result = JOptionPane.showConfirmDialog(null, "Would you like to save the file?", "Save",
				JOptionPane.YES_NO_CANCEL_OPTION);
		if (result == JOptionPane.YES_OPTION) {
			fSave();
		}
		if (result == JOptionPane.NO_OPTION) {

		}
		if (result == JOptionPane.CANCEL_OPTION) {
			return false;
		}
		return true;
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

		int userSelection = 0;
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
						int result = JOptionPane.showConfirmDialog(null,
								"A file with that name already exists. Would you like to overwrite?", "Overwrite",
								JOptionPane.YES_NO_OPTION);
						if (result == JOptionPane.YES_OPTION) {
							existisVerDone = true;
						}
						if (result == JOptionPane.NO_OPTION) {

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

		if (fullPath.length() >= 4 && !fullPath.substring(fullPath.length() - 4).equals(".tto")) {
			fullPath += ".tto";
		} else if (fullPath.length() < 4) {
			fullPath += ".tto";
		}

		return fullPath;
	}

}
