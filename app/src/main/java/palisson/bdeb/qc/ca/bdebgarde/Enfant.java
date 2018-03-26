package palisson.bdeb.qc.ca.bdebgarde;

import android.os.Build;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.*;
import java.time.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



public class Enfant {
    public Enfant(String JSON) {
        try {
            JSON = "{\"nom\":\"jacques\",\"prenom\":\"boulanger\",\"saitNager\":true,\"sexe\":\"M\",\"id\":2,\"estPresent\":true,\"date\":\"1995-08-10\"}";
            JSONObject jsonObj = new JSONObject(JSON);
            SimpleDateFormat formater = new SimpleDateFormat("y-M-d");
            this.prenom = jsonObj.getString("prenom");
            this.saitNager = jsonObj.getBoolean("saitNager");
            if (jsonObj.getString("sexe").charAt(0) == 'F') {
                setSexeF();
            } else if (jsonObj.getString("sexe").charAt(0) == 'M') {
                setSexeM();
            }
            this.id = jsonObj.getInt("id");
            this.nom = jsonObj.getString("nom");
            this.estPresent = jsonObj.getBoolean("estPresent");
          try   { this.dateNaissance = formater.parse(jsonObj.getString("date"));
          } catch (Exception e) {

          }
        } catch (final JSONException e) {

        }

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

  /*  public void setDateNaissance(String dateString) {
        SimpleDateFormat formater = new SimpleDateFormat("y-M-d");
        formater.parse()
        this.dateNaissance = dateNaissance;
    }*/


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

    public void setSexeM() {
        this.sexe = Sexe.M;
    }

    public void setSexeF() {
        this.sexe = Sexe.F;
    }

    private String prenom;
    private boolean saitNager;
    private Sexe sexe;
    private int id;
    private String nom;
    private boolean estPresent;

    private Date dateNaissance;
    private int age;

    /*public Enfant(String prenom, Date dateNaissance){
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
    }*/

}
