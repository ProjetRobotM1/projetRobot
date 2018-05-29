package com.projetRobot;

import java.io.Serializable;

/**
 * Created by Hugo on 17/05/2018.
 */

public class Article implements Serializable {
    public String nom;
    public String description;

    public Article() {
    }

    public Article(String nom, String description) {
        this.nom = nom;
        this.description = description;
    }

    public String getNom() {
        return nom;
    }


    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Article article = (Article) o;

        if (nom != null ? !nom.equals(article.nom) : article.nom != null) return false;
        return description != null ? description.equals(article.description) : article.description == null;
    }

    @Override
    public String toString() {
        return nom ;
    }

    @Override
    public int hashCode() {
        int result = nom != null ? nom.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}
