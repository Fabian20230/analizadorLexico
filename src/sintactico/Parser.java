//package sintactico;
//
//import analizadorLexico.Token;
//import analizadorLexico.TipoToken;
//
//import java.util.List;
//
//public class Parser {
//
//    private List<Token> tokens;
//    private int pos = 0;
//
//    public Parser(List<Token> tokens) {
//        this.tokens = tokens;
//    }
//
//    // =============================
//    // UTILIDADES
//    // =============================
//
//    private Token actual() {
//        if (pos < tokens.size()) {
//            return tokens.get(pos);
//        }
//        return new Token(TipoToken.EOF, "EOF");
//    }
//
//    private void avanzar() {
//        if (pos < tokens.size()) pos++;
//    }
//
//    private boolean matchLexema(String lexema) {
//        if (actual().getLexema().equals(lexema)) {
//            avanzar();
//            return true;
//        } else {
//            error("Se esperaba '" + lexema + "'");
//            return false;
//        }
//    }
//
//    private boolean matchTipo(TipoToken tipo) {
//        if (actual().getTipo() == tipo) {
//            avanzar();
//            return true;
//        } else {
//            error("Se esperaba tipo " + tipo);
//            return false;
//        }
//    }
//
//    private void error(String msg) {
//        System.out.println("Error sintáctico: " + msg + " en '" + actual().getLexema() + "'");
//    }
//
//    // =============================
//    // PROGRAMA
//    // =============================
//
//    public Nodo parse() {
//
//        Nodo raiz = new Nodo("PROGRAMA");
//
//        while (actual().getTipo() != TipoToken.EOF) {
//            Nodo stmt = sentencia();
//
//            if (stmt != null) {
//                raiz.agregarHijo(stmt);
//            }
//        }
//
//        System.out.println("Parsing finalizado.");
//        return raiz;
//    }
//
//    // =============================
//    // SENTENCIAS
//    // =============================
//
//    private Nodo sentencia() {
//
//        Token t = actual();
//
//        if (t.getTipo() == TipoToken.PALABRA_RESERVADA &&
//            t.getLexema().equals("int")) {
//
//            Nodo n = declaracion();
//            matchLexema(";");
//            return n;
//        }
//
//        else if (t.getTipo() == TipoToken.IDENTIFICADOR) {
//
//            Nodo n = asignacion();
//            matchLexema(";");
//            return n;
//        }
//
//        else if (t.getTipo() == TipoToken.PALABRA_RESERVADA &&
//                 t.getLexema().equals("if")) {
//
//            return if_stmt();
//        }
//
//        else if (t.getTipo() == TipoToken.PALABRA_RESERVADA &&
//                 t.getLexema().equals("for")) {
//
//            return for_stmt();
//        }
//
//        else if (t.getTipo() == TipoToken.PALABRA_RESERVADA &&
//                 t.getLexema().equals("print")) {
//
//            Nodo n = print_stmt();
//            matchLexema(";");
//            return n;
//        }
//
//        else {
//            error("Sentencia inválida");
//            avanzar();
//            return null;
//        }
//    }
//
//    // =============================
//    // DECLARACIÓN
//    // =============================
//
//    private Nodo declaracion() {
//
//        Nodo nodo = new Nodo("DECLARACION");
//
//        matchLexema("int");
//
//        Token id = actual();
//        matchTipo(TipoToken.IDENTIFICADOR);
//
//        nodo.agregarHijo(new Nodo(id.getLexema()));
//
//        return nodo;
//    }
//
//    // =============================
//    // ASIGNACIÓN
//    // =============================
//
//    private Nodo asignacion() {
//
//        Nodo nodo = new Nodo("ASIGNACION");
//
//        Token id = actual();
//        matchTipo(TipoToken.IDENTIFICADOR);
//
//        matchLexema(":=");
//
//        Nodo expr = expresion();
//
//        nodo.agregarHijo(new Nodo(id.getLexema()));
//        nodo.agregarHijo(expr);
//
//        return nodo;
//    }
//
//    // =============================
//    // IF
//    // =============================
//
//    private Nodo if_stmt() {
//
//        Nodo nodo = new Nodo("IF");
//
//        matchLexema("if");
//        matchLexema("(");
//
//        Nodo condicion = expresion();
//
//        matchLexema(")");
//
//        Nodo cuerpo = sentencia();
//
//        nodo.agregarHijo(condicion);
//        nodo.agregarHijo(cuerpo);
//
//        return nodo;
//    }
//
//    // =============================
//    // FOR
//    // =============================
//
//    private Nodo for_stmt() {
//
//        Nodo nodo = new Nodo("FOR");
//
//        matchLexema("for");
//
//        Token id = actual();
//        matchTipo(TipoToken.IDENTIFICADOR);
//
//        matchLexema(":=");
//
//        Nodo valor = expresion();
//
//        matchLexema(";");
//
//        nodo.agregarHijo(new Nodo(id.getLexema()));
//        nodo.agregarHijo(valor);
//
//        return nodo;
//    }
//
//    // =============================
//    // PRINT
//    // =============================
//
//    private Nodo print_stmt() {
//
//        Nodo nodo = new Nodo("PRINT");
//
//        matchLexema("print");
//        matchLexema("(");
//
//        Nodo expr = expresion();
//
//        matchLexema(")");
//
//        nodo.agregarHijo(expr);
//
//        return nodo;
//    }
//
//    // =============================
//    // EXPRESIONES (ÁRBOL PROFUNDO)
//    // =============================
//
//    private Nodo expresion() {
//
//        Nodo nodo = new Nodo("EXPRESION");
//
//        Nodo izq = termino();
//        nodo.agregarHijo(izq);
//
//        while (actual().getLexema().equals("+") ||
//               actual().getLexema().equals("-")) {
//
//            Token op = actual();
//            avanzar();
//
//            Nodo der = termino();
//
//            Nodo opNodo = new Nodo(op.getLexema());
//            opNodo.agregarHijo(nodo);
//            opNodo.agregarHijo(der);
//
//            nodo = opNodo;
//        }
//
//        return nodo;
//    }
//
//    private Nodo termino() {
//
//        Nodo nodo = new Nodo("TERMINO");
//
//        Nodo izq = factor();
//        nodo.agregarHijo(izq);
//
//        while (actual().getLexema().equals("*") ||
//               actual().getLexema().equals("/")) {
//
//            Token op = actual();
//            avanzar();
//
//            Nodo der = factor();
//
//            Nodo opNodo = new Nodo(op.getLexema());
//            opNodo.agregarHijo(nodo);
//            opNodo.agregarHijo(der);
//
//            nodo = opNodo;
//        }
//
//        return nodo;
//    }
//
//    private Nodo factor() {
//
//        Nodo nodo = new Nodo("FACTOR");
//
//        Token t = actual();
//
//        if (t.getTipo() == TipoToken.NUMERO) {
//            avanzar();
//            nodo.agregarHijo(new Nodo(t.getLexema()));
//
//        } else if (t.getTipo() == TipoToken.IDENTIFICADOR) {
//            avanzar();
//            nodo.agregarHijo(new Nodo(t.getLexema()));
//
//        } else if (t.getLexema().equals("(")) {
//            avanzar();
//            Nodo n = expresion();
//            matchLexema(")");
//            nodo.agregarHijo(n);
//
//        } else {
//            error("Factor inválido");
//            avanzar();
//        }
//
//        return nodo;
//    }
//}

