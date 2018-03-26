package palisson.bdeb.qc.ca.bdebgarde;


import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.*;



public class Enfant {
    public Enfant(String prenom, boolean saitNager, Sexe sexe, int id, String nom, boolean estPresent, Date dateNaissance) {
        this.prenom = prenom;
        this.saitNager = saitNager;
        this.sexe = sexe;
        this.id = id;
        this.nom = nom;
        this.estPresent = estPresent;
        this.dateNaissance = dateNaissance;
    }

    public Enfant(JSONObject enfant)
    {
        try {
            this.prenom = enfant.getString("prenom");
            this.nom = enfant.getString("nom");
            this.sexe = (enfant.getString("sexe").equals("M")) ? Sexe.M : Sexe.F;
            this.id = enfant.getInt("id");
            this.saitNager = enfant.getBoolean("saitNager");
            this.estPresent = enfant.getBoolean("estPresent");
            this.dateNaissance = new Date(); // TODO: Mettre qqchose ici
        }
        catch(Exception e) {}
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
