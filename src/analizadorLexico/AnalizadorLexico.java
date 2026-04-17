package analizadorLexico;

import java.io.*;
import java.util.*;
import java.util.regex.*;

public class AnalizadorLexico {

	private List<Token> tokens = new ArrayList<>();
    private TablaSimbolos tabla = new TablaSimbolos();
    private TablaErrores tablaErrores = new TablaErrores();

    private Set<String> palabrasReservadas = new HashSet<>(Arrays.asList(
            "if", "else", "for", "int", "print"
    ));

    private Pattern patron = Pattern.compile(
            "\"[^\"]*asdfg[^\"]*\"|:=|<=|>=|<>|\\.\\.|[+\\-*/<>=]|[(){}\\[\\],;]|\\d+|[a-zA-Z][a-zA-Z0-9]*"
    );

    public void analizarArchivo(String ruta) {

    	tokens.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {

            String linea;
            int numeroLinea = 1;

            while ((linea = br.readLine()) != null) {

                linea = linea.trim();

                if (linea.isEmpty()) {
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

                	tablaErrores.agregar(
                	        error,
                	        "LEXICO",
                	        "Símbolo no reconocido",
                	        numeroLinea
                	);
                }
            }

            String lexema = matcher.group();

            procesarToken(lexema, numeroLinea);

            ultimoFin = matcher.end();
        }
        
        if (ultimoFin < linea.length()) {

			String error = linea.substring(ultimoFin).trim();

			if (!error.isEmpty()) {

				tablaErrores.agregar(
				        error,
				        "LEXICO",
				        "Símbolo no reconocido",
				        numeroLinea
				);
			}
		}
        
    }

    private void procesarToken(String lexema, int numeroLinea) {

        if (palabrasReservadas.contains(lexema)) {

            Token token = new Token(TipoToken.PALABRA_RESERVADA, lexema);
            System.out.println(token);

            tokens.add(token);
            tabla.agregar(lexema, "palabra_reservada");
            
        }else if (lexema.matches("\"[^\"]*asdfg[^\"]*\"")) {

            Token token = new Token(TipoToken.CADENA, lexema);
            System.out.println(token);

            tokens.add(token);
            tabla.agregar(lexema, "cadena");
        }

        else if (lexema.matches("\\d+")) {

            int numero = Integer.parseInt(lexema);

            if (numero > 100) {

            	tablaErrores.agregar(
            	        lexema,
            	        "LEXICO",
            	        "Número fuera de rango",
            	        numeroLinea
            	);


            } else {

                Token token = new Token(TipoToken.NUMERO, lexema);
                System.out.println(token);

                tokens.add(token);
                tabla.agregar(lexema, "numero");
            }
        }

        else if (lexema.matches("[a-zA-Z][a-zA-Z0-9]*")) {

            if (lexema.length() > 10) {

            	tablaErrores.agregar(
            	        lexema,
            	        "LEXICO",
            	        "Identificador demasiado largo",
            	        numeroLinea
            	);

            } else {

                Token token = new Token(TipoToken.IDENTIFICADOR, lexema);
                System.out.println(token);
                
				tokens.add(token);
                tabla.agregar(lexema, "identificador");
            }
        }

        else if (lexema.matches(":=|<=|>=|<>|\\.\\.|[+\\-*/<>=]")) {

            Token token = new Token(TipoToken.OPERADOR, lexema);
            System.out.println(token);
            
            tokens.add(token);
            tabla.agregar(lexema, "operador");
        }

        else if (lexema.matches("[(){}\\[\\],;]")) {

            Token token = new Token(TipoToken.DELIMITADOR, lexema);
            System.out.println(token);
            tokens.add(token);
            tabla.agregar(lexema, "delimitador");
        }
    }

    public void mostrarTablaSimbolos() {
        tabla.mostrar();
    }
    
    public void mostrarTablaErrores() {
        tablaErrores.mostrar();
    }

    
    public List<Token> getTokens() {

        if (tokens.isEmpty() || 
            tokens.get(tokens.size()-1).getTipo() != TipoToken.EOF) {

            tokens.add(new Token(TipoToken.EOF, "EOF"));
        }

        return tokens;
    }

}
