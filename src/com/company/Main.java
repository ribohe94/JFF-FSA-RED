package com.company;

import com.company.Control.Control;

import static javax.swing.text.html.HTML.Tag.HEAD;

public class Main {

    public static void main(String[] args) {
        //Prueba - Se puede borrar
        Control control = new Control();
        control.makeStateList("FSA3.jff");
        control.makeTransitionList("FSA3.jff");
        control.reduce();

        //Pruebas
        //Control control = new Control();
        //control.makeStateList("FSA2.jff");
        //control.makeTransitionList("FSA2.jff");
        //control.writeFSA("output.xml");
        //control.ValidateXMLFiles("FSA2.jff");
        //control.ValidateXMLFiles("output.xml");
    }
}
