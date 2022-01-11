package com.florida.AE06_T2_2_MongoDB;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.json.JSONObject;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import static com.mongodb.client.model.Filters.*;

public class Principal {

	static int documents;
	static Scanner input;
	
	public static void main(String[] args) throws InterruptedException {

		MongoClient mongoClient = new MongoClient("localhost", 27017);
		MongoDatabase database = mongoClient.getDatabase("Biblioteca");
		MongoCollection<Document> collection = database.getCollection("Libros");
		
		Logger mongoLogger = Logger.getLogger( "org.mongodb.driver" );
		mongoLogger.setLevel(Level.SEVERE); 
		
		//CRUD OPERATIONS
		Thread.sleep(500);
		System.out.println("Conexión establecida");
			
		documents = (int) collection.countDocuments();
		System.out.println("Total de documentos: " + documents);
		
		//Leemos entrada de teclado del usuario para realizar acción correspondiente.
				input = new Scanner(System.in);
				int choice = 0;
				int id;
				
				while (choice != 6) {
					System.out.println("\n=================================================");
					System.out.println("                MENU PRINCIPAL");
					System.out.println("==================================================");
					System.out.println("\n1. Mostrar todos los títulos de la biblioteca");
					System.out.println("2. Mostrar información detallada de un libro");
					System.out.println("3. Crear nuevo libro");
					System.out.println("4. Actualizar libro");
					System.out.println("5. Borrar libro");
					System.out.println("6. Cerrar la biblioteca");
					System.out.println("");
					System.out.println("INDICA LA ACCIÓN A REALIZAR: ");
					choice = Integer.parseInt(input.next());
					System.out.println("\n");
					
					switch(choice) {
					case 1:
						mostrarLibros(collection);
						break;
					case 2:
						System.out.println("Indica el ID del libro:");
						id = Integer.parseInt(input.next());
						infoLibro(collection, id);
						break;
					case 3:
						anadirLibro(collection);
						break;
					case 4:
						System.out.println("Indica el ID del libro a actualizar:");
						id = Integer.parseInt(input.next());
						actualizarLibro(collection, id);
						break;
					case 5:
						System.out.println("Indica el ID del libro a borrar:");
						id = Integer.parseInt(input.next());
						borrarLibro(collection, 15);
						break;
					case 6:
						System.out.println("Finalizando el programa");
						input.close();
						break;
					default:
						break;
					}
				}
		
		mongoClient.close();
	}

	/**
	 * Método para mostrar el id y título de todos los libros.
	 * @param collection
	 */
	public static void mostrarLibros(MongoCollection<Document> collection) {
		
		MongoCursor<Document> cursor = collection.find().iterator();
		
		System.out.println("\n=========================");
		System.out.println("Libros de la biblioteca:");
		System.out.println("=========================");
		
		System.out.println("ID" + "     TITULO");
		while (cursor.hasNext()) {
			JSONObject obj = new JSONObject(cursor.next().toJson());
			System.out.println(obj.getString("Id") + "      " + obj.getString("Titol"));
		}
	}
	
	/**
	 * Método para imprimir la información de un libro seleccionado a partir de su ID.
	 * @param collection
	 * @param id
	 */
	public static void infoLibro(MongoCollection<Document> collection, int id) {
		
		Bson query = eq("Id", String.valueOf(id));
		
		MongoCursor<Document> cursor = collection.find(query).iterator();
		while (cursor.hasNext()) {
			JSONObject obj = new JSONObject(cursor.next().toJson());
			
			System.out.println("\n================================");
			System.out.println("Información del libro con ID " + id + ":");
			System.out.println("================================");
			System.out.println("Título: " + obj.getString("Titol"));
			System.out.println("Autor: " + obj.getString("Autor"));
			System.out.println("Año de nacimiento: " + obj.getString("Any_naixement"));
			System.out.println("Año de publicación: " + obj.getString("Any_publicacio"));
			System.out.println("Editorial: " + obj.getString("Editorial"));
			System.out.println("Número de paginas: " + obj.getString("Nombre_pagines"));
		}	
	}
	
	
	public static void anadirLibro(MongoCollection<Document> collection){
		
		String titulo, autor, anoNacimiento, anoPublicacion, editorial, numPaginas;
		
		System.out.println("\n================================");
		System.out.println("Indica los datos del libro a añadir:");
		System.out.println("================================");
		
		input = new Scanner(System.in);
		
		System.out.print("Indica el título: ");
		titulo = input.next();
		System.out.print("Indica el autor: ");
		autor = input.next();
		System.out.print("Indica el año de nacimiento del autor: ");
		anoNacimiento = input.next();
		System.out.print("Indica el año de publicación: ");
		anoPublicacion = input.next();
		System.out.print("Indica la editorial: ");
		editorial = input.next();
		System.out.print("Indica el número de páginas: ");
		numPaginas = input.next();
		
		Document doc = new Document();
		doc.append("Id", String.valueOf(documents + 1));
		doc.append("Titol", titulo);
		doc.append("Autor", autor);
		doc.append("Any_naixement", anoNacimiento);
		doc.append("Any_publicacio", anoPublicacion);
		doc.append("Editorial", editorial);
		doc.append("Nombre_pagines", numPaginas);
		collection.insertOne(doc);
		
		documents = (int) collection.countDocuments();
		System.out.println("\nDocumento insertado en la colección.");
		System.out.println("Total de documentos: " + collection.countDocuments());
	}
	
	/**
	 * Método para la actualización de un libro indicando su id por parametros.
	 * @param collection
	 * @param id
	 */
	public static void actualizarLibro(MongoCollection<Document> collection, int id) {
		
		String newTitulo, newAutor, newAnoNacimiento, newAnoPublicacion, newEditorial, newNumPaginas;

		Bson query = eq("Id", String.valueOf(id));
		
		MongoCursor<Document> cursor = collection.find(query).iterator();
		while (cursor.hasNext()) {
			JSONObject obj = new JSONObject(cursor.next().toJson());
			
			System.out.println("\n================================");
			System.out.println("Indica los valores de los campos que quieras actualizar. Si no quieres actualizar algún campo escribe un guión(-)");
			System.out.println("================================");
			
			input = new Scanner(System.in);
			
			System.out.print("Indica el título: ");
			newTitulo = input.nextLine();
			System.out.print("Indica el autor: ");
			newAutor = input.nextLine();
			System.out.print("Indica el año de nacimiento del autor: ");
			newAnoNacimiento = input.nextLine();
			System.out.print("Indica el año de publicación: ");
			newAnoPublicacion = input.nextLine();
			System.out.print("Indica la editorial: ");
			newEditorial = input.nextLine();
			System.out.print("Indica el número de páginas: ");
			newNumPaginas = input.nextLine();
			
			
			//En el caso de no haber guión se actualizará con la información que ha pasado el usuario.
			if(!newTitulo.contains("-"))
				collection.updateOne(eq("Titol", obj.getString("Titol")), new Document("$set", new Document("Titol", newTitulo)));
			if(!newAutor.contains("-"))
				collection.updateOne(eq("Autor", obj.getString("Autor")), new Document("$set", new Document("Autor", newAutor)));
			if(!newAnoNacimiento.contains("-"))
				collection.updateOne(eq("Any_naixement", obj.getString("Any_naixement")), new Document("$set", new Document("Any_naixement", newAnoNacimiento)));
			if(!newAnoPublicacion.contains("-"))
				collection.updateOne(eq("Any_publicacio", obj.getString("Any_publicacio")), new Document("$set", new Document("Any_publicacio", newAnoPublicacion)));
			if(!newEditorial.contains("-"))
				collection.updateOne(eq("Editorial", obj.getString("Editorial")), new Document("$set", new Document("Editorial", newEditorial)));
			if(!newNumPaginas.contains("-"))
				collection.updateOne(eq("Nombre_pagines", obj.getString("Nombre_pagines")), new Document("$set", new Document("Nombre_pagines", newNumPaginas)));
		}	
		
		System.out.println("\nDocumento actualizado correctamente.");
	}
	
	/**
	 * Método para la eliminación de Libros de la BBDD indicando su id.
	 * @param collection
	 * @param id
	 */
	public static void borrarLibro(MongoCollection<Document> collection, int id) {
		
		System.out.println("Eliminando el libro con id " + id);
		
		collection.deleteOne(eq("Id", String.valueOf(id)));
		
		System.out.println("Total de documentos: " + collection.countDocuments());
	}
}
  