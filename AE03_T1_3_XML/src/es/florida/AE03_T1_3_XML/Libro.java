package es.florida.AE03_T1_3_XML;

public class Libro {
	private int identificador;
	private String titulo;
	private String autor;
	private int anoPublicacion;
	private String editorial;
	private int numPaginas;	
	
	public Libro(int identificador, String titulo, String autor, int anoPublicacion, String editorial, int numPaginas)
	{
		this.identificador = identificador;
		this.titulo = titulo;
		this.autor = autor;
		this.anoPublicacion = anoPublicacion;
		this.editorial = editorial;
		this.numPaginas = numPaginas;
	}
	
	public int getIdentificador() {
		return identificador;
	}
	
	public String getTitulo() {
		return titulo;
	}
	
	public String getAutor() {
		return autor;
	}
	
	public int getAnoPublicacion() {
		return anoPublicacion;
	}
	
	public String getEditorial() {
		return editorial;
	}
	
	public int getNumPaginas() {
		return numPaginas;
	}
	
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	public void setAutor(String autor) {
		this.autor = autor;
	}
	
	public void setAnoPublicacion(int anoPublicacion) {
		this.anoPublicacion = anoPublicacion;
	}
	
	public void setEditorial(String editorial) {
		this.editorial = editorial;
	}
	
	public void setNumPaginas(int numPaginas) {
		this.numPaginas = numPaginas;
	}
}
