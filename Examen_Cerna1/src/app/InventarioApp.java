package app;
import java.io.*;
import java.util.ArrayList;

public class InventarioApp {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static ArrayList<Producto> productos = new ArrayList<>();

    public static void main(String[] args) {
        InventarioManager.crearArchivo();
        productos = InventarioManager.leerProductos();
        menu();
    }

    public static void menu() {
        while (true) {
            System.out.println("\n---- MENU ----");
            System.out.println("1 - Agregar producto");
            System.out.println("2 - Mostrar productos");
            System.out.println("3 - Comprar/Vender");
            System.out.println("4 - Buscar por marca");
            System.out.println("5 - Salir");
            System.out.print("Opcion: ");

            try {
                int op = Integer.parseInt(br.readLine());

                if (op == 1) agregar();
                else if (op == 2) mostrar();
                else if (op == 3) comprarVender();
                else if (op == 4) buscarMarca();
                else if (op == 5) {
                    InventarioManager.guardarProductos(productos);
                    System.out.println("Cambios guardados. Adios!");
                    break;
                } else System.out.println("Opcion invalida");

            } catch (Exception e) {
                InventarioManager.escribirError(e.getMessage());
            }
        }
    }

    public static void agregar() {
        try {
            System.out.print("Nombre: ");
            String n = br.readLine();

            System.out.print("Marca: ");
            String m = br.readLine();

            System.out.print("Categoria: ");
            String c = br.readLine();

            System.out.print("Cantidad: ");
            int cant = Integer.parseInt(br.readLine());

            System.out.print("Precio: ");
            double pre = Double.parseDouble(br.readLine());

            System.out.print("Fecha (AAAA-MM-DD): ");
            String f = br.readLine();

            productos.add(new Producto(n, m, c, cant, pre, f));

        } catch (Exception e) {
            InventarioManager.escribirError(e.getMessage());
        }
    }

    public static void mostrar() {
        ArrayList<Producto> sinRep = new ArrayList<>();

        for (Producto p : productos) {
            boolean existe = false;
            for (Producto x : sinRep) {
                if (x.getNombre().equalsIgnoreCase(p.getNombre())) {
                    x.setCantidad(x.getcantidad() + p.getcantidad());
                    existe = true;
                    break;
                }
            }
            if (!existe) sinRep.add(p);
        }

        for (Producto p : sinRep) {
            System.out.println(p.toString());
        }
    }

    public static void comprarVender() {
        try {
            System.out.print("Nombre del producto: ");
            String n = br.readLine();

            Producto encontrado = null;
            for (Producto p : productos) {
                if (p.getNombre().equalsIgnoreCase(n)) {
                    encontrado = p;
                    break;
                }
            }

            if (encontrado == null) {
                System.out.println("No existe.");
                return;
            }

            System.out.print("Cantidad (+ para comprar / - para vender): ");
            int cant = Integer.parseInt(br.readLine());

            encontrado.setCantidad(encontrado.Cantidad() + cant);

        } catch (Exception e) {
            InventarioManager.escribirError(e.getMessage());
        }
    }

    public static void buscarMarca() {
        try {
            System.out.print("Marca: ");
            String m = br.readLine();

            ArrayList<Producto> lista = new ArrayList<>();

            for (Producto p : productos) {
                if (p.getMarca().equalsIgnoreCase(m)) {
                    boolean existe = false;
                    for (Producto x : lista) {
                        if (x.getNombre().equalsIgnoreCase(p.getNombre())) {
                            x.setCantidad(x.Cantidad() + p.Cantidad());
                            x.setPrecio((x.getPrecio() + p.getPrecio()) / 2);
                            existe = true;
                            break;
                        }
                    }
                    if (!existe) lista.add(p);
                }
            }

            for (Producto p : lista) {
                System.out.println(p.toString());
            }

        } catch (Exception e) {
            InventarioManager.escribirError(e.getMessage());
        }
    }
}