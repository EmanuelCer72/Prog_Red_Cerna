package ar.edu.et32.ejercicio1;

public class HiloAlfanumerico implements Runnable {
    private int tipo;

    public HiloAlfanumerico(int tipo) {
        this.tipo = tipo;
    }

    @Override
    public void run() {
        if (tipo == 1) {
            for (int i = 1; i <= 30; i++) {
                System.out.println("Número: " + i);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } else if (tipo == 2) {
            for (char c = 'a'; c <= 'z'; c++) {
                System.out.println("Letra: " + c);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
