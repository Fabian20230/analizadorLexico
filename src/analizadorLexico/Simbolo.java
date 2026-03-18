package analizadorLexico;

public class Simbolo {

    private int id;
    private String simbolo;
    private String tipo;

    public Simbolo(int id, String simbolo, String tipo) {
        this.id = id;
        this.simbolo = simbolo;
        this.tipo = tipo;
    }

    public int getId() {
        return id;
    }

    public String getSimbolo() {
        return simbolo;
    }

    public String getTipo() {
        return tipo;
    }
}

