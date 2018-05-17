package com.projetRobot;

import android.media.Image;

import java.io.Serializable;

/**
 * Created by Hugo on 17/05/2018.
 * Nom: …..
 Prénom: ……
 Age: …..
 Profession: ….
 Adresse : ……
 Email: …..
 Site web: ……
 Photo :
 */

public class Personne implements Serializable {

    public String nom;
    public String prenom;
    public int age;
    public String profession;
    public String adresse;
    public String email;
    public String site;
    public Image photo;

    public Personne(String nom, String prenom, int age, String profession, String adresse, String email, String site, Image photo) {
        this.nom = nom;
        this.prenom = prenom;
        this.age = age;
        this.profession = profession;
        this.adresse = adresse;
        this.email = email;
        this.site = site;
        this.photo = photo;
    }
}
