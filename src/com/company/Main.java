package com.company;

import com.company.Control.Control;

public class Main {

    public static void main(String[] args) {
        //Prueba - Se puede borrar
        Control control = new Control();
        control.makeStateList("FSA3.jff");
        control.makeTransitionList("FSA3.jff");
        control.reduce();
    }
}
