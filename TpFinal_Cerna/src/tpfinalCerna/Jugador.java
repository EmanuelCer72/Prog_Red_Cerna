package tpfinalCerna;

public class Jugador {
    public String nombre;
    public String simbolo;
    public Tablero tablero;
    public Jugador(String n, String s, Tablero t) {
        nombre = n;
        simbolo = s;
        tablero = t;
    }
    public boolean realizarMovimiento(int f, int c) {
        return tablero.colocarSimbolo(f, c, simbolo);
    }
}
