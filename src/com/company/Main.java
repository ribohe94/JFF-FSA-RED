package com.company;

import com.company.Control.Control;
import com.company.Model.State;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

import static javax.swing.text.html.HTML.Tag.HEAD;

public class Main {

    public static void main(String[] args) {
        Control control = new Control();
        control.init(args);

		//Ejemplo de uso.
        //Consola: java -jar Proyecto\ Paradigmas.jar "/home/ribohe94/IdeaProjects/Proyecto Paradigmas/out/artifacts/Proyecto_Paradigmas_jar/test/" "JFF/"

    }
}
