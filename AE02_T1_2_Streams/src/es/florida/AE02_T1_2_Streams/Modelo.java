package es.florida.AE02_T1_2_Streams;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Modelo {

	private String fichero_lectura;
	private String fichero_escritura;
	
	public Modelo() {
		fichero_lectura = "AE02_T1_2_Streams_Groucho.txt";
		fichero_escritura = "ficheroEscritura.txt";
	}
	
	public ArrayList<String> contenidoFichero(String fichero){
		//Este método devuelve una lista de string con el contenido del fichero (cada elemento de la lista es una linea del fichero).
		
		ArrayList<String> contenidoFichero = new ArrayList<String>();
		File f = new File(fichero);
		try {
			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);
			String linea = br.readLine();
			while(linea != null) {
				contenidoFichero.add(linea);
				linea = br.readLine();
			}
			br.close();
			fr.close();
		} catch(Exception e) {
			JOptionPane.showMessageDialog(new JFrame(), e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
		}
		
		return contenidoFichero;
	}
	
	public  String ficheroLectura() {
		return fichero_lectura;
	}
	
	public String ficheroEscritura() {
		return fichero_escritura;
	}
	
	public int buscarTexto(String textToSearch) {
		
		File f1 = new File(ficheroLectura());
		try {
			
			String[] words = null;
			FileReader fr = new FileReader(f1);
			BufferedReader br = new BufferedReader(fr);
			
			String linea;
			int count = 0;
			
			while((linea = br.readLine()) != null) {
				words = linea.split(" ");
				for(String word : words) {
					if(word.equals(textToSearch)) {
						count++;
					}
				}
			}
			
			fr.close();
			br.close();
			
			JOptionPane.showMessageDialog(new JFrame(), "La palabra indicada aparece "+count+ " veces en el fichero.", "SUUUU", JOptionPane.INFORMATION_MESSAGE);
			
			return count;

			
		} catch(Exception e) {
			JOptionPane.showMessageDialog(new JFrame(), e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
		}
		
		return 0;
	}
	
	public void reemplazarTexto(String textToSearch, String finalText) {
		try {
			FileReader fr = new FileReader(ficheroLectura());
			BufferedReader br = new BufferedReader(fr);
			
			FileWriter fw = new FileWriter(ficheroEscritura());
			BufferedWriter bw = new BufferedWriter(fw);
			
			String linea = br.readLine();
			while(linea != null) {
				bw.write(linea.replaceAll(textToSearch, finalText));
				bw.newLine();
				linea = br.readLine();
			}
			
			br.close();
			fr.close();
			bw.close();
			fw.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(new JFrame(), e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
		}
	}
}
