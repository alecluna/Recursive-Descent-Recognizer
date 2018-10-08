import java.io.*;
import java.util.Scanner;

import jdk.tools.jaotc.collect.classname.ClassNameSource;

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

        if (token() == 'X') {
            className();
        }
        match('B');
        varlist();

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
        if ((token() == 'I') || (token() == 'S')) {
            type();
            varname();
        } else if ((token() == 'C') || (token() == 'D')) {
            className();
            varref();
        }
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

    private void varref() {
        if ((token() == 'J') || (token() == 'K')) {
            match(token());
        } else
            error();
    }

    private void method() {
        accessor();
        type();
        methodname();
        if ((token() == '(')) {
            match(token());
            varlist();
            match(token());
        }
        match('B');
        while ((token() == '<') || (token() == '=') || (token() == '>') || (token() == '!') || (token() == 'Y')
                || (token() == 'Z') || (token() == '0') || (token() == '1') || (token() == '2') || (token() == '3')) {
            statemt();
        }

        returnstatement();

        match('E');
    }

    // <statemt> ::= <ifstatemt> | <assignstatemt>;|<whilestatemt>|<methodcall>
    private void statemt() {
        if ((token() == 'F')) {
            ifstatemt();
        } else if ((token() == 'Y') || (token() == 'Z') || (token() == '0') || (token() == '1') || (token() == '2')
                || (token() == '3') || (token() == '=')) {
            assignstatemt();
            match(';');
        } else if ((token() == 'W')) {
            whilestatemt();
        } else {
            methodcall();
        }
    }

    private void assignstatemt() {

        if ((token() == 'Y') || (token() == 'Z') || (token() == '0') || (token() == '1') || (token() == '2')
                || (token() == '3')) {
            match(token());
            match('=');

        }
    }

    private void whilestatemt() {

        match('W');
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

    methodcall(){
        if ((token() == 'I') || (token() == 'S') || 
        (token() == 'C') || (token() == 'D')){
            varref();
            match('.');
            methodname();

            if ((token() == '(')){

                if ((token() ==  'I') || (token() == 'S')){
                    vardef();
                    match(')');
                 }
            }
        }
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