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
	
	public static void main(String[] args) {	
		/*Reader reader = new StringReader("program {\r\n"
				+ "	define {\r\n"
				+ "		natural teste 2\r\n"
				+ "	}\r\n"
				+ "}");
		
		build(reader);*/
		
		/*try {
			parser = new Lexer(new FileInputStream(new File("C:\\Users\\Windows\\eclipse-workspace\\Compiler\\test.tto")));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}*/
			
		/*try {
			for (Token token : tokenize()) {			
				System.out.println(
						"Linha: " + token.beginLine
						+ "\nColuna:" + token.beginColumn
						+ "\nNumero da Categoria: " + token.kind
						+ "\nCategoria: " + LexerConstants.tokenImage[token.kind]
						+ "\nToken: " + token.image + "\n"
						);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}*/
		
	}
	
	public String build(Reader reader) {
		parser = new Parser(reader);
		var strTokenizer = "";
		
		try {
			for (Token token : tokenize()) {
				
				if(ParserConstants.tokenImage[token.kind] == "<UNKNOWN>") {
					strTokenizer += "Lexical error at line " + token.beginLine + ", column " + token.beginColumn + ". The following character '" + token.image +  "' is invalid.\n\n";
				} else {
					strTokenizer += "Line: " + token.beginLine
							+ "\nColumn:" + token.beginColumn
							+ "\nCategory Number: " + token.kind
							+ "\nCategory: " + ParserConstants.tokenImage[token.kind]
							+ "\nToken: " + token.image + "\n\n";
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
