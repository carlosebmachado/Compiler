package br.univali.ttoproject.ide.components;

import br.univali.ttoproject.ide.App;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class MenuBar extends JMenuBar {
    public MenuBar(Consumer[] consumers, Supplier[] suppliers){
        JMenu mnFile = new JMenu("File");
        add(mnFile);

        JMenuItem mntmNew = new JMenuItem("New");
        mntmNew.addActionListener(e -> consumers[MenuEnum.NEW].accept(null));
        mntmNew.setIcon(new ImageIcon(App.class.getResource("/img/New File.png")));
        mnFile.add(mntmNew);

        JMenuItem mntmOpen = new JMenuItem("Open");
        mntmOpen.addActionListener(e -> consumers[MenuEnum.OPEN].accept(null));
        mntmOpen.setIcon(new ImageIcon(App.class.getResource("/img/Open Project.png")));
        mnFile.add(mntmOpen);

        JMenuItem mntmSave = new JMenuItem("Save");
        mntmSave.addActionListener(e -> suppliers[MenuEnum.SAVE].get());
        mntmSave.setIcon(new ImageIcon(App.class.getResource("/img/Save File.png")));
        mnFile.add(mntmSave);

        JMenuItem mntmSaveAs = new JMenuItem("Save as...");
        mntmSaveAs.addActionListener(e -> suppliers[MenuEnum.SAVE_AS].get());
        mnFile.add(mntmSaveAs);

        JMenuItem mntmExit = new JMenuItem("Exit");
        mntmExit.addActionListener(e -> consumers[MenuEnum.EXIT].accept(null));
        mnFile.add(mntmExit);

        JMenu mnEdit = new JMenu("Edit");
        add(mnEdit);

        JMenuItem mntmCut = new JMenuItem("Cut");
        mntmCut.addActionListener(e -> consumers[MenuEnum.CUT].accept(null));
        mntmCut.setIcon(new ImageIcon(App.class.getResource("/img/Cut.PNG")));
        mnEdit.add(mntmCut);

        JMenuItem mntmCopy = new JMenuItem("Copy");
        mntmCopy.addActionListener(e -> consumers[MenuEnum.COPY].accept(null));
        mntmCopy.setIcon(new ImageIcon(App.class.getResource("/img/Copy.PNG")));
        mnEdit.add(mntmCopy);

        JMenuItem mntmPaste = new JMenuItem("Paste");
        mntmPaste.addActionListener(e -> consumers[MenuEnum.PASTE].accept(null));
        mntmPaste.setIcon(new ImageIcon(App.class.getResource("/img/Paste.png")));
        mnEdit.add(mntmPaste);

        JMenu mnBuild = new JMenu("Build");
        add(mnBuild);

        JMenuItem mntmCompile = new JMenuItem("Compile");
        mntmCompile.addActionListener(e -> consumers[MenuEnum.COMPILE].accept(null));
        mntmCompile.setIcon(new ImageIcon(App.class.getResource("/img/Cog.png")));
        mnBuild.add(mntmCompile);

        JMenuItem mntmRun = new JMenuItem("Run");
        mntmRun.addActionListener(e -> consumers[MenuEnum.RUN].accept(null));
        mntmRun.setIcon(new ImageIcon(App.class.getResource("/img/Run .PNG")));
        mnBuild.add(mntmRun);

        JMenu mnAbout = new JMenu("Help");
        add(mnAbout);

        JMenuItem mntmNewMenuItem = new JMenuItem("Show help");
        mntmNewMenuItem.addActionListener(e -> consumers[MenuEnum.HELP].accept(null));
        mnAbout.add(mntmNewMenuItem);

        JMenuItem mntmNewMenuItem_1 = new JMenuItem("About");
        mntmNewMenuItem_1.addActionListener(e -> consumers[MenuEnum.ABOUT].accept(null));
        mnAbout.add(mntmNewMenuItem_1);
    }
}
