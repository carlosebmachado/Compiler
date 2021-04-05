package br.univali.ttoproject.ide.components;

import br.univali.ttoproject.ide.App;

import javax.swing.*;
import java.util.function.Supplier;

public class MenuBar extends JMenuBar {

    public MenuBar(Supplier<?>[] methods) {
        var mnFile = new JMenu("File");
        add(mnFile);

        var mntmNew = new JMenuItem("New");
        mntmNew.addActionListener(e -> methods[MenuOptions.NEW.getId()].get());
        mntmNew.setIcon(new ImageIcon(App.class.getResource("/img/New File.png")));
        mnFile.add(mntmNew);

        var mntmOpen = new JMenuItem("Open");
        mntmOpen.addActionListener(e -> methods[MenuOptions.OPEN.getId()].get());
        mntmOpen.setIcon(new ImageIcon(App.class.getResource("/img/Open Project.png")));
        mnFile.add(mntmOpen);

        var mntmSave = new JMenuItem("Save");
        mntmSave.addActionListener(e -> methods[MenuOptions.SAVE.getId()].get());
        mntmSave.setIcon(new ImageIcon(App.class.getResource("/img/Save File.png")));
        mnFile.add(mntmSave);

        var mntmSaveAs = new JMenuItem("Save as...");
        mntmSaveAs.addActionListener(e -> methods[MenuOptions.SAVE_AS.getId()].get());
        mnFile.add(mntmSaveAs);

        mnFile.addSeparator();

        var mntmSettings = new JMenuItem("Settings");
        mntmSettings.addActionListener(e -> methods[MenuOptions.SETTINGS.getId()].get());
        mnFile.add(mntmSettings);

        mnFile.addSeparator();

        var mntmExit = new JMenuItem("Exit");
        mntmExit.addActionListener(e -> methods[MenuOptions.EXIT.getId()].get());
        mnFile.add(mntmExit);

        var mnEdit = new JMenu("Edit");
        add(mnEdit);

        var mntmCut = new JMenuItem("Cut");
        mntmCut.addActionListener(e -> methods[MenuOptions.CUT.getId()].get());
        mntmCut.setIcon(new ImageIcon(App.class.getResource("/img/Cut.PNG")));
        mnEdit.add(mntmCut);

        var mntmCopy = new JMenuItem("Copy");
        mntmCopy.addActionListener(e -> methods[MenuOptions.COPY.getId()].get());
        mntmCopy.setIcon(new ImageIcon(App.class.getResource("/img/Copy.PNG")));
        mnEdit.add(mntmCopy);

        var mntmPaste = new JMenuItem("Paste");
        mntmPaste.addActionListener(e -> methods[MenuOptions.PASTE.getId()].get());
        mntmPaste.setIcon(new ImageIcon(App.class.getResource("/img/Paste.png")));
        mnEdit.add(mntmPaste);

        var mnBuild = new JMenu("Build");
        add(mnBuild);

        var mntmCompile = new JMenuItem("Compile");
        mntmCompile.addActionListener(e -> methods[MenuOptions.COMPILE.getId()].get());
        mntmCompile.setIcon(new ImageIcon(App.class.getResource("/img/Cog.png")));
        mnBuild.add(mntmCompile);

        var mntmRun = new JMenuItem("Run");
        mntmRun.addActionListener(e -> methods[MenuOptions.RUN.getId()].get());
        mntmRun.setIcon(new ImageIcon(App.class.getResource("/img/Run .PNG")));
        mnBuild.add(mntmRun);

        var mnAbout = new JMenu("Help");
        add(mnAbout);

        var mntmNewMenuItem = new JMenuItem("Show help");
        mntmNewMenuItem.addActionListener(e -> methods[MenuOptions.HELP.getId()].get());
        mnAbout.add(mntmNewMenuItem);

        var mntmNewMenuItem_1 = new JMenuItem("About");
        mntmNewMenuItem_1.addActionListener(e -> methods[MenuOptions.ABOUT.getId()].get());
        mnAbout.add(mntmNewMenuItem_1);
    }

}
