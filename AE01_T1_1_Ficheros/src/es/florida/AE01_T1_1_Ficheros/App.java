package es.florida.AE01_T1_1_Ficheros;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class App {

	
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);
		
		int choice = 0;
		
		System.out.println("Introduce el número de la acción que quieras realizar:");
		System.out.println("");
		System.out.println("1. Ver información.");
		System.out.println("2. Crear carpeta.");
		System.out.println("3. Crear fichero.");
		System.out.println("4. Eliminar fichero/carpeta.");
		System.out.println("5. Renombrar fichero/carpeta");
		System.out.println("");
		System.out.print("Número de acción: ");
		choice = Integer.parseInt(br.readLine());
		 
		switch(choice) {
		case 1:	
			System.out.print("Indica el nombre del fichero o directorio: ");
			String name = br.readLine();
			
			getInformacion(args, name);				
			break;
			
		case 2: 
			System.out.print("Como quieres llamar a la carpeta? ");
			String folderName = br.readLine();
			creaCarpeta(args, folderName);
			break;
			
		case 3:
			System.out.print("Como quieres llamar al fichero? ");
			String fileName = br.readLine();
			
			creaFichero(fileName);
			break;
			
		case 4:
			System.out.print("Indica el nombre del archivo o carpeta a eliminar: ");
			String fileToDelete = br.readLine();
			
			elimina(fileToDelete);
			break;
			
		case 5:
			System.out.print("Indica el nombre del fichero o directorio a renombrar: ");
			String oldFileName = br.readLine();
			System.out.print("Indica como quieres renombrarlo: ");
			String newFileName = br.readLine();
			
			renombra(oldFileName, newFileName);
			break;
		}
	}
	
	public static void getInformacion(String[] args, String name) {
		
		File fichero = new File(args[0], name);
		
		if(fichero.exists()) 
		{
			System.out.println("Nombre: " + fichero.getName());
			if(fichero.isFile())
				System.out.println("Tipo: Fichero");
			else
				System.out.println("Tipo: Carpeta");
			System.out.println("Ubicación: " + fichero.getAbsolutePath());
			
			DateFormat sdf
            = new SimpleDateFormat("dd MMMM yyyy | hh:mm a");
			long lastModified = fichero.lastModified();
			System.out.println("Última modificación: " + sdf.format(lastModified));
			if(fichero.canRead())
				System.out.println("Oculto: NO");
			else
				System.out.println("Oculto: SI");
			
			if(fichero.isFile()) {
				System.out.println("Tamaño (bytes): " + fichero.length());
			}
			else if(fichero.isDirectory()) {
				String[] list = fichero.list();
				System.out.println("Contenido:");
				Integer count = 0;
 				for(String elemento : list) { 					
 					System.out.println(elemento);
 					count++;
 				}
 				System.out.println("Total elementos contenidos: " + count);
				
				System.out.println("Espacio libre: " + fichero.getFreeSpace());
				System.out.println("Espacio disponible: " + fichero.getUsableSpace());
				System.out.println("Espacio total: " + fichero.getTotalSpace());
			}
				
			
		}
		else {
			System.out.print("El nombre indicado no corresponde a ningun fichero o directorio");
		}
		
	}
	
	public static void creaCarpeta(String[] args,String folderName) {

		
		File theDir = new File(folderName);
		if (!theDir.exists()){
		    theDir.mkdirs();
		}
		else
			System.out.print("nope");
	}
	
	public static void creaFichero(String fileName) {

		File fichero = new File(fileName);
		try {
			fichero.createNewFile();
		} catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public static void elimina(String fileName) {

		System.out.print("Eliminamos fichero/carpeta");
		
		try  
		{         
			File f = new File(fileName);          
			if(f.delete())                     
			{  
				System.out.println(f.getName() + " eliminado");
			}  
			else  
			{  
				System.out.println("failed");  
			}  
		} catch(Exception e)  
		{  
			e.printStackTrace();  
		}    
	}
	
	public static void renombra(String oldFileName, String newFileName) throws IOException {

		System.out.print("Renombramos fichero/carpeta");
		
		File file = new File(oldFileName);
		File file2 = new File(newFileName);
		
		if(file.exists()) {
			
			if(file2.exists())
				throw new java.io.IOException("File exists");
			
			boolean success = file.renameTo(file2);
			
			if (!success) {
				System.out.println("No se ha renombrado el elemento");
			}
		}		
	}

}

