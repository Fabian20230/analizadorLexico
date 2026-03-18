package analizadorLexico;

import java.util.LinkedHashMap;
import java.util.Map;

public class TablaSimbolos {

    private Map<String, Simbolo> tabla = new LinkedHashMap<>();
    private int contador = 1;

    public void agregar(String simbolo, String tipo) {

        if (!tabla.containsKey(simbolo)) {

            Simbolo nuevo = new Simbolo(contador, simbolo, tipo);

            tabla.put(simbolo, nuevo);

            contador++;
        }
    }

    public void mostrar() {

        System.out.println("\nTABLA DE SIMBOLOS");
        System.out.println("ID   SIMBOLO        TIPO");

        for (Simbolo s : tabla.values()) {

            System.out.printf(
                    "%-4d %-13s %-15s\n",
                    s.getId(),
                    s.getSimbolo(),
                    s.getTipo()
            );
        }
    }
}
