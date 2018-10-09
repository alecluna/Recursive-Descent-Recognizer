
/*
Grammar: 
<javaclass> ::= <classname> [X <classname>] B <varlist>; {<method>} E
<classname> ::= C|D
<varlist> ::= <vardef> {, <vardef>}
<vardef> ::= <type> <varname> | <classname> <varref>
<type> ::= I|S
<varname> ::= <letter> {<char>}
<letter> ::= Y|Z
<char> ::= <letter> | <digit>
<digit> ::= 0|1|2|3
<integer> ::= <digit> {<digit>}
<varref> ::= J|K
<method> ::= <accessor> <type> <methodname> ([<varlist>]) B {<statemt>} <returnstatemt> E
<accessor> ::= P|V
<methodname> ::= M|N
<statemt> ::= <ifstatemt> | <assignstatemt>;|<whilestatemt>|<methodcall>
<ifstatemt> ::= F <cond> T B {<statemt>} E [L B {<statemt>} E]
<assignstatemt> ::= <varname> = <mathexpr> | <varref> = <getvarref>
<mathexpr> ::= <factor> {+ factor}
<factor> ::= <oprnd> {* oprnd}
<oprnd> ::= <integer> | <varname> | (<mathexpr>) | <methodcall>
<getvarref> ::= O <classname>() | <methodcall>
<whilestatemt> ::= W <cond> T B {<statemt>} E
<cond> ::= (<oprnd> <operator> <oprnd>)
<operator> ::= < | = | > | !
<returnstatemt> ::= R <varname>;
<methodcall> ::= <varref>.<methodname>( [ <varlist> ] )
Note: The single letters are codes after lexical analysis for the following words:
The tokens are:
X for extends
B for Begin of block
E for End of block
I for Integer
S for String
P for public
V for private
F for if
T for then
L for else
O for new (to create a new class Object reference)
W for while
R for return
*/
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

    private void varlist() {

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

    private void statemt() { // double check this works
        if ((token() == 'F')) {
            ifstatemt();
        } else if ((token() == 'Y') || (token() == 'Z') || (token() == '0') || (token() == '1') || (token() == '2')
                || (token() == '3')) {
            assignstatemt();
            match(';');
        } else if ((token() == 'W')) {
            whilestatemt();
        } else {
            methodcall();
        }
    }

    private void ifstatemt() { // TODO fix while statement loop
        match('F');
        cond();
        match('T');
        match('B');

        // while ((token() == '')){
        // statemt();
        // }

        while ((token() == '<') || (token() == '=') || (token() == '>') || (token() == '!') || (token() == 'Y')
                || (token() == 'Z') || (token() == '0') || (token() == '1') || (token() == '2') || (token() == '3')) {
            statemt();
        }
        match('E');
        if (token() == 'L') {
            match(token());
            match('B');
            // while (statement)
            while ((token() == '<') || (token() == '=') || (token() == '>') || (token() == '!') || (token() == 'Y')
                    || (token() == 'Z') || (token() == '0') || (token() == '1') || (token() == '2')
                    || (token() == '3')) {
                statemt();
            }
            match('E');
        }
    }

    private void assignstatemt() {

        if ((token() == 'Y') || (token() == 'Z')) {
            varname();
            match('=');
            mathexpr();
        } else if ((token() == 'J') || (token() == 'K')) {
            varref();
            match('=');
            getvarref();
        } else {
            error();
        }
    }

    private void mathexpr() {

        factor();
        while (token() == '+') {
            factor();
        }
    }

    private void factor() {
        oprnd();
        while (token() == '*') {
            oprnd();
        }
    }

    private void oprnd() {

        if ((token() == '0') || (token() == '1') || (token() == '2') || (token() == '3')) {
            integer();
        } else if (token() == '(') {
            match('(');
            mathexpr();
            match(')');

        } else if ((token() == 'P') || (token() == 'V')) {
            methodcall();
        } else if ((token() == 'Y') || (token() == 'Z')) {
            varname();
        } else {
            error();
        }
    }

    private void getvarref() {
        match('O');

        if ((token() == 'C') || (token() == 'D')) {
            classname();
            match('(');
            match(')');
        } else {
            methodcall();
        }
    }

    private void whilestatemt() {

        match('W');
        cond();
        match('T');
        match('B');
        while ((token() == 'F') || (token() == 'Y') || (token() == 'Z') || (token() == '0') || (token() == '1')
                || (token() == '2') || (token() == '3') || (token() == 'W')) {
            statemt();
        }
        match('E');

    }

    private void cond() {

        if (token() == '(') {

            match(token());
            operd();
            oper();
            operd();
            match(token());
        }
    }

    private void operator() {
        if ((token() == '<') || (token() == '=') || (token() == '>') || (token() == '!')) {
            match(token());
        } else {
            error();
        }
    }

    private void returnstatemt() {

        match('R');
        varname();
        match(';');
    }

    private void methodcall() {
        varref();
        match('.');
        methodname();

        if ((token() == '(')) {
            match(token());
            if ((token() == 'I') || (token() == 'S')) {
                varlist();
            }
            match(token());
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
