package br.univali.ttoproject.compiler.parser;

public class CategorizedToken {
    public final String lexeme;
    public final int kind;
    public final int beginLine;
    public final int beginColumn;
    public final int endLine;
    public final int endColumn;
    public final TokenCategory category;

    public CategorizedToken(String lexeme, int kind, int beginLine, int beginColumn, int endLine, int endColumn,TokenCategory category) {
        this.lexeme = lexeme;
        this.kind = kind;
        this.beginLine = beginLine;
        this.endLine = endLine;
        this.beginColumn = beginColumn;
        this.endColumn = endColumn;
        this.category = category;
    }
}
