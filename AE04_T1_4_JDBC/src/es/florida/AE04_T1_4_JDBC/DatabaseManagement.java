package es.florida.AE04_T1_4_JDBC;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManagement {

	public static void main(String[] args) throws ClassNotFoundException 
	{
		DatabaseConnection();
	}
	
	public static void DatabaseConnection() throws ClassNotFoundException 
	{
		Class.forName("com.mysql.cj.jdbc.Driver");
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/biblioteca","root","");
			System.out.println("Conexión correcta");
			
			InsertFromCsv(con);
			
			autoresAntiguosQuery(con);
			siglo21Query(con);
			
			con.close();
		} catch (SQLException e){
		System.err.println("ERROR en la conexión");
		e.printStackTrace();
		}
	}	
	
	/*
	 * Método para volcar los datos que provienen del CSV para trasladarlos a la BBDD.
	 */
	public static void InsertFromCsv(Connection con) throws SQLException 
	{
		File f = new File("AE04_T1_4_JDBC_Datos.csv");
		if (f.isFile()) 
		{		
			FileReader fr;
			BufferedReader br;

			try {
				fr = new FileReader(f);
				br = new BufferedReader(fr);
				String row = br.readLine();
				int contador = 0;
				System.out.println("Insertados los siguientes libros en la BBDD:" + "\n");
				while(row != null) {
					
					if(contador > 0) 
					{		
						String[] linea = row.split(";");
						System.out.println("- " + linea[0]);
						
						PreparedStatement psInsertar = con.prepareStatement("INSERT INTO libros (titulo, autor, anoNacimiento, anoPublicacion, editorial, numPaginas) VALUES (?,?,?,?,?,?)");
						
						for (int i = 0; i <= linea.length-1; i++) {
							if(linea[i] == null || linea[i].equals(""))
								psInsertar.setString(i+1, "N.C.");
							else								
								psInsertar.setString(i+1, linea[i]);
							}
						
						int resultadoInsertar = psInsertar.executeUpdate();
					}				
					contador++;
					row = br.readLine();	
				}
				br.close();
				fr.close();
					
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}	
	}
	
	/*
	 * Método para realización de consulta de los libros cuyos autores hayan nacido antes de 1950.
	 */
	public static void autoresAntiguosQuery(Connection con) throws SQLException 
	{		
		Statement stmt = con.createStatement();
		
		String query = "SELECT titulo,autor,anoPublicacion FROM `libros` WHERE anoNacimiento < 1950";
		
		ResultSet rs = stmt.executeQuery(query);
		
		System.out.println("\n" + "CONSULTA => Libros cuyo autor haya nacido antes de 1950:" + "\n");
		
		System.out.format("%30s%30s%25s", "Título", "Autor", "Año Publicación" + "\n");
		System.out.format("%30s%30s%25s", "======", "=====", "===============" + "\n");
		
		while(rs.next()) {
			System.out.format("%30s%30s%25s", rs.getString(1), rs.getString(2), rs.getString(3) + "\n");
		}
	}
	
	/*
	 * Método para realizar consulta de aquellas editoriales que hayan publicado libros en el siglo XXI.
	 */
	public static void siglo21Query(Connection con) throws SQLException 
	{		
		Statement stmt = con.createStatement();
		
		String query = "SELECT editorial, titulo, anoPublicacion FROM `libros` WHERE anoPublicacion >= 2001 AND anoPublicacion <= 2100";
		
		ResultSet rs = stmt.executeQuery(query);
		
		System.out.println("\n"+ "CONSULTA => Editoriales que hayan publicado libros en el siglo XXI:" + "\n");
		
		System.out.format("%15s%25s%25s", "Editorial", "Titulo", "Año Publicación" + "\n");
		System.out.format("%15s%25s%25s", "=========", "======", "===============" + "\n");
		
		while(rs.next()) {
			System.out.format("%15s%25s%25s", rs.getString(1), rs.getString(2), rs.getString(3) + "\n");
		}
	}
}
