package br.univali.ttoproject.ide.components;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Console extends JTextArea {

    private boolean allowConsoleInput = false;
    private int allowedCaretPosition;
    private int initialCaretPosition;

    public Console() {
        setTabSize(4);
        setFocusTraversalKeysEnabled(false);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                handleKey(e);
            }
        });
    }

    public void handleKey(KeyEvent e) {
        if (allowConsoleInput) {
            userInput(e);
        } else {
            e.consume();
        }
    }

    public void init() {
        allowConsoleInput = true;
        allowedCaretPosition = getCaretPosition();
        initialCaretPosition = allowedCaretPosition;
    }

    public void stop() {
        allowConsoleInput = false;
    }

    public void reset() {
        setText("");
    }

    public void addContent(String content) {
        setText(getText() + content);
    }

    public void userInput(KeyEvent e) {
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
    }

}
