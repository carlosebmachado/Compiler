package br.univali.ttoproject.ide.components;

public enum MenuOptions {
    NEW(0),
    OPEN(1),
    SAVE(0),
    SAVE_AS(1),
    EXIT(2),
    CUT(3),
    COPY(4),
    PASTE(5),
    COMPILE(6),
    RUN(7),
    HELP(8),
    ABOUT(9);

    private int id;

    MenuOptions(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
