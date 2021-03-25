package br.univali.ttoproject.compiler.parser;

public class TokenCategorizer {
    private
    public static CategorizedToken categorize(Token token) {
        return new CategorizedToken(token.image, token.kind, token.beginLine, token.beginColumn, token.endLine, token.endColumn);
    }
}
