package app;
import java.io.*;
import java.util.ArrayList;

public class InventarioManager {

    public static void crearArchivo() {
        File f = new File("inventario.csv");
        if (!f.exists()) {
            try {
                PrintWriter pw = new PrintWriter(new FileWriter(f));
                pw.println("Laptop;HP;Electrónica;10;1500;2023-10-01");
                pw.println("Smartphone;Samsung;Electrónica;15;1000;2023-10-02");
                pw.println("Auriculares;Logitech;Audio;50;200;2023-10-03");
                pw.println("Smartphone;Samsung;Electrónica;35;1300;2023-10-02");
                pw.close();
            } catch (Exception e) {
                escribirError(e.getMessage());
            }
        }
    }

    public static void escribirError(String msg) {
        try {
            PrintWriter pw = new PrintWriter(new FileWriter("errores.log", true));
            pw.println(msg);
            pw.close();
        } catch (Exception e) {}
    }

    public static ArrayList<Producto> leerProductos() {
        ArrayList<Producto> lista = new ArrayList<>();

        try {
            BufferedReader br = new BufferedReader(new FileReader("inventario.csv"));
            String linea;

            while ((linea = br.readLine()) != null) {

                String[] datos = linea.split(";");

                Producto p = new Producto(
                    datos[0],                           
                    datos[1],                         
                    datos[2],                           
                    Integer.parseInt(datos[3]),        
                    Double.parseDouble(datos[4]),      
                    datos[5]                            
                );
                lista.add(p);
            }
            br.close();
        } catch (Exception e) {
            escribirError(e.getMessage());
        }
        return lista;
    }


    public static void guardarProductos(ArrayList<Producto> lista) {
        try {
            PrintWriter pw = new PrintWriter(new FileWriter("inventario.csv"));
            for (Producto p : lista) {
                pw.println(p.getNombre() + ";" + p.getMarca() + ";" +
                           p.getCategoria() + ";" + p.Cantidad() + ";" +
                           p.getPrecio() + ";" + p.getFecha());
            }
            pw.close();
        } catch (Exception e) {
            escribirError(e.getMessage());
        }
    }
}