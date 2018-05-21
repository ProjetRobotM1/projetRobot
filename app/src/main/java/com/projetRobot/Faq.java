package com.projetRobot;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Hugo on 17/05/2018.
 */

public class Faq implements Serializable {
    public Faq() {
    }

    public Faq(ArrayList<String> questions, ArrayList<String> reponses) {
        this.questions = questions;
        this.reponses = reponses;
    }

    public ArrayList<String> questions;
    public ArrayList<String> reponses;

    public ArrayList<String> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<String> questions) {
        this.questions = questions;
    }

    public ArrayList<String> getReponses() {
        return reponses;
    }

    public void setReponses(ArrayList<String> reponses) {
        this.reponses = reponses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Faq faq = (Faq) o;

        if (questions != null ? !questions.equals(faq.questions) : faq.questions != null)
            return false;
        return reponses != null ? reponses.equals(faq.reponses) : faq.reponses == null;
    }

    @Override
    public int hashCode() {
        int result = questions != null ? questions.hashCode() : 0;
        result = 31 * result + (reponses != null ? reponses.hashCode() : 0);
        return result;
    }
}
