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

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public boolean isFinal_state() {
        return final_state;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setFinal_state(boolean final_state) {
        this.final_state = final_state;
    }

    public State(int id, String name, float x, float y, boolean final_state) {
        this.setId(id);
        this.setName(name);
        this.setX(x);
        this.setY(y);
        this.setFinal_state(final_state);
    }
}
