package tpfinalCerna;

public class Celda {
    public String estado;
    public Celda() {
        estado = "VACIA";
    }
    public void vaciar() {
        estado = "VACIA";
    }
    public void marcar(String simbolo) {
        estado = simbolo;
    }
    public boolean esVacia() {
        return estado.equals("VACIA");
    }
}
