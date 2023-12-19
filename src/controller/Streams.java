package controller;

import model.Tarea;

import java.io.*;
import java.util.ArrayList;

public class Streams {

    public static void exportarTareas(ArrayList<Tarea> tareas) throws IOException {
        //Creamos los recursos FileOutputStream pasando como argumento la ruta que deseamos
        //y el ObjectOutputStream asociado para exportar el objeto.
        FileOutputStream fileWriter = new FileOutputStream("src/model/data/ArrayListTarea");
        ObjectOutputStream objectWriter = new ObjectOutputStream(fileWriter);

        //Pasamos por los streams el objeto deseado.
        objectWriter.writeObject(tareas);

        //Cerramos los recursos.

        objectWriter.close();
        fileWriter.close();
    }

    public static ArrayList<Tarea> importarTareas(String path) throws IOException, ClassNotFoundException{
        //Creamos el objeto donde almacenaremos la importación.
        ArrayList<Tarea> tareas;

        //Creamos el recurso FileInputStream pasando como argumento la ruta que deseamos
        //y creamos el ObjectInputStream asociado para importar el objeto.
        FileInputStream fileReader = new FileInputStream(path);
        ObjectInputStream objectReader = new ObjectInputStream(fileReader);

        //Guardamos en nuestro objeto el resultado que nos ofrece el ObjectInputStream.
        tareas = (ArrayList<Tarea>) objectReader.readObject();

        //Cerramos los recursos.
        objectReader.close();
        fileReader.close();

        //Devolvemos el objeto persona resultante.
        return tareas;
    }
}
