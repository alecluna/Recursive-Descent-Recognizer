import java.io.*;
import java.util.Scanner;

public class Recognizer2 {
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

    private void javaClass() {
        className();
        match('B');
        varlist();

        if (token() == 'X') {
            className();
        }

        while (token() == 'P') {
            method();
        }
        match('E');
    }

    private void className() {
        if ((token() == 'C') || (token() == 'D')) {
            match(token());
        } else
            error();
    }

    private void varlist() { // fix

        vardef();
        while (token() == ',') {
            match(token());
            vardef();
        }
    }

    private void vardef() {
        type();
        varname();
    }

    private void varname() {
        letter();
        while ((token() == 'Y') || (token() == 'Z') || (token() == '0') || (token() == '1') || (token() == '2')
                || (token() == '3')) {
            charToken();
        }
    }

    private void charToken() {

        if ((token() == 'Y') || (token() == 'Z')) {
            letter();
        } else {
            digit();
        }
    }

    private void digit() {
        if ((token() == '0') || (token() == '1') || (token() == '2') || (token() == '3')) {
            match(token());
        } else
            error();
    }

    private void integer() {
        digit();
        while ((token() == '0') || (token() == '1') || (token() == '2') || (token() == '3')) {
            digit();
        }
    }

    private void letter() {

        if ((token() == 'Y') || (token() == 'Z')) {
            match(token());
        } else
            error();
    }

    private void type() {

        if ((token() == 'I') || (token() == 'S')) {
            match(token());
        } else
            error();
    }

    // <varref> ::= J|K
    private void varref() {
        if ((token() == 'J') || (token() == 'k')) {
            match(token());
        } else
            error();
    }

    private void method() {
        accessor();
        type();
        methodname();
        varref();
    }

    private void accessor() {
        if ((token() == 'P') || (token() == 'V')) {
            match(token());
        } else
            error();
    }

    private void methodname() {

        if ((token() == 'M') || (token() == 'N')) {
            match(token());
        } else
            error();
    }

    private void start() {
        javaClass();
        match('$');
        if (errorflag == 0) {

            System.out.println("legal." + "\n");
        } else {
            System.out.println("errors found." + "\n");
        }
    }

    public static void main(String[] args) throws IOException {
        Recognizer2 rec = new Recognizer2();
        Scanner input = new Scanner(System.in);
        System.out.print("\n" + "enter an expression: ");
        inputString = input.nextLine();
        rec.start();
    }
}