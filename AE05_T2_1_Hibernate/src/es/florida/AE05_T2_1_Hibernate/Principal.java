package es.florida.AE05_T2_1_Hibernate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class Principal {
	
	public static void main(String[] args) {
		
		//Carga la configuraci�n y crea un session factory
		Configuration conf = new Configuration().configure("hibernate.cfg.xml");
		conf.addClass(Libro.class);
		
		//Creamos ServiceRegistry y SessionFactory
		ServiceRegistry registry = new StandardServiceRegistryBuilder().applySettings(conf.getProperties()).build();
		SessionFactory sessionFactory = conf.buildSessionFactory(registry);	
		
		//Abre una nueva session de la sessionFactory
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		//Leemos entrada de teclado del usuario para realizar acci�n correspondiente.
		Scanner input = new Scanner(System.in);
		int choice = 0;
		int id;
		
		while (choice != 6) {
			System.out.println("=================================================");
			System.out.println("                MENU PRINCIPAL");
			System.out.println("==================================================");
			System.out.println("\n1. Mostrar todos los t�tulos de la biblioteca");
			System.out.println("2. Mostrar informaci�n detallada de un libro");
			System.out.println("3. Crear nuevo libro");
			System.out.println("4. Actualizar libro");
			System.out.println("5. Borrar libro");
			System.out.println("6. Cerrar la biblioteca");
			System.out.println("");
			System.out.println("INDICA LA ACCI�N A REALIZAR: ");
			choice = Integer.parseInt(input.next());
			System.out.println("\n");
			
			switch(choice) {
			case 1:
				mostrarLibros(session);
				break;
			case 2:
				System.out.println("Indica el ID del libro:");
				id = Integer.parseInt(input.next());
				infoLibro(session, id);
				break;
			case 3:
				anadirLibro(session, nuevoLibro());	
				break;
			case 4:
				System.out.println("Indica el ID del libro a actualizar:");
				id = Integer.parseInt(input.next());
				actualizarLibro(session, id);
				break;
			case 5:
				System.out.println("Indica el ID del libro a borrar:");
				id = Integer.parseInt(input.next());
				borrarLibro(session, id);
				break;
			case 6:
				System.out.println("Finalizando el programa");
				input.close();
				break;
			default:
				break;
			}
		}
		
		session.getTransaction().commit(); 
		session.close();
	}
	
	/**
	 * Mostramos todos lod libros de la Biblioteca.
	 * @param session
	 */
	public static void mostrarLibros(Session session) {
		
		List lista= new ArrayList();
		lista = session.createQuery("FROM Libro").list();
		
		System.out.println("Mostrando todos los libros de la biblioteca: \n");
		
		for (Object obj : lista)
		{
				Libro libro = (Libro)obj;
				System.out.println("ID: " + libro.getIdentificador() + " | T�tulo: " + libro.getTitulo());
		}
		System.out.println("\n");
	}
	
	/**
	 * M�todo para imprimir la informaci�n de un libro seleccionado a partir de su ID.
	 * @param session
	 * @param id
	 */
	public static void infoLibro(Session session, int id) {
		
		Libro libro = (Libro) session.get(Libro.class, id);
			
		if(libro != null) {
			
			System.out.println("\n");
			System.out.println("Informaci�n del libro con ID " + id + ":\n");
			System.out.println("T�tulo: " + libro.getTitulo());
			System.out.println("Autor: " + libro.getAutor());
			System.out.println("A�o de nacimiento: " + libro.getAnoNacimiento());
			System.out.println("A�o de publicaci�n: " + libro.getAnoPublicacion());
			System.out.println("Editorial: " + libro.getEditorial());
			System.out.println("N�mero de paginas: " + libro.getNumPaginas());
		}
		else
			System.err.println("El ID seleccionado no corresponde a ning�n libro de la BBDD.");
		
		System.out.println("\n");
	}
	
	/**
	 * M�todo que devuelve el libro creado a partir de los parametros pasados.
	 * @param titulo
	 * @param autor
	 * @param anoNacimiento
	 * @param anoPublicacion
	 * @param editorial
	 * @param numPaginas
	 * @return
	 */
	public static Libro nuevoLibro() {
		
		String titulo,autor, anoNacimiento, anoPublicacion, editorial, numPaginas;
		
		Scanner inputTeclado = new Scanner(System.in);
		
		System.out.println("\n");
		System.out.print("Indica el t�tulo: ");
		titulo = inputTeclado.next();
		System.out.print("Indica el autor: ");
		autor = inputTeclado.next();
		System.out.print("Indica el a�o de nacimiento del autor: ");
		anoNacimiento = inputTeclado.next();
		System.out.print("Indica el a�o de publicaci�n: ");
		anoPublicacion = inputTeclado.next();
		System.out.print("Indica la editorial: ");
		editorial = inputTeclado.next();
		System.out.print("Indica el n�mero de p�ginas: ");
		numPaginas = inputTeclado.next();
		
		return new Libro(titulo, autor, anoNacimiento, anoPublicacion, editorial, numPaginas);
	}
	
	/**	
	 * M�todo que a�ade el Libro a nuestra base de datos.
	 * @param session
	 * @param libro
	 */
	public static void anadirLibro(Session session, Libro libro) {
		
		Serializable id = session.save(libro);
		
		System.out.println("Libro a�adido correctamente");
		
		System.out.println("\n");
	}
	
	/**
	 * M�todo para actualizar los datos del libro en funci�n del ID indicado por parametros.
	 * @param session
	 * @param id
	 */
	public static void actualizarLibro(Session session, int id) {
		
		Libro libro = (Libro) session.load(Libro.class, id);
		
		String newTitulo, newAutor, newAnoNacimiento, newAnoPublicacion, newEditorial, newNumPaginas;

		Scanner inputTeclado = new Scanner(System.in);	
		
		
		System.out.println("\n");
		System.out.println("Indica los valores de los campos que quieras actualizar. Si no quieres actualizar alg�n campo escribe un gui�n(-)");
		System.out.print("Indica el t�tulo: ");
		newTitulo = inputTeclado.nextLine();
		System.out.print("Indica el autor: ");
		newAutor = inputTeclado.nextLine();
		System.out.print("Indica el a�o de nacimiento del autor: ");
		newAnoNacimiento = inputTeclado.nextLine();
		System.out.print("Indica el a�o de publicaci�n: ");
		newAnoPublicacion = inputTeclado.nextLine();
		System.out.print("Indica la editorial: ");
		newEditorial = inputTeclado.nextLine();
		System.out.print("Indica el n�mero de p�ginas: ");
		newNumPaginas = inputTeclado.nextLine();
		
		try {
			if(!newTitulo.contains("-"))
				libro.setTitulo(newTitulo);
			if(!newAutor.contains("-"))
				libro.setAutor(newAutor);
			if(!newAnoNacimiento.contains("-"))
				libro.setAnoNacimiento(newAnoNacimiento);
			if(!newAnoPublicacion.contains("-"))
				libro.setAnoPublicacion(newAnoPublicacion);
			if(!newEditorial.contains("-"))
				libro.setEditorial(newEditorial);
			if(!newNumPaginas.contains("-"))
				libro.setNumPaginas(newNumPaginas);	
			
			System.out.println("\nLibro actualizado correctamente.");
			
		} catch(ObjectNotFoundException e) {
			System.err.println("\nEl ID indicado no corresponde a ning�n libro de la base de datos.");
		}
		
		System.out.println("\n");
	}
	
	/**
	 * Borrar un libro de la BBDD en funci�n del ID indicado por parametros.
	 * @param session
	 * @param id
	 */
	public static void borrarLibro(Session session, int id) {
		
		try {
			Libro libro = (Libro) session.get(Libro.class, id);
			session.delete(libro);
			
			System.out.println("El libro indicado se ha eliminado correctamente.");
			
		}catch(IllegalArgumentException e) {
			System.err.println("\nEl ID indicado no corresponde a ning�n libro de la base de datos.");
		}
		System.out.println("\n");
	}
}
