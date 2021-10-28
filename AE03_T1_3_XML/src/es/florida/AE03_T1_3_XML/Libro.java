package es.florida.AE03_T1_3_XML;

public class Libro {
	int identificador;
	String titulo;
	String autor;
	int anoPublicacion;
	String editorial;
	int numPaginas;
	
	public Libro(int identificador, String titulo, String autor, int anoPublicacion, String editorial, int numPaginas) {
		this.identificador = identificador;
		this.titulo = titulo;
		this.autor = autor;
		this.anoPublicacion = anoPublicacion;
		this.editorial = editorial;
		this.numPaginas = numPaginas;
	}
}
