package tp_files;

import java.io.File;

public class Main {

    public static void main(String[] args) {
        EntradaDatos entrada = new EntradaDatos();
        int[] vector = entrada.IngresarDatosVector();


        ManejadorArchivo archivo = new ManejadorArchivo("datos.txt");
        archivo.crearFileConBuffer(archivo.getFiles(), vector);


        Procesos proc = new Procesos();
        proc.procesarVector(vector);
    }
}