package sintactico;

import analizadorLexico.Token;
import analizadorLexico.TipoToken;

import java.util.List;

public class Parser {

    private List<Token> tokens;
    private int pos = 0;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
    }
    
    private Token actual() {
        if (pos < tokens.size()) {
            return tokens.get(pos);
        }
        return new Token(TipoToken.EOF, "EOF");
    }

    private void avanzar() {
        if (pos < tokens.size()) pos++;
    }

    private boolean matchLexema(String lexema) {
        if (actual().getLexema().equals(lexema)) {
            avanzar();
            return true;
        } else {
            error("Se esperaba '" + lexema + "'");
            return false;
        }
    }

    private boolean matchTipo(TipoToken tipo) {
        if (actual().getTipo() == tipo) {
            avanzar();
            return true;
        } else {
            error("Se esperaba tipo " + tipo);
            return false;
        }
    }

    private void error(String msg) {
        System.out.println("Error sintáctico: " + msg + " en '" + actual().getLexema() + "'");
    }


    public Nodo parse() {

        Nodo raiz = new Nodo("PROGRAMA");

        while (actual().getTipo() != TipoToken.EOF) {
            Nodo stmt = sentencia();

            if (stmt != null) {
                raiz.agregarHijo(stmt);
            }
        }

        System.out.println("Parsing finalizado.");
        return raiz;
    }


    private Nodo sentencia() {

        Token t = actual();

        if (t.getTipo() == TipoToken.PALABRA_RESERVADA &&
            t.getLexema().equals("int")) {

            Nodo n = declaracion();
            matchLexema(";");
            return n;
        }

        else if (t.getTipo() == TipoToken.IDENTIFICADOR) {

            Nodo n = asignacion();
            matchLexema(";");
            return n;
        }

        else if (t.getTipo() == TipoToken.PALABRA_RESERVADA &&
                 t.getLexema().equals("if")) {

            return if_stmt();
        }

        else if (t.getTipo() == TipoToken.PALABRA_RESERVADA &&
                 t.getLexema().equals("for")) {

            return for_stmt();
        }

        else if (t.getTipo() == TipoToken.PALABRA_RESERVADA &&
                 t.getLexema().equals("print")) {

            Nodo n = print_stmt();
            matchLexema(";");
            return n;
        }

        else {
            error("Sentencia inválida");
            avanzar();
            return null;
        }
    }


    private Nodo declaracion() {

        Nodo nodo = new Nodo("DECLARACION");

        nodo.agregarHijo(new Nodo("int")); 
        matchLexema("int");

        Token id = actual();
        matchTipo(TipoToken.IDENTIFICADOR);

        nodo.agregarHijo(new Nodo(id.getLexema()));

        return nodo;
    }

    private Nodo asignacion() {

        Nodo nodo = new Nodo("ASIGNACION");

        Token id = actual();
        matchTipo(TipoToken.IDENTIFICADOR);

        nodo.agregarHijo(new Nodo(id.getLexema()));

        matchLexema(":=");
        nodo.agregarHijo(new Nodo(":=")); 

        Nodo expr = expresion();
        nodo.agregarHijo(expr);

        return nodo;
    }


    private Nodo if_stmt() {

        Nodo nodo = new Nodo("IF");

        nodo.agregarHijo(new Nodo("if"));
        matchLexema("if");

        matchLexema("(");

        Nodo condicion = expresion();

        matchLexema(")");

        Nodo cuerpo = sentencia();

        nodo.agregarHijo(condicion);
        nodo.agregarHijo(cuerpo);

        return nodo;
    }


    private Nodo for_stmt() {

        Nodo nodo = new Nodo("FOR");

        nodo.agregarHijo(new Nodo("for"));
        matchLexema("for");

        Token id = actual();
        matchTipo(TipoToken.IDENTIFICADOR);

        nodo.agregarHijo(new Nodo(id.getLexema()));

        matchLexema(":=");
        nodo.agregarHijo(new Nodo(":="));

        Nodo valor = expresion();

        matchLexema(";");

        nodo.agregarHijo(valor);

        return nodo;
    }

    private Nodo print_stmt() {

        Nodo nodo = new Nodo("PRINT");

        nodo.agregarHijo(new Nodo("print"));
        matchLexema("print");

        matchLexema("(");

        Nodo expr = expresion();

        matchLexema(")");

        nodo.agregarHijo(expr);

        return nodo;
    }

