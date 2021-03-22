package me.herikc.compiler;

import java.io.FileNotFoundException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import me.herikc.core.Lexer;
import me.herikc.core.LexerConstants;
import me.herikc.core.Token;

public class Compiler {
	
	private static Lexer parser;
	
	public Compiler() {
		setParser(null);
	}
	
	public static void main(String[] args) {	
		Reader reader = new StringReader("program {\r\n"
				+ "	define {\r\n"
				+ "		natural teste 2\r\n"
				+ "	}\r\n"
				+ "}");
		
		build(reader);
		
		/*try {
			parser = new Lexer(new FileInputStream(new File("C:\\Users\\Windows\\eclipse-workspace\\Compiler\\teste.txt")));
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
	
	public static void build(Reader reader) {
		parser = new Lexer(reader);
		try {
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
		}
	}
	
	public static List<Token> tokenize() throws FileNotFoundException {
		List<Token> tokens = new ArrayList<>();

		Token token = Lexer.getNextToken();
		while (token.kind != LexerConstants.EOF) {
			tokens.add(token);
			token = Lexer.getNextToken();
		}
		return tokens;
	}

	public Lexer getParser() {
		return parser;
	}

	public void setParser(Lexer parser) {
		Compiler.parser = parser;
	}

}
