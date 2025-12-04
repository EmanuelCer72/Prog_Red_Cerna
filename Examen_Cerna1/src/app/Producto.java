package app;

public class Producto {
	private String nombre;
	private String marca;
	private String categoria;
	private int cantidad;
	private double precio;
	private String fecha;
	
	public Producto(String n, String m, String c, int cant, double p, String f) {
		nombre = n;
		marca = m;
		categoria = c;
		cantidad = cant;
		precio = p;
		fecha = f;
	}            
	public String getNombre() { return nombre; }
	public String getMarca() { return marca; }
	public String getCategoria() { return categoria; }
	public int Cantidad() { return cantidad; }
	public double getPrecio() { return precio; }
	public String getFecha() { return fecha; }
	
	public void setCantidad(int c) {cantidad = c;}
	public void setPrecio(double p) {precio = p;}
	
	@Override
	public String toString() {
	    return nombre + " " + marca + " " + categoria + " " + cantidad + " " + precio + " " + fecha;
	}
}
