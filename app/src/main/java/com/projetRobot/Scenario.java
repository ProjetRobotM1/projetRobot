package com.projetRobot;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Hugo on 08/05/2018.
 */

public class Scenario implements Serializable {
    public String name;
    public ArrayList<Faq> faq;
    public ArrayList<ConteneurListPersonne> list_listPersonne;
    public ArrayList<ConteneurListArticle> list_listArticle;
    public String presTexte;

    public ArrayList<ConteneurListPersonne> getList_listPersonne() {
        return list_listPersonne;
    }

    public void setList_listPersonne(ArrayList<ConteneurListPersonne> list_listPersonne) {
        this.list_listPersonne = list_listPersonne;
    }

    public ArrayList<ConteneurListArticle> getList_listArticle() {
        return list_listArticle;
    }

    public void setList_listArticle(ArrayList<ConteneurListArticle> list_listArticle) {
        this.list_listArticle = list_listArticle;
    }

    public ArrayList<Faq> getFaq() {
        return faq;
    }

    public void setFaq(ArrayList<Faq> faq) {
        this.faq = faq;
    }

    public String getPresTexte() {
        return presTexte;
    }

    public void setPresTexte(String presTexte) {
        this.presTexte = presTexte;
    }

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
        return name;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Scenario scenario = (Scenario) o;

        if (name != null ? !name.equals(scenario.name) : scenario.name != null) return false;

        if (faq != null ? !faq.equals(scenario.faq) : scenario.faq != null) return false;

        if (list_listPersonne != null ? !list_listPersonne.equals(scenario.list_listPersonne) : scenario.list_listPersonne != null)
            return false;

        if (list_listArticle != null ? !list_listArticle.equals(scenario.list_listArticle) : scenario.list_listArticle != null)
            return false;

        if( presTexte != null ? !presTexte.equals(scenario.presTexte) : scenario.presTexte != null)return false;

        return true;

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (faq != null ? faq.hashCode() : 0);
        result = 31 * result + (list_listPersonne != null ? list_listPersonne.hashCode() : 0);
        result = 31 * result + (list_listArticle != null ? list_listArticle.hashCode() : 0);
        result = 31 * result + (presTexte != null ? presTexte.hashCode() : 0);
        return result;
    }

}

