package com.company;

import com.company.Control.Control;

public class Main {

    public static void main(String[] args) {
        //Prueba - Se puede borrar
        Control control = new Control();
        control.makeStateList("FSA2.jff");
        control.makeTransitionList("FSA2.jff");
        control.writeFSA("output.xml");
    }
}
