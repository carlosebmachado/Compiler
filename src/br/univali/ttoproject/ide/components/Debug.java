package br.univali.ttoproject.ide.components;

import javax.swing.JOptionPane;

public class Debug {
    public static void show(String message) {
        JOptionPane.showMessageDialog(null, message, "Debug", JOptionPane.OK_OPTION);
    }
}
