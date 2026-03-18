package analizadorLexico;

public class Main {

    public static void main(String[] args) {

        String ruta = "C:\\Users\\dimas\\OneDrive\\Documents\\-5.txt";

        AnalizadorLexico analizador = new AnalizadorLexico();

        analizador.analizarArchivo(ruta);

        analizador.mostrarTablaSimbolos();
    }
}
