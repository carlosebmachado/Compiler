package br.univali.ttoproject.ide.components;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Console extends JTextArea {

    private boolean allowConsoleInput = false;
    private int allowedCaretPosition;
    private int initialCaretPosition;

    public Console() {
        setTabSize(0);
        setFocusTraversalKeysEnabled(false);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                handleKey(e);
            }
        });
    }

    public String getUserInput() {
        return "";
    }

    public void addContent(String content) {
        setText(getText() + content);
    }

    public void reset() {
        setText("");
        allowConsoleInput = false;
    }

    private void init() {
        allowConsoleInput = true;
        allowedCaretPosition = getCaretPosition();
        initialCaretPosition = allowedCaretPosition;
    }

    private void stop() {
        allowConsoleInput = false;
    }

    private void handleKey(KeyEvent e) {
        if (allowConsoleInput) {
            requestFocusInWindow();

            var keyChar = e.getKeyChar();
            var curCaretPosition = getCaretPosition();

            if (keyChar == '\b') {
                if (curCaretPosition > initialCaretPosition) {
                    allowedCaretPosition--;
                } else {
                    e.consume();
                }
                return;
            }
            if (keyChar == '\n') {
                stop();
                return;
            }
            if (keyChar == '\t') {
                e.consume();
                return;
            }

            if (allowedCaretPosition != curCaretPosition) {
                e.consume();
            } else {
                allowedCaretPosition++;
            }
        } else {
            e.consume();
        }
    }

}
