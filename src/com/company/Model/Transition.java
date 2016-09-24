package com.company.Model;

/**
 * Created by Edwin on 17/09/2016.
 */
public class Transition {
	//Clase base para la estructura de las transiciones del autómata.
    private int from;
    private int to;
    private String value;

    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }

    public String getValue() {
        return value;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Transition(int from, int to, String value) {
        this.setFrom(from);
        this.setTo(to);
        this.setValue(value);
    }
}
