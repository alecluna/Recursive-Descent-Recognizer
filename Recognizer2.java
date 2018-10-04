
// --------------------------------------------// Recognizer for simple Java
// Class grammar// Written by JaganChidella9/3/2017// // to run on Athena
// (linux) -// save as: JClassRecognizer.java// compile:
// javacJClassRecognizer.java// execute: java JClassRecognizer// // EBNF Grammar
// is -//Java Class <jClass > ::= <className> B < varlist> {<method>} E//Class
// Name <className> ::= C|D//Variable List<varlist>::= <vardef>{,
// <vardef>};//Variable Declaration<vardef>::=<type> <var>//Type<type> ::= I |
// S//Variable Names<var>::= V|Z//Class Method<method> ::= P <type> <mname>
// (<vardef>{,<vardef>}) B//<stmnt> <returnstmnt>E//Method Name<mname>::= M|N//
// Statement<stmnt>::= <ifstmnt>|<assignstmnt>|<whilestmnt>// If
// statement<ifstmnt>::= F <cond>T B {<stmnt>} E [L B { <stmnt>} E]// Assignment
// Statement<assignstmnt>::= <var>= <digit>;// While Statement<whilestmnt>::= W
// <cond>T B <stmnt>{<stmnt>} E// Condition<cond>::= (<var>== <digit>)// Return
// Statement<returnstmnt>::= R <var>;////KEY: C and D are names of two Java
// classes//I stands for Integer// S stands for String//B stands for { brace//E
// stands for } brace// V and Z are names of two variables//P stands for the
// word: public//F stands for If// T stands for Then//L stands for Else//W
// stands for While//R stands for Return// Valid (Legal)stringstoENTERfor
// testing class method containingfollowing // statements:ENTER the
// corresponding string. // Response ‘Legal’validatesstring.Response ‘Errors
// Found’invalidatesstring.// IF
// statement:CBIV,SZ;PIM(IV)BF(V==9)TBV=8;ERV;EE$// IF THEN ELSE
// statement:CBIV,SZ;PSM(SZ)BF(V==9)TBV=8;ELBV=0;ERV;EE$// WHILE
// statement:CBIV,SZ;PIM(IV)BW(V==0)TBV=9;Z=2;ERV;EE$//
// Assignment:CBIV,SZ;PIM(IV)BV=8;RV;EE$// Invalid Strings:
// CBIV,SZ;PIM(IV)BF(V==9)TBV=8;E;RV;E$// Invalid Strings:
// CBIV,SZ;PIM(IV)BF(V==9)TBV=8;E;RVEE$//---------------------------------------------------------------//
// Create FIRST and FOLLOW table 70% pointsby 10/4.Showto me, get it
// corrected.// Then Write the Parser 30% pointsby 10/9. Show to me, get it
// corrected.// Recursivelyimprovebothforfull points. Seepage 2,3for snippet of
// program.

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