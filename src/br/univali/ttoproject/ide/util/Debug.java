package br.univali.ttoproject.ide.util;

import javax.swing.JOptionPane;

public class Debug {
    public static void show(String message) {
        JOptionPane.showMessageDialog(null, message, "Debug", JOptionPane.ERROR_MESSAGE);
    }
}
