package analizadorLexico;

import java.io.*;
import java.util.*;
import java.util.regex.*;

public class AnalizadorLexico {

    private TablaSimbolos tabla = new TablaSimbolos();

    private Set<String> palabrasReservadas = new HashSet<>(Arrays.asList(
            "if", "else", "while", "int", "float", "print"
    ));

    private Pattern patron = Pattern.compile(
            ":=|<=|>=|<>|[+\\-*/<>]|[(){}\\[\\],;]|\\d+|[a-zA-Z][a-zA-Z0-9]*"
    );

    public void analizarArchivo(String ruta) {

        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {

            String linea;
            int numeroLinea = 1;

            while ((linea = br.readLine()) != null) {

                linea = linea.trim();

                if (linea.isEmpty()) {
                    numeroLinea++;
                    continue;
                }

                if (!linea.endsWith(";")) {

                    System.out.println(
                            "Error sintáctico en línea " + numeroLinea +
                            ": la línea debe terminar con ';'"
                    );

                    numeroLinea++;
                    continue;
                }

                analizarLinea(linea, numeroLinea);

                numeroLinea++;
            }

        } catch (IOException e) {

            System.out.println("Error al leer archivo: " + e.getMessage());
        }
    }

    private void analizarLinea(String linea, int numeroLinea) {

        Matcher matcher = patron.matcher(linea);

        int ultimoFin = 0;

        while (matcher.find()) {

            if (matcher.start() > ultimoFin) {

                String error = linea.substring(ultimoFin, matcher.start()).trim();

                if (!error.isEmpty()) {

                    System.out.println(
                            "Error léxico en línea " + numeroLinea +
                            ": símbolo no reconocido -> " + error
                    );
                }
            }

            String lexema = matcher.group();

            procesarToken(lexema);

            ultimoFin = matcher.end();
        }
    }

    private void procesarToken(String lexema) {

        if (palabrasReservadas.contains(lexema)) {

            Token token = new Token(TipoToken.PALABRA_RESERVADA, lexema);
            System.out.println(token);

            tabla.agregar(lexema, "palabra_reservada");
        }

        else if (lexema.matches("\\d+")) {

            int numero = Integer.parseInt(lexema);

            if (numero > 100) {

                System.out.println(
                        "Error léxico: número fuera de rango -> " + lexema
                );

            } else {

                Token token = new Token(TipoToken.NUMERO, lexema);
                System.out.println(token);

                tabla.agregar(lexema, "numero");
            }
        }

        else if (lexema.matches("[a-zA-Z][a-zA-Z0-9]*")) {

            if (lexema.length() > 10) {

                System.out.println(
                        "Error léxico: identificador demasiado largo -> " + lexema
                );

            } else {

                Token token = new Token(TipoToken.IDENTIFICADOR, lexema);
                System.out.println(token);

                tabla.agregar(lexema, "identificador");
            }
        }

        else if (lexema.matches(":=|<=|>=|<>|[+\\-*/<>]")) {

            Token token = new Token(TipoToken.OPERADOR, lexema);
            System.out.println(token);

            tabla.agregar(lexema, "operador");
        }

        else if (lexema.matches("[(){}\\[\\],;]")) {

            Token token = new Token(TipoToken.DELIMITADOR, lexema);
            System.out.println(token);

            tabla.agregar(lexema, "delimitador");
        }
    }

    public void mostrarTablaSimbolos() {

        tabla.mostrar();
    }
}
