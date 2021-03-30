package br.univali.ttoproject.ide.components;

import br.univali.ttoproject.ide.App;

import javax.swing.*;
import java.awt.*;
import java.util.function.Supplier;

public class ToolBar extends JToolBar {

    public ToolBar(Supplier<?>[] methods){

        var btnNew = new JButton("");
        btnNew.addActionListener(e -> methods[MenuOptions.NEW.getId()].get());
        btnNew.setIcon(new ImageIcon(App.class.getResource("/img/New File.png")));
        add(btnNew);

        var btnOpen = new JButton("");
        btnOpen.addActionListener(e -> methods[MenuOptions.OPEN.getId()].get());
        btnOpen.setIcon(new ImageIcon(App.class.getResource("/img/Open Project.png")));
        add(btnOpen);

        var btnSave = new JButton("");
        btnSave.addActionListener(e -> methods[MenuOptions.SAVE.getId()].get());
        btnSave.setIcon(new ImageIcon(App.class.getResource("/img/Save File.png")));
        add(btnSave);

        var separator1 = new JSeparator();
        separator1.setMaximumSize(new Dimension(4, 32767));
        separator1.setOrientation(SwingConstants.VERTICAL);
        add(separator1);

        var btnCut = new JButton("");
        btnCut.addActionListener(e -> methods[MenuOptions.CUT.getId()].get());
        btnCut.setIcon(new ImageIcon(App.class.getResource("/img/Cut.PNG")));
        add(btnCut);

        var btnCopy = new JButton("");
        btnCopy.addActionListener(e -> methods[MenuOptions.COPY.getId()].get());
        btnCopy.setIcon(new ImageIcon(App.class.getResource("/img/Copy.PNG")));
        add(btnCopy);

        var btnPaste = new JButton("");
        btnPaste.addActionListener(e -> methods[MenuOptions.PASTE.getId()].get());
        btnPaste.setIcon(new ImageIcon(App.class.getResource("/img/Paste.png")));
        add(btnPaste);

        var separator2 = new JSeparator();
        separator2.setOrientation(SwingConstants.VERTICAL);
        separator2.setMaximumSize(new Dimension(4, 32767));
        add(separator2);

        var btnBuild = new JButton("");
        btnBuild.addActionListener(e -> methods[MenuOptions.COMPILE.getId()].get());
        btnBuild.setIcon(new ImageIcon(App.class.getResource("/img/Cog.png")));
        add(btnBuild);

        var btnRun = new JButton("");
        btnRun.addActionListener(e -> methods[MenuOptions.RUN.getId()].get());
        btnRun.setIcon(new ImageIcon(App.class.getResource("/img/Run .PNG")));
        add(btnRun);
    }

}
