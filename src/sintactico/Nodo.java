package sintactico;

import java.util.ArrayList;
import java.util.List;

public class Nodo {

    private String valor;
    private List<Nodo> hijos;

    public Nodo(String valor) {
        this.valor = valor;
        this.hijos = new ArrayList<>();
    }

    public void agregarHijo(Nodo hijo) {
        hijos.add(hijo);
    }

    public String getValor() {
        return valor;
    }

    public List<Nodo> getHijos() {
        return hijos;
    }

    public void imprimirArbol() {
        System.out.println(valor);

        for (int i = 0; i < hijos.size(); i++) {
            hijos.get(i).imprimir("", i == hijos.size() - 1);
        }
    }

    private void imprimir(String prefijo, boolean esUltimo) {

        System.out.println(prefijo + (esUltimo ? "└── " : "├── ") + valor);

        for (int i = 0; i < hijos.size(); i++) {
            hijos.get(i).imprimir(
                prefijo + (esUltimo ? "    " : "│   "),
                i == hijos.size() - 1
            );
        }
    }
}
