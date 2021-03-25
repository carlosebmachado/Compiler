package br.univali.ttoproject.ide.components;

import br.univali.ttoproject.ide.App;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ToolBar extends JToolBar {
    public ToolBar(Consumer[] consumers, Supplier[] suppliers){

        JButton btnNew = new JButton("");
        btnNew.addActionListener(e -> consumers[MenuEnum.NEW].accept(null));
        btnNew.setIcon(new ImageIcon(App.class.getResource("/img/New File.png")));
        add(btnNew);

        JButton btnOpen = new JButton("");
        btnOpen.addActionListener(e -> consumers[MenuEnum.OPEN].accept(null));
        btnOpen.setIcon(new ImageIcon(App.class.getResource("/img/Open Project.png")));
        add(btnOpen);

        JButton btnSave = new JButton("");
        btnSave.addActionListener(e -> suppliers[MenuEnum.SAVE].get());
        btnSave.setIcon(new ImageIcon(App.class.getResource("/img/Save File.png")));
        add(btnSave);

        JSeparator separator = new JSeparator();
        separator.setMaximumSize(new Dimension(4, 32767));
        separator.setOrientation(SwingConstants.VERTICAL);
        add(separator);

        JButton btnCut = new JButton("");
        btnCut.addActionListener(e -> consumers[MenuEnum.CUT].accept(null));
        btnCut.setIcon(new ImageIcon(App.class.getResource("/img/Cut.PNG")));
        add(btnCut);

        JButton btnCopy = new JButton("");
        btnCopy.addActionListener(e -> consumers[MenuEnum.COPY].accept(null));
        btnCopy.setIcon(new ImageIcon(App.class.getResource("/img/Copy.PNG")));
        add(btnCopy);

        JButton btnPaste = new JButton("");
        btnPaste.addActionListener(e -> consumers[MenuEnum.PASTE].accept(null));
        btnPaste.setIcon(new ImageIcon(App.class.getResource("/img/Paste.png")));
        add(btnPaste);

        JSeparator separator_1 = new JSeparator();
        separator_1.setOrientation(SwingConstants.VERTICAL);
        separator_1.setMaximumSize(new Dimension(4, 32767));
        add(separator_1);

        JButton btnBuild = new JButton("");
        btnBuild.addActionListener(e -> consumers[MenuEnum.COMPILE].accept(null));
        btnBuild.setIcon(new ImageIcon(App.class.getResource("/img/Cog.png")));
        add(btnBuild);

        JButton btnRun = new JButton("");
        btnRun.addActionListener(e -> consumers[MenuEnum.RUN].accept(null));
        btnRun.setIcon(new ImageIcon(App.class.getResource("/img/Run .PNG")));
        add(btnRun);
    }
}
