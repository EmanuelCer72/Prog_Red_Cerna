package tpfinalCerna;

public class Tablero {

    public Celda[][] tablero;

    public Tablero() {
        tablero = new Celda[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                tablero[i][j] = new Celda();
            }
        }
    }

    public boolean colocarSimbolo(int f, int c, String simbolo) {
        if (f < 0 || f > 2 || c < 0 || c > 2) {
            return false;
        }
        if (tablero[f][c].esVacia()) {
            tablero[f][c].marcar(simbolo);
            return true;
        }
        return false;
    }

    public boolean esGanador(String s) {
        for (int i = 0; i < 3; i++) {
            if (tablero[i][0].estado.equals(s) &&
                tablero[i][1].estado.equals(s) &&
                tablero[i][2].estado.equals(s)) {
                return true;
            }
        }

        for (int j = 0; j < 3; j++) {
            if (tablero[0][j].estado.equals(s) &&
                tablero[1][j].estado.equals(s) &&
                tablero[2][j].estado.equals(s)) {
                return true;
            }
        }

        if (tablero[0][0].estado.equals(s) &&
            tablero[1][1].estado.equals(s) &&
            tablero[2][2].estado.equals(s)) {
            return true;
        }

        if (tablero[0][2].estado.equals(s) &&
            tablero[1][1].estado.equals(s) &&
            tablero[2][0].estado.equals(s)) {
            return true;
        }

        return false;
    }

    public boolean esEmpate() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (tablero[i][j].esVacia()) {
                    return false;
                }
            }
        }
        return true;
    }

    public String mostrar() {
        String t = "";
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (tablero[i][j].estado.equals("VACIA")) {
                    t += ".";
                } else {
                    t += tablero[i][j].estado;
                }
            }
            t += "\n";
        }
        return t;
    }
}
