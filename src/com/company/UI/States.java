package com.company.UI;

import com.company.Control.Control;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * Created by ribohe94 on 23/09/16.
 * <p>
 * Enum de estados para manejar posibles situaciones con respecto a los argumentos de entrada.
 */
public enum States implements State {
    Init {
        @Override
        public State next(Input value) {
            if (value.getInput() != null && value.getInput().length == 2) {
                return fileIn;
            } else {
                return missingPar;
            }
        }
    },
    fileIn {
        @Override
        public State next(Input value) {
            String[] input = value.getInput();
            Control control = new Control();
            int i = 0;
            try (Stream<Path> paths = Files.walk(Paths.get(input[0]))) {
                paths.forEach(filePath -> {
                    if (Files.isRegularFile(filePath)) {
                        System.out.println(filePath);
                        if (control.ValidateXMLFiles(filePath.toString())) {
                            control.makeStateList(filePath.toString());
                            control.makeTransitionList(filePath.toString());
                            control.makeAlphabet(filePath.toString());
                            control.reduce(input[1] + "output - " + filePath.getFileName());
                        }
                    }
                });
            } catch (IOException e) {
                return missingFolder;
            }
            return null;
        }
    },
    missingPar {
        @Override
        public State next(Input value) {
            System.err.println("Faltan parametros, ingrese un directorio, especificando la direccion desde 'home/'. " +
                    "Considere usar el comando 'readlink -f file.txt' para obtener la direccion");
            return null;
        }
    },
    missingFolder {
        @Override
        public State next(Input value) {
            System.err.println("Hubo un error con los argumentos, " +
                    "Es posible que la direccion de carpeta sea incorrecta" +
                    "Ingrese un directorio, especificando la direccion desde 'home/'. ");
            return null;
        }
    }
}
