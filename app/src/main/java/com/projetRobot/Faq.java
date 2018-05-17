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
    
}
