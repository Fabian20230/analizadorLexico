package analizadorLexico;

public class ErrorCompilador {

    private int id;
    private String lexema;
    private String tipoError;
    private String descripcion;
    private int linea;

    public ErrorCompilador(int id, String lexema, String tipoError, String descripcion, int linea) {
        this.id = id;
        this.lexema = lexema;
        this.tipoError = tipoError;
        this.descripcion = descripcion;
        this.linea = linea;
    }

    public int getId() {
        return id;
    }

    public String getLexema() {
        return lexema;
    }

    public String getTipoError() {
        return tipoError;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getLinea() {
        return linea;
    }
}

