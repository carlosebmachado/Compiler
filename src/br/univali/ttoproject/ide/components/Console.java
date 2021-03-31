package br.univali.ttoproject.ide.components;

import br.univali.ttoproject.ide.App;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.function.Consumer;

public class Console extends JTextArea {

    private final Consumer<String> method;

    private boolean allowConsoleInput = false;
    private int allowedCaretPosition;
    private int initialCaretPosition;

    public Console(Consumer<String> method) {
        this.method = method;
        setTabSize(4);
        setFocusTraversalKeysEnabled(false);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                handleKeyTyped(e);
            }

            @Override
            public void keyPressed(KeyEvent e) {
                handleKeyPressed(e);
            }
        });
    }

    public void addContent(String content) {
        setText(getText() + content);
    }

    public void reset() {
        setText("");
        allowConsoleInput = false;
    }

    public void initDataEntry(String content) {
        addContent(content);
        allowConsoleInput = true;
        allowedCaretPosition = getCaretPosition();
        initialCaretPosition = allowedCaretPosition;
    }

    private void stopDataEntry() {
        allowConsoleInput = false;
    }

    private void handleKeyTyped(KeyEvent e) {
        if (allowConsoleInput) {
            requestFocusInWindow();

            var keyChar = e.getKeyChar();
            var curCaretPosition = getCaretPosition();

            if (keyChar == '\n') {
                stopDataEntry();
                var entry = getText().substring(initialCaretPosition);
                entry = entry.substring(0, entry.length() - 1);
                method.accept(entry);
                return;
            }

            if (curCaretPosition >= initialCaretPosition && curCaretPosition <= allowedCaretPosition) {
                allowedCaretPosition++;
            } else {
                e.consume();
            }
        } else {
            e.consume();
        }
    }

    private void handleKeyPressed(KeyEvent e) {
        var keyChar = e.getKeyCode();
        var curCaretPosition = getCaretPosition();

        if (keyChar == KeyEvent.VK_BACK_SPACE) {
            if (!allowConsoleInput){
                e.consume();
            } else if (curCaretPosition > initialCaretPosition) {
                allowedCaretPosition--;
            } else {
                e.consume();
            }
        } else if (keyChar == KeyEvent.VK_ENTER) {
            if (!allowConsoleInput){
                e.consume();
            }
        } else if (keyChar == KeyEvent.VK_TAB) {
            e.consume();
        }
    }

}
