package com.projetRobot;

import java.util.ArrayList;

/**
 * Created by Hugo on 21/05/2018.
 */

public class ConteneurListPersonne {
    private String name;
    private ArrayList<Personne> listPersonne;

    public ConteneurListPersonne() {
    }

    public ConteneurListPersonne(String name) {
        this.name = name;
    }

    public ConteneurListPersonne(String name, ArrayList<Personne> listPersonne) {
        this.name = name;
        this.listPersonne = listPersonne;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Personne> getListPersonne() {
        return listPersonne;
    }

    public void setListPersonne(ArrayList<Personne> listPersonne) {
        this.listPersonne = listPersonne;
    }
    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ConteneurListPersonne that = (ConteneurListPersonne) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return listPersonne != null ? listPersonne.equals(that.listPersonne) : that.listPersonne == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (listPersonne != null ? listPersonne.hashCode() : 0);
        return result;
    }
}

