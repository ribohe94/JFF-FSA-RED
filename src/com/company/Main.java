package com.company;

import com.company.Control.Control;
import com.company.Model.State;

import java.util.*;

import static javax.swing.text.html.HTML.Tag.HEAD;

public class Main {

    public static void main(String[] args) {
        //Prueba - Se puede borrar
        Control control = new Control();
        control.makeStateList("FSA3.jff");
        control.makeTransitionList("FSA3.jff");
        control.makeAlphabet("FSA3.jff");
        control.reduce();
    }
}
