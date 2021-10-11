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

	/*
	 * Método inicialización de programa.
	 */
	public void initEventHandlers() {
		
		ficheroLectura = modelo.ficheroLectura();
		ficheroEscritura = modelo.ficheroEscritura();
		
		mostrarFichero(ficheroLectura, 1);
	
		//Asignamos el método buscarTexto() al botón correspondiente.
		actionListenerSearch = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				String textToSearch = vista.getTextFieldBuscar().getText();
				modelo.buscarTexto(textToSearch);
			}
		};
		vista.getBtnBuscar().addActionListener(actionListenerSearch);
		
		//Asignamos el método reemplazarTexto() al botón correspondiente.
		actionListenerReplace = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				String textToReplace = vista.getTextFieldBuscar().getText();
				modelo.reemplazarTexto(textToReplace, vista.getTextFieldReemplazar().getText());
				mostrarFichero(ficheroEscritura, 2);
			}
		};
		vista.getBtnReemplazar().addActionListener(actionListenerReplace);
	}

	
	/*
	 * Método para imprimir en el area correspondiente (indicada por parámetros) el contenido del fichero indicado (también indicado por parámetros).
	 */
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
