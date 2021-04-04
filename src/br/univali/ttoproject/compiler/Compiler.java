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
        boolean startedBlockComment = false;
        Token startedBlockCommentToken = null;

        try {
            var tokens = tokenize();

            for (Token token : tokens) {
                if(!startedBlockComment) {
                    if (token.image.equals("/*")) {
                        startedBlockComment = true;
                        startedBlockCommentToken = token;
                    }
                }

                if(!startedBlockComment) {
                    if (ParserConstants.tokenImage[token.kind] == "<UNKNOWN>") {
                        strTokenizer += "Lexical error at line " + token.beginLine + ", column " + token.beginColumn
                                + ". The following character '" + token.image + "' is invalid.\n\n";
                    } else {
                        strTokenizer += token.toString();
                    }
                }

                if(startedBlockComment) {
                    if (token.image.equals("*/"))
                        startedBlockComment = false;
                }

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if(startedBlockComment){
            strTokenizer += "Comment not finished at line " + startedBlockCommentToken.beginLine + ", column " + startedBlockCommentToken.beginColumn
                    + ".\n\n";
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
