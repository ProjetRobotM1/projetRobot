package com.projetRobot;

import java.util.ArrayList;

/**
 * Created by Hugo on 21/05/2018.
 */

public class ConteneurListArticle {
    private String name;
    private ArrayList<Article> listArticle;

    public ConteneurListArticle() {
    }

    public ConteneurListArticle(String name) {
        this.name = name;
    }

    public ConteneurListArticle(String name, ArrayList<Article> listArticle) {
        this.name = name;
        this.listArticle = listArticle;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Article> getListArticle() {
        return listArticle;
    }

    public void setListArticle(ArrayList<Article> listArticle) {
        this.listArticle = listArticle;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ConteneurListArticle that = (ConteneurListArticle) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return listArticle != null ? listArticle.equals(that.listArticle) : that.listArticle == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (listArticle != null ? listArticle.hashCode() : 0);
        return result;
    }
}
