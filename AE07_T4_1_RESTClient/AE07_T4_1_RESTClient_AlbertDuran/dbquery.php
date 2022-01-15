<?php
    if(isset($_POST["titulo"])){

        // Vínculo de variables
        $titulo = $_POST["titulo"];
        $director = $_POST["director"];
        $reparto = $_POST["reparto"];
        $sinopsis = $_POST["sinopsis"]; 
        $poster = $_POST["poster"]; 

        // Datos de conexión
        $servidor = "localhost";
        $usuario = "root";
        $password = "";
        $dbName = "filmoteca";

        //Gestión de conexión
        $conexion = mysqli_connect($servidor, $usuario, $password, $dbName);
        if(!$conexion){
            echo "Error en la conexión a MySQL: " . mysqli_connect_error();
            exit();
        }

        //Consulta
        $sql = "INSERT INTO peliculas (titulo,director,reparto,sinopsis,poster) VALUES ('".addslashes($titulo)."',
                '".addslashes($director)."', '".addslashes($reparto)."', '".addslashes($sinopsis)."', '".addslashes($poster)."')";
        
        if(mysqli_query($conexion, $sql)){
            echo "Registro insertado correctamente.";
        } else{
            echo "Error: " . $sql . "<br>" . mysqli_error($conexion);
        }
    }
?>