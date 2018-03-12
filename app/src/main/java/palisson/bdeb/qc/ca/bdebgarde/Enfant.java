package palisson.bdeb.qc.ca.bdebgarde;

import android.os.Build;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.*;
import java.time.*;



public class Enfant {
    public Enfant(String prenom, boolean saitNager, Sexe sexe, int id, String nom, boolean estPresent, Date dateNaissance) {
        this.prenom = prenom;
        this.saitNager = saitNager;
        this.sexe = sexe;
        this.id = id;
        this.nom = nom;
        this.estPresent = estPresent;
        this.dateNaissance = dateNaissance;

        if (Build.VERSION.SDK_INT > 26) {
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime naissance = LocalDateTime.ofInstant(dateNaissance.toInstant(), ZoneId.systemDefault());
            age = (int)ChronoUnit.YEARS.between(naissance, now);
        }

    }

    public enum Sexe {M, F};

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Date getDateNaissance() {
        return dateNaissance;
    }

    public String stringDateNaissance(){
        SimpleDateFormat formater = new SimpleDateFormat("y-M-d");
        return formater.format(dateNaissance);
    }

    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }


    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }


    public boolean isEstPresent() {
        return estPresent;
    }

    public void setEstPresent(boolean estPresent) {
        this.estPresent = estPresent;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public boolean isSaitNager() {
        return saitNager;
    }

    public void setSaitNager(boolean saitNager) {
        this.saitNager = saitNager;
    }


    public Sexe getSexe() {
        return sexe;
    }

    public void setSexe(Sexe sexe) {
        this.sexe = sexe;
    }

    private String prenom;
    private boolean saitNager;
    private Sexe sexe;
    private int id;
    private String nom;
    private boolean estPresent;

    private Date dateNaissance;
    private int age;

    public Enfant(String prenom, Date dateNaissance){
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;

    }

}
