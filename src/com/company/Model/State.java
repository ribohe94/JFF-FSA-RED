package com.company.Model;

/**
 * Created by Edwin on 17/09/2016.
 */
public class State {
    private int id;
    private String name;
    private float x;
    private float y;
    private boolean final_state;
    private boolean initial;

    public State(int id, String name, float x, float y, boolean final_state) {
        this.id = id;
        this.name = name;
        this.x = x;
        this.y = y;
        this.final_state = final_state;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public boolean isFinal_state() {
        return final_state;
    }

    public void setFinal_state(boolean final_state) {
        this.final_state = final_state;
    }
}
