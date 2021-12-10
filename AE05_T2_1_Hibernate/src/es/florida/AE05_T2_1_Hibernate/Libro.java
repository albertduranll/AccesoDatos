package es.florida.AE05_T2_1_Hibernate;

public class Libro {
	private int identificador;
	private String titulo;
	private String autor;
	private String anoNacimiento;
	private String anoPublicacion;
	private String editorial;
	private String numPaginas;	
	
	public Libro() {		
	}
	
	public Libro(int identificador, String titulo, String autor, String anoNacimiento, String anoPublicacion, String editorial, String numPaginas)
	{
		this.identificador = identificador;
		this.titulo = titulo;
		this.autor = autor;
		this.anoNacimiento = anoNacimiento;
		this.anoPublicacion = anoPublicacion;
		this.editorial = editorial;
		this.numPaginas = numPaginas;
	}
	
	public Libro(String titulo, String autor, String anoNacimiento, String anoPublicacion, String editorial, String numPaginas)
	{
		this.titulo = titulo;
		this.autor = autor;
		this.anoNacimiento = anoNacimiento;
		this.anoPublicacion = anoPublicacion;
		this.editorial = editorial;
		this.numPaginas = numPaginas;
	}

	public int getIdentificador() {
		return identificador;
	}

	public void setIdentificador(int identificador) {
		this.identificador = identificador;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}
	
	public String getAnoNacimiento() {
		return anoNacimiento;
	}

	public void setAnoNacimiento(String anoNacimiento) {
		this.anoNacimiento = anoNacimiento;
	}

	public String getAnoPublicacion() {
		return anoPublicacion;
	}

	public void setAnoPublicacion(String anoPublicacion) {
		this.anoPublicacion = anoPublicacion;
	}

	public String getEditorial() {
		return editorial;
	}

	public void setEditorial(String editorial) {
		this.editorial = editorial;
	}

	public String getNumPaginas() {
		return numPaginas;
	}

	public void setNumPaginas(String numPaginas) {
		this.numPaginas = numPaginas;
	}
	
	
}
