package com.projetRobot;

import java.io.Serializable;

/**
 * Created by Hugo on 17/05/2018.
 */

public class Article implements Serializable {
    public String nom;
    public String description;

    public Article(String nom, String description) {
        this.nom = nom;
        this.description = description;
    }
}
