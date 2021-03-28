package test.br.univali.ttoproject.compiler;

import br.univali.ttoproject.compiler.Compiler;
import br.univali.ttoproject.compiler.parser.*;
import org.junit.jupiter.api.Test;

import java.io.StringReader;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TestCase {
    public final String input;
    public final List<Token> expected;

    public TestCase(String input, List<Token> expected) {
        this.input = input;
        this.expected = expected;
    }
}

public class CompilerTest {
    @Test
    public void isCaseInsensitiveWhenLexingKeywords() {
        List<TestCase> testCases = Arrays.asList(
                new TestCase("define",
                        Arrays.asList(new CategorizedToken(TokenCategory.Keyword, ParserConstants.DEFINE, "define", 1, 1, 1, 6))),
                new TestCase("Define",
                        Arrays.asList(new CategorizedToken(TokenCategory.Keyword, ParserConstants.DEFINE, "Define", 1, 1, 1, 6))),
                new TestCase("DeFiNe",
                        Arrays.asList(new CategorizedToken(TokenCategory.Keyword, ParserConstants.DEFINE, "DeFiNe", 1, 1, 1, 6))),
                new TestCase("DEFINE",
                        Arrays.asList(new CategorizedToken(TokenCategory.Keyword, ParserConstants.DEFINE, "DEFINE", 1, 1, 1, 6))));


        for (TestCase testCase : testCases) {
            var compiler = new Compiler();

            compiler.setParser(new Parser(new StringReader(testCase.input)));

            try {
                var actual = compiler.tokenize();
                assertEquals(testCase.expected, actual);
            } catch (Exception e) {
                System.out.println(e);
                assertTrue(false);
            }
        }
    }

    @Test
    public void categorizesTokens() {
        List<TestCase> testCases = Arrays.asList(
                new TestCase("program",
                        Arrays.asList(new CategorizedToken(TokenCategory.Keyword, ParserConstants.PROGRAM, "program", 1, 1, 1, 7))),
                new TestCase("define",
                        Arrays.asList(new CategorizedToken(TokenCategory.Keyword, ParserConstants.DEFINE, "define", 1, 1, 1, 6))),
                new TestCase("not",
                        Arrays.asList(new CategorizedToken(TokenCategory.Keyword, ParserConstants.NOT, "not", 1, 1, 1, 3))),
                new TestCase("variable",
                        Arrays.asList(new CategorizedToken(TokenCategory.Keyword, ParserConstants.VARIABLE, "variable", 1, 1, 1, 8))),
                new TestCase("is",
                        Arrays.asList(new CategorizedToken(TokenCategory.Keyword, ParserConstants.IS, "is", 1, 1, 1, 2))),
                new TestCase("natural",
                        Arrays.asList(new CategorizedToken(TokenCategory.Keyword, ParserConstants.NATURAL, "natural", 1, 1, 1, 7))),
                new TestCase("real",
                        Arrays.asList(new CategorizedToken(TokenCategory.Keyword, ParserConstants.REAL, "real", 1, 1, 1, 4))),
                new TestCase("char",
                        Arrays.asList(new CategorizedToken(TokenCategory.Keyword, ParserConstants.CHAR, "char", 1, 1, 1, 4))),
                new TestCase("boolean",
                        Arrays.asList(new CategorizedToken(TokenCategory.Keyword, ParserConstants.BOOLEAN, "boolean", 1, 1, 1, 7))),
                new TestCase("execute",
                        Arrays.asList(new CategorizedToken(TokenCategory.Keyword, ParserConstants.EXECUTE, "execute", 1, 1, 1, 7))),
                new TestCase("set",
                        Arrays.asList(new CategorizedToken(TokenCategory.Keyword, ParserConstants.SET, "set", 1, 1, 1, 3))),
                new TestCase("to",
                        Arrays.asList(new CategorizedToken(TokenCategory.Keyword, ParserConstants.TO, "to", 1, 1, 1, 2))),
                new TestCase("get",
                        Arrays.asList(new CategorizedToken(TokenCategory.Keyword, ParserConstants.GET, "get", 1, 1, 1, 3))),
                new TestCase("put",
                        Arrays.asList(new CategorizedToken(TokenCategory.Keyword, ParserConstants.PUT, "put", 1, 1, 1, 3))),
                new TestCase("verify",
                        Arrays.asList(new CategorizedToken(TokenCategory.Keyword, ParserConstants.VERIFY, "verify", 1, 1, 1, 6))),
                new TestCase("true",
                        Arrays.asList(new CategorizedToken(TokenCategory.Keyword, ParserConstants.TRUE, "true", 1, 1, 1, 4))),
                new TestCase("false",
                        Arrays.asList(new CategorizedToken(TokenCategory.Keyword, ParserConstants.FALSE, "false", 1, 1, 1, 5))),
                new TestCase("loop",
                        Arrays.asList(new CategorizedToken(TokenCategory.Keyword, ParserConstants.LOOP, "loop", 1, 1, 1, 4))),
                new TestCase("while",
                        Arrays.asList(new CategorizedToken(TokenCategory.Keyword, ParserConstants.WHILE, "while", 1, 1, 1, 5))),
                new TestCase("do",
                        Arrays.asList(new CategorizedToken(TokenCategory.Keyword, ParserConstants.DO, "do", 1, 1, 1, 2)))

        );

        for (TestCase testCase : testCases) {
            var compiler = new Compiler();

            compiler.setParser(new Parser(new StringReader(testCase.input)));

            try {
                var actual = compiler.tokenize();
                assertEquals(testCase.expected, actual);
            } catch (Exception e) {
                System.out.println(e);
                assertTrue(false);
            }
        }
    }
}
