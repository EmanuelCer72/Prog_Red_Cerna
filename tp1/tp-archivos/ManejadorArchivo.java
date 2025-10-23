package tp_files;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ManejadorArchivo {
	
	PrintStream ps;
	File file;
	File filee;	
	
	public ManejadorArchivo(String ruta){
		ps = new PrintStream(System.out);
		file = new File(ruta);
	}
	
	public File getFiles()
	{
		return this.file;
	}
	
	
	public void crearFileConBuffer(File f, int[] array) {
		FileWriter fw = null;
		BufferedWriter bw = null;// escritor

		try {
			fw = new FileWriter(f, false); // true = append
			bw = new BufferedWriter(fw);
			
			for(int i = 0; i < array.length; i++) {
				bw.append(String.valueOf(array[i]));
				bw.newLine();
			}
			

		} catch (IOException e) {
			Logger.getLogger(ManejadorArchivo.class.getName()).log(Level.WARNING, null, e);
		} finally {
			try {
				if (bw != null)
					bw.close();
				if (fw != null)
					fw.close();
			} catch (IOException e) {
				Logger.getLogger(ManejadorArchivo.class.getName()).log(Level.WARNING, null, e);
			}
		}

	}
	
	public String leerFileCaracterCaracter(File f) {
		FileReader fr = null;

		try {
			fr = new FileReader(f);

			int caracter, EOF = -1;
			String texto = "";
			while ((caracter = fr.read()) != EOF) {

				if (caracter == '\n') {
					texto = texto.concat("\n");
				} else {
					texto = texto.concat(String.valueOf(caracter));
				}
			}
			return texto;
		} catch (IOException e) {
			Logger.getLogger(ManejadorArchivo.class.getName()).log(Level.WARNING, null, e);
		} finally {
			try {
				if (fr != null)
					fr.close();
			} catch (IOException e) {
				Logger.getLogger(ManejadorArchivo.class.getName()).log(Level.WARNING, null, e);
			}
		}
		return null;
	}
	

	
}
