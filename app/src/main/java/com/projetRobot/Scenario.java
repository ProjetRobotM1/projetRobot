package com.projetRobot;

/**
 * Created by Hugo on 08/05/2018.
 */

public class Scenario {
    public String name;

    public Scenario() {
    }

    public Scenario(String name) {
        this.name = name;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return   name ;
    }
}
