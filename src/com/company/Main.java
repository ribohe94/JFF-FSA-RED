package com.company;

import com.company.Control.Control;

public class Main {

    public static void main(String[] args) {
        Control control = new Control();
        control.init(args);
		
        /**
         * Ejemplo de como utilizar el programa. Recibe dos argumentos
         * 1- Directorio de entrada. Va a verificar todos los archivos dentro del directorio
         * 2- Directorio de salida. Todos los archivos de salida van a llegar a este directorio.
         * Puede tambien agregar un prefijo si escribe algo despues del ultimo slasg, por ejemplo: 'JFF/prefijo -'
         */

        //Consola: java -jar Proyecto\ Paradigmas.jar "/home/ribohe94/IdeaProjects/Proyecto Paradigmas/out/artifacts/Proyecto_Paradigmas_jar/test/" "JFF/"

    }
}
