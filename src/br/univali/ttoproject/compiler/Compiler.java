package br.univali.ttoproject.compiler;

import java.io.FileNotFoundException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import br.univali.ttoproject.compiler.parser.ParserTTO;
import br.univali.ttoproject.compiler.parser.ParserTTOConstants;
import br.univali.ttoproject.compiler.parser.Token;

public class Compiler {
	
	private static ParserTTO parser;
	
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
	
	public static void build(Reader reader) {
		parser = new ParserTTO(reader);
		try {
			for (Token token : tokenize()) {			
				System.out.println(
						"Linha: " + token.beginLine
						+ "\nColuna:" + token.beginColumn
						+ "\nNumero da Categoria: " + token.kind
						+ "\nCategoria: " + ParserTTOConstants.tokenImage[token.kind]
						+ "\nToken: " + token.image + "\n"
						);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static List<Token> tokenize() throws FileNotFoundException {
		List<Token> tokens = new ArrayList<>();

		Token token = ParserTTO.getNextToken();
		while (token.kind != ParserTTOConstants.EOF) {
			tokens.add(token);
			token = ParserTTO.getNextToken();
		}
		return tokens;
	}

	public ParserTTO getParser() {
		return parser;
	}

	public void setParser(ParserTTO parser) {
		Compiler.parser = parser;
	}

}
