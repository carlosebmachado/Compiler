package br.univali.ttoproject.ide.components;

public enum MenuOptions {
    NEW(0),
    OPEN(1),
    SAVE(2),
    SAVE_AS(3),
    SETTINGS(4),
    EXIT(5),
    CUT(6),
    COPY(7),
    PASTE(8),
    COMPILE(9),
    RUN(10),
    HELP(11),
    ABOUT(12);

    private int id;

    MenuOptions(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
