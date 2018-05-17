package com.projetRobot;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Hugo on 08/05/2018.
 */

public class Scenario implements Serializable{
    public String name;
    public ArrayList<Faq> faq;

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

    public String presTexte;


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
