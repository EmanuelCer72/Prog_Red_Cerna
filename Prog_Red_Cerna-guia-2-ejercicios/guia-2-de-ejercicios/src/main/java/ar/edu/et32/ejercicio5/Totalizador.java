package ar.edu.et32.ejercicio5;

import java.util.concurrent.atomic.AtomicInteger;

public class Totalizador {
    private AtomicInteger totalLineas = new AtomicInteger(0);

    public void agregarLineas(int cantidad) {
        totalLineas.addAndGet(cantidad);
    }

    public int getTotalLineas() {
        return totalLineas.get();
    }
}
