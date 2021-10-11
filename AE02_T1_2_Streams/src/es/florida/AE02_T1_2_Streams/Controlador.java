package es.florida.AE02_T1_2_Streams;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Controlador {

	private Vista vista;
	private Modelo modelo;
	private ActionListener actionListenerSearch;
	private ActionListener actionListenerReplace;
	private String ficheroLectura, ficheroEscritura;
	
	
	public Controlador(Vista vista, Modelo modelo) {
		this.vista = vista;
		this.modelo = modelo;
		initEventHandlers();
	}

	public void initEventHandlers() {
		
		ficheroLectura = modelo.ficheroLectura();
		ficheroEscritura = modelo.ficheroEscritura();
		
		mostrarFichero(ficheroLectura, 1);
	
		actionListenerSearch = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				String textToSearch = vista.getTextFieldBuscar().getText();
				modelo.buscarTexto(textToSearch);
//				mostrarFichero(ficheroLectura, 2);
			}
		};
		vista.getBtnBuscar().addActionListener(actionListenerSearch);
		
		actionListenerReplace = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				String textToReplace = vista.getTextFieldBuscar().getText();
				modelo.reemplazarTexto(textToReplace, vista.getTextFieldReemplazar().getText());
				mostrarFichero(ficheroEscritura, 2);
			}
		};
		vista.getBtnReemplazar().addActionListener(actionListenerReplace);
	}

	
	public void mostrarFichero(String fichero, int numeroTextArea) {
		ArrayList<String> arrayLineas = modelo.contenidoFichero(fichero);
		for(String linea : arrayLineas) {
			if(numeroTextArea == 1)
				vista.getTextAreaOriginal().append(linea+"\n");
			else
				vista.getTextAreaModificado().append(linea+"\n");
		}
	}
}
