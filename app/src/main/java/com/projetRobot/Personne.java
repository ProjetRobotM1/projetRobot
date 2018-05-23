package com.projetRobot;

import android.media.Image;

import java.io.Serializable;

/**
 * Created by Hugo on 17/05/2018.
 * Nom: …..
 * Prénom: ……
 * Age: …..
 * Profession: ….
 * Adresse : ……
 * Email: …..
 * Site web: ……
 * Photo :
 */

public class Personne implements Serializable {

    public String nom;
    public String prenom;
    public Integer age;
    public String profession;
    public String adresse;
    public String email;
    public String site;

    public Personne() {
    }

    public Personne(String nom, String prenom, Integer age, String profession, String adresse, String email, String site) {
        this.nom = nom;
        this.prenom = prenom;
        this.age = age;
        this.profession = profession;
        this.adresse = adresse;
        this.email = email;
        this.site = site;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    @Override
    public String toString() {
        return  ""+prenom+" "+ nom ;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Personne personne = (Personne) o;

        if (age != personne.age) return false;
        if (nom != null ? !nom.equals(personne.nom) : personne.nom != null) return false;
        if (prenom != null ? !prenom.equals(personne.prenom) : personne.prenom != null)
            return false;
        if (profession != null ? !profession.equals(personne.profession) : personne.profession != null)
            return false;
        if (adresse != null ? !adresse.equals(personne.adresse) : personne.adresse != null)
            return false;
        if (email != null ? !email.equals(personne.email) : personne.email != null) return false;
        return site != null ? site.equals(personne.site) : personne.site == null;
    }

    @Override
    public int hashCode() {
        int result = nom != null ? nom.hashCode() : 0;
        result = 31 * result + (prenom != null ? prenom.hashCode() : 0);
        result = 31 * result + age;
        result = 31 * result + (profession != null ? profession.hashCode() : 0);
        result = 31 * result + (adresse != null ? adresse.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (site != null ? site.hashCode() : 0);
        return result;
    }
}
