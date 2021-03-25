package br.univali.ttoproject.ide.components;

import javax.swing.*;
import java.awt.*;

public class StatusBar extends JPanel {
    public StatusBar(){
        setMinimumSize(new Dimension(10, 16));
        setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
    }

    @Override
    public Component add(Component comp) {
        JSeparator separator = new JSeparator();
        separator.setPreferredSize(new Dimension(5, 14));
        separator.setMaximumSize(new Dimension(5, 32767));
        separator.setOrientation(SwingConstants.VERTICAL);
        super.add(separator);
        return super.add(comp);
    }
}
