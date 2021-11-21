package es.florida.AE03_T1_3_XML;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Biblioteca {
	
	private static ArrayList<Libro> lista = new ArrayList<Libro>();
	private static String ficheroXML = "biblioteca.xml";
	public static int ultimoId = 0; 
	
	public static void main(String[] args) {

		lista = recuperarTodos();
		Scanner input = new Scanner(System.in);
		int choice = 0;
		int id;
		
		while (choice != 6) {
			System.out.println("\n\n1. Mostrar todos los títulos de la biblioteca");
			System.out.println("2. Mostrar información detallada de un libro");
			System.out.println("3. Crear nuevo libro");
			System.out.println("4. Actualizar libro");
			System.out.println("5. Borrar libro");
			System.out.println("6. Cerrar la biblioteca");
			System.out.println("");
			System.out.println("INDICA LA ACCIÓN A REALIZAR: ");
			choice = Integer.parseInt(input.next());
			
			switch(choice) {
			case 1:
				mostrarLibrosBiblioteca();
				break;
			case 2:
				System.out.print("Indica el id del libro a mostrar: ");
				id = Integer.parseInt(input.next());
				mostrarLibro(recuperarLibro(id));
				break;
			case 3:
				
				String titulo,autor,editorial;
				int anoPublicacion, numPaginas;
				
				Scanner inputTeclado = new Scanner(System.in);
				
				System.out.print("Indica el título: ");
				titulo = inputTeclado.next();
				System.out.print("Indica el autor: ");
				autor = inputTeclado.next();
				System.out.print("Indica el año de publicación: ");
				anoPublicacion = Integer.parseInt(inputTeclado.next());
				System.out.print("Indica la editorial: ");
				editorial = inputTeclado.next();
				System.out.print("Indica el número de páginas: ");
				numPaginas = Integer.parseInt(inputTeclado.next());
				
				int identificadorNuevoLibro = crearLibro(new Libro(++ultimoId, titulo, autor, anoPublicacion, editorial, numPaginas));
				System.out.println("Creado libro con ID " + identificadorNuevoLibro);
				
				break;
			case 4:
				System.out.print("Indica el id del libro a actualizar: ");
				id = Integer.parseInt(input.next());
				actualizarLibro(id);
				break;
			case 5:
				System.out.print("Indica el id del libro a borrar: ");
				id = Integer.parseInt(input.next());
				borrarLibro(id);
				break;
			case 6:
				System.out.println("Cerrando biblioteca");
				input.close();
				break;
			default:
				break;
			}
		}
	}

	/*
	 * Muestra todos los libro de la biblioteca. 
	 */
	public static void mostrarLibrosBiblioteca() {
		System.out.println("\nContenido de la biblioteca: ");
		for (Libro libro : lista) {
			System.out.println(libro.getIdentificador() + " - " + libro.getTitulo());			
		}
	}
	
	/**
	 *Crear un nuevo libro como un XML a partir de los datos proporcionados 
	 * por el usuario, devuelve el identificador del libro. 
	 */
	public static int crearLibro(Libro libro) 
	{
				
		lista.add(libro);
		writeXmlFile(lista);
		
		return libro.getIdentificador();
	}
	
	/*
	 * Devuelve un objeto Libro a partir de un identificador.
	 */
	public static Libro recuperarLibro(int identificador) 
	{
		Libro resultado = null;
		for (Libro libro : lista) {
			if (libro.getIdentificador() == identificador) {
				resultado = libro;
				break;
			}	
		}
		return resultado;
	}
	
	/*
	 * Muestra los atributos del libro por pantalla
	 */
	public static void mostrarLibro(Libro libro) {
		System.out.println("Identificador: " + libro.getIdentificador());
		System.out.println("Título: " + libro.getTitulo());
		System.out.println("Autor: " + libro.getAutor());
		System.out.println("Año de publicación: " + libro.getAnoPublicacion());
		System.out.println("Editorial: " + libro.getEditorial());
		System.out.println("Número de páginas: " + libro.getNumPaginas());
	}
	
	/*
	* Borra un libro a partir de un identificador.
	*/
	public static void borrarLibro(int identificador) {
		Scanner teclado = new Scanner(System.in);
		Libro lib = recuperarLibro(identificador);
		System.out.println(lib.toString());
		System.out.println("¿Está seguro que desea borrar este libro de la biblioteca (s/n)?");
		String respuesta = teclado.next();
		if (!respuesta.equals("s")) return;
		int indice = 0;
		for (Libro l : lista) {
			if (l.getIdentificador() == identificador) {
				lista.remove(indice);
				break;
			}
			indice++;
		}
		writeXmlFile(lista);
	}
	
	/*
	 * Actualiza (modifica) la información de un objeto a partir de un identificador.
	 */
	public static void actualizarLibro(int identificador) {
		
		Scanner teclado = new Scanner(System.in);
		String nuevoValor;
		Libro lib = recuperarLibro(identificador);
		
		System.out.println(lib.toString());
		System.out.println("Actualizar datos (introducir espacio y enter para preservar valor): ");
		System.out.print("Actualizar título: "); nuevoValor = teclado.nextLine();
		if (!nuevoValor.equals(" ")) lib.setTitulo(nuevoValor);
		System.out.print("Actualizar autor: "); nuevoValor = teclado.nextLine();
		if (!nuevoValor.equals(" ")) lib.setAutor(nuevoValor);
		System.out.print("Actualizar año: "); nuevoValor = teclado.nextLine();
		if (!nuevoValor.equals(" ")) lib.setAnoPublicacion(Integer.parseInt(nuevoValor));
		System.out.print("Actualizar editorial: "); nuevoValor = teclado.nextLine();
		if (!nuevoValor.equals(" ")) lib.setEditorial(nuevoValor);
		System.out.print("Actualizar páginas: "); nuevoValor = teclado.nextLine();
		if (!nuevoValor.equals(" ")) lib.setNumPaginas(Integer.parseInt(nuevoValor));
		int indice = 0;
		for (Libro l : lista) {
			if (l.getIdentificador() == identificador) {
				lista.set(indice,lib);
				break;
			}
			indice++;
		}
		writeXmlFile(lista);
		
	}
	
	/*
	 * Devuelve una lista con todos los libros de la biblioteca.
	 */
	public static ArrayList<Libro> recuperarTodos(){
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document document = dBuilder.parse(new File(ficheroXML));
			Element raiz = document.getDocumentElement();
			NodeList nodeList = document.getElementsByTagName("libro");			 
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);
				Element eElement = (Element) node;
				int id = Integer.parseInt(eElement.getAttribute("id"));
			    String titulo = eElement.getElementsByTagName("titulo").item(0).getTextContent();
			    String autor = eElement.getElementsByTagName("autor").item(0).getTextContent();
			    int anyo = Integer.parseInt(eElement.getElementsByTagName("anyo").item(0).getTextContent());
			    String editorial = eElement.getElementsByTagName("editorial").item(0).getTextContent();
			    int paginas = Integer.parseInt(eElement.getElementsByTagName("paginas").item(0).getTextContent());
			    Libro lib = new Libro(id,titulo,autor,anyo,editorial,paginas);
			    lista.add(lib);
			    ultimoId = id;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}
	
	/*
	 * Método para escribir el fichero XML mediante el ArrayList que pasamos como parametro.
	 */	
	public static void writeXmlFile(ArrayList<Libro> lista) 
	{
		try
		{
			DocumentBuilderFactory dFact = DocumentBuilderFactory.newInstance();
			DocumentBuilder build = dFact.newDocumentBuilder();
			Document doc = build.newDocument();
			
			Element raiz = doc.createElement("biblioteca");
			doc.appendChild(raiz);
			for (Libro lib : lista) {
				Element libro = doc.createElement("libro");
				String id = String.valueOf(lib.getIdentificador());
				libro.setAttribute("id",id);
		        raiz.appendChild(libro);
		        
		        Element titulo = doc.createElement("titulo");
	            titulo.appendChild(doc.createTextNode(String.valueOf(lib.getTitulo())));
	            libro.appendChild(titulo);
	            
	            Element autor = doc.createElement("autor");
	            autor.appendChild(doc.createTextNode(String.valueOf(lib.getAutor())));
	            libro.appendChild(autor);
	            
	            Element anyo = doc.createElement("anyo");
	            anyo.appendChild(doc.createTextNode(String.valueOf(lib.getAnoPublicacion())));
	            libro.appendChild(anyo);
	            
	            Element editorial = doc.createElement("editorial");
	            editorial.appendChild(doc.createTextNode(String.valueOf(lib.getEditorial())));
	            libro.appendChild(editorial);
	            
	            Element paginas = doc.createElement("paginas");
	            paginas.appendChild(doc.createTextNode(String.valueOf(lib.getNumPaginas())));
	            libro.appendChild(paginas);
			}
			
			TransformerFactory tranFactory = TransformerFactory.newInstance(); //Crear serializador
	        Transformer aTransformer = tranFactory.newTransformer();
	        aTransformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1"); //Darle formato al documento
	        aTransformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
	        aTransformer.setOutputProperty(OutputKeys.INDENT, "yes");
	        DOMSource source = new DOMSource(doc);
	        try {
	            FileWriter fw = new FileWriter(ficheroXML); //Definir el nombre del fichero y guardar
	            StreamResult result = new StreamResult(fw);
	            aTransformer.transform(source, result);
	            fw.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
			
			
		} catch(TransformerException ex) {
			System.out.println("Error escribiendo el documento");	
		} catch(ParserConfigurationException ex) {
			System.out.println("Error construyendo el documento");
		}
		
	}
	
}
