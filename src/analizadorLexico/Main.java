package analizadorLexico;

import sintactico.Parser;
import sintactico.Nodo;

public class Main {

    public static void main(String[] args) {

        String ruta = "C:\\Users\\dimas\\OneDrive\\Documents\\3.txt";

        AnalizadorLexico analizador = new AnalizadorLexico();

        analizador.analizarArchivo(ruta);

        analizador.mostrarTablaSimbolos();
        analizador.mostrarTablaErrores();

        Parser parser = new Parser(analizador.getTokens());
        Nodo arbol = parser.parse();

        System.out.println("\n===== ÁRBOL DE DERIVACIÓN =====");
        arbol.imprimirArbol();
    }
}
