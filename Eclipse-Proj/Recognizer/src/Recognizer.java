import java.io.*;
import java.util.Scanner;
//--------------------------------------------
// Recognizer for simple expression grammar
// Written by Alec Luna 3/3/18
//
/*
I am using the professor's given example code to increment through the characters that are input, error checking, and output.
* To compile and run program on athena:  javac Rocognizer.java --> java Rocognizer --> input expression
* INPUT FORMATTING: No spaces and accepts all letters
  BEGIN INPUT WITH A $ SYMBOL 
*/
//

public class Recognizer {
	static String inputString;
	static int index = 0;
	static int errorflag = 0;

	private char token() {
		return (inputString.charAt(index));
	}

	private void advancePtr() {
		if (index < (inputString.length() - 1))
			index++;
	}

	private void match(char T) {
		if (T == token())
			advancePtr();
		else
			error();
	}

	private void error() {
		System.out.println("error at position: " + index);
		errorflag = 1;
		advancePtr();
	}

	private void program() {
		if (token() == 'B')
			match(token());
		while (token() == 'A' || token() == 'G') {

			deflst();
		}

		if (token() == 'D') {
			match(token());
		}

		while (token() == 'A' || token() == 'G' || token() == 'X' || token() == 'Y' || token() == 'Z') {

			statmnt();
		}

		if (token() == 'F') {
			match(token());
		}
		if (token() == ';') {

			match(token());
		}

	}

	private void deflst() {

		vardef();
		if (token() == ';') {

			match(token());
		}
		while (token() == 'A' || token() == 'G' || token() == 'X' || token() == 'Y' || token() == 'Z') {

			vardef();
			match(token());

		}
		if (token() == 'Q') {

			match(token());
		}
	}

	private void vardef() {

		typesym();
		varlst();
	}

	private void typesym() {

		if (token() == 'A') {
			match(token());
		}
		if (token() == 'G') {
			match(token());
		}
	}

	private void varlst() {

		ident();
		while (token() == ',') {
			match(token());
			ident();
		}
	}

	private void statmnt() {

		if ((token() == 'X') || (token() == 'Y') || (token() == 'Z')) {

			asgnstmt();
		} else if (token() == 'S') {
			ifstmt();
		} else if (token() == 'P') {
			loop();

		} else if (token() == 'L') {

			input();
		} else if (token() == 'C') {

			write();
		}
	}

	private void asgnstmt() {

		ident();
		if (token() == '#') {

			match(token());
		}
		expr();
		if (token() == ';') {

			match(token());
		}
	}

	private void ifstmt() {

		if (token() == 'S') {

			match(token());
		}
		comp();

		if (token() == '&') {
			match(token());
		}

		while ((token() == 'X') || (token() == 'Y') || (token() == 'Z') || (token() == 'S') || (token() == 'P')
				|| (token() == 'L') || (token() == 'C')) {

			statmnt();
		}

		if (token() == 'O') {

			match(token());
			while ((token() == 'X') || (token() == 'Y') || (token() == 'Z') || (token() == 'S') || (token() == 'P')
					|| (token() == 'L') || (token() == 'C')) {

				statmnt();
			}
		}
		if (token() == 'H') {

			match(token());
		}

	}

	private void loop() {

		if (token() == 'P') {
			match(token());
		}
		comp();

		if (token() == 'U') {

			match(token());
		}

		while (token() == ',') {
			match(token());
			ident();
		}
		if (token() == 'E') {
			match(token());
		}
	}

	private void input() {

		if (token() == 'L') {
			match(token());
		}
		ident();
		while (token() == ',') {
			match(token());
			ident();
		}

		if (token() == ';') {
			match(token());
		}
	}

	private void write() {

		if (token() == 'C') {

			match(token());
			ident();
		}

		while (token() == ',') {
			match(token());
			ident();
		}
		if (token() == ';') {
			match(token());
		}
	}

	private void comp() {

		if (token() == '(') {

			match(token());
			operd();
			oper();
			operd();
			match(token());
		}
	}

	private void expr() {

		term();
		while (token() == '*') {

			match(token());
			term();
		}
	}

	private void term() {

		operd();
		while (token() == ',') {

			match(token());
			operd();
		}
	}

	private void operd() {

		if ((token() == '0') || (token() == '1')) {
			intToken();
		} else if ((token() == 'X') || (token() == 'Y') || (token() == 'Z') || (token() == '0') || (token() == '1')) {

			charToken();
		} else if (token() == '(') {
			match(token());
			expr();
			match(token());
		} else {
			error();
		}
	}

	private void oper() {

		if ((token() == '<') || (token() == '=') || (token() == '>') || (token() == '?')) {
			match(token());
		} else {
			error();
		}
	}

	private void ident() {

		letter();
		while ((token() == 'X') || (token() == 'Y') || (token() == 'Z') || (token() == '0') || (token() == '1')) {

			charToken();
		}
	}

	private void charToken() {

		if ((token() == 'X') || (token() == 'Y') || (token() == 'Z')) {

			letter();
		} else {
			digit();
		}
	}

	private void intToken() {

		digit();
		while ((token() == '0') || (token() == '1')) {
			digit();
		}
	}

	private void letter() {
		if ((token() == 'X') || (token() == 'Y') || (token() == 'Z'))
			match(token());
		else
			error();
	}

	private void digit() {
		if ((token() == '0') || (token() == '1'))
			match(token());
		else
			error();
	}

	// ----------------------
	private void start() {
		program();
		match('$');
		if (errorflag == 0)
			System.out.println("legal." + "\n");
		else
			System.out.println("errors found." + "\n");
	}

	// ----------------------
	public static void main(String[] args) throws IOException {
		Recognizer rec = new Recognizer();
		Scanner input = new Scanner(System.in);
		System.out.print("\n" + "enter an expression: ");
		inputString = input.nextLine();
		rec.start();
	}
}
