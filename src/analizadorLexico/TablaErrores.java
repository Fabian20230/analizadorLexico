
package analizadorLexico;

import java.util.ArrayList;
import java.util.List;

public class TablaErrores {

    private List<ErrorCompilador> errores = new ArrayList<>();
    private int contador = 1;

    public void agregar(String lexema, String tipoError, String descripcion, int linea) {

        ErrorCompilador error = new ErrorCompilador(contador, lexema, tipoError, descripcion, linea);

        errores.add(error);
        contador++;
    }

    public void mostrar() {

        System.out.println("\nTABLA DE ERRORES");
        System.out.println("ID   LEXEMA        TIPO ERROR    DESCRIPCION                     LINEA");

        for (ErrorCompilador e : errores) {

            System.out.printf(
                    "%-4d %-13s %-13s %-30s %-5d\n",
                    e.getId(),
                    e.getLexema(),
                    e.getTipoError(),
                    e.getDescripcion(),
                    e.getLinea()
            );
        }
    }
}