//    private Nodo expresion() {
//
//        Nodo izquierdo = termino();
//
//        while (actual().getLexema().equals("+") ||
//               actual().getLexema().equals("-")) {
//
//            Token op = actual();
//            avanzar();
//
//            Nodo derecho = termino();
//
//            Nodo nuevo = new Nodo("EXPRESION");
//            nuevo.agregarHijo(izquierdo);
//            nuevo.agregarHijo(new Nodo(op.getLexema())); // operador visible
//            nuevo.agregarHijo(derecho);
//
//            izquierdo = nuevo;
//        }
//
//        return izquierdo;
//    }
    private Nodo expresion() {

        Nodo nodo = new Nodo("EXPRESION");

        Nodo primerTermino = termino();
        nodo.agregarHijo(primerTermino);

        while (actual().getLexema().equals("+") ||
               actual().getLexema().equals("-")) {

            Token op = actual();
            avanzar();

            nodo.agregarHijo(new Nodo(op.getLexema())); 

            Nodo siguiente = termino();
            nodo.agregarHijo(siguiente);
        }

        return nodo;
    }


//    private Nodo termino() {
//
//        Nodo izquierdo = factor();
//
//        while (actual().getLexema().equals("*") ||
//               actual().getLexema().equals("/")) {
//
//            Token op = actual();
//            avanzar();
//
//            Nodo derecho = factor();
//
//            Nodo nuevo = new Nodo("TERMINO");
//            nuevo.agregarHijo(izquierdo);
//            nuevo.agregarHijo(new Nodo(op.getLexema())); // operador visible
//            nuevo.agregarHijo(derecho);
//
//            izquierdo = nuevo;
//        }
//
//        return izquierdo;
//    }

    private Nodo termino() {

        Nodo nodo = new Nodo("TERMINO");

        Nodo primerFactor = factor();
        nodo.agregarHijo(primerFactor);

        while (actual().getLexema().equals("*") ||
               actual().getLexema().equals("/")) {

            Token op = actual();
            avanzar();

            nodo.agregarHijo(new Nodo(op.getLexema())); 

            Nodo siguiente = factor();
            nodo.agregarHijo(siguiente);
        }

        return nodo;
    }

    
    private Nodo factor() {

        Nodo nodo = new Nodo("FACTOR");

        Token t = actual();

        if (t.getTipo() == TipoToken.NUMERO) {
            avanzar();
            nodo.agregarHijo(new Nodo(t.getLexema()));

        } else if (t.getTipo() == TipoToken.IDENTIFICADOR) {
            avanzar();
            nodo.agregarHijo(new Nodo(t.getLexema()));

        } else if (t.getLexema().equals("(")) {
            avanzar();
            Nodo n = expresion();
            matchLexema(")");
            nodo.agregarHijo(n);

        } else {
            error("Factor inválido");
            avanzar();
        }

        return nodo;
    }
}








