package br.univali.ttoproject.compiler;

import java.io.FileNotFoundException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import br.univali.ttoproject.compiler.parser.Parser;
import br.univali.ttoproject.compiler.parser.ParserConstants;
import br.univali.ttoproject.compiler.parser.Token;

public class Compiler {

    private Parser parser;

    public Compiler() {
        setParser(null);
    }

    public String build(Reader reader) {
        parser = new Parser(reader);
        var strTokenizer = "";

        try {
            var tokens = tokenize();

            for (Token token : tokens) {
                if (ParserConstants.tokenImage[token.kind] == "<UNKNOWN>") {
                    strTokenizer += "Lexical error at line " + token.beginLine + ", column " + token.beginColumn
                            + ". The following character '" + token.image + "' is invalid.\n\n";
                } else {
                    strTokenizer += token.toString();
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return strTokenizer;
    }

    public List<Token> tokenize() throws FileNotFoundException {
        List<Token> tokens = new ArrayList<>();

        Token token = parser.getNextToken();

        while (token.kind != ParserConstants.EOF) {
            tokens.add(token);
            token = parser.getNextToken();
        }

        return tokens;
    }

    public Parser getParser() {
        return parser;
    }

    public void setParser(Parser parser) {
        this.parser = parser;
    }
}
