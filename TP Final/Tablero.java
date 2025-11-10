public class Tablero {
    Celda[][] tablero;
    public Tablero() {
        tablero = new Celda[3][3];
        for (int i=0;i<3;i++) for (int j=0;j<3;j++) tablero[i][j]=new Celda();
    }
    public synchronized boolean colocarSimbolo(int fila, int col, String simbolo) {
        if (fila<0||fila>2||col<0||col>2) return false;
        if (!tablero[fila][col].esVacia()) return false;
        tablero[fila][col].marcar(simbolo);
        return true;
    }
    public synchronized boolean esGanador(String simbolo) {
        for (int i=0;i<3;i++) if (tablero[i][0].estado.equals(simbolo)&&tablero[i][1].estado.equals(simbolo)&&tablero[i][2].estado.equals(simbolo)) return true;
        for (int j=0;j<3;j++) if (tablero[0][j].estado.equals(simbolo)&&tablero[1][j].estado.equals(simbolo)&&tablero[2][j].estado.equals(simbolo)) return true;
        if (tablero[0][0].estado.equals(simbolo)&&tablero[1][1].estado.equals(simbolo)&&tablero[2][2].estado.equals(simbolo)) return true;
        if (tablero[0][2].estado.equals(simbolo)&&tablero[1][1].estado.equals(simbolo)&&tablero[2][0].estado.equals(simbolo)) return true;
        return false;
    }
    public synchronized boolean esTableroCompleto() {
        for (int i=0;i<3;i++) for (int j=0;j<3;j++) if (tablero[i][j].esVacia()) return false;
        return true;
    }
    public synchronized boolean esEmpate() {
        return esTableroCompleto() && !esGanador("X") && !esGanador("O");
    }
    public synchronized String mostrarTablero() {
        StringBuilder sb = new StringBuilder();
        sb.append("  1 2 3\n");
        for (int i=0;i<3;i++) {
            sb.append((i+1)+" ");
            for (int j=0;j<3;j++) {
                sb.append(tablero[i][j].toString());
                if (j<2) sb.append("|");
            }
            sb.append("\n");
            if (i<2) sb.append("  -+-+-\n");
        }
        return sb.toString();
    }
}
