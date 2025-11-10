public class Celda {
    String estado;
    public Celda() { estado = "VACIA"; }
    public void vaciar() { estado = "VACIA"; }
    public void marcar(String simbolo) { estado = simbolo; }
    public boolean esVacia() { return "VACIA".equals(estado); }
    public String toString() { return "VACIA".equals(estado) ? " " : estado; }
}
