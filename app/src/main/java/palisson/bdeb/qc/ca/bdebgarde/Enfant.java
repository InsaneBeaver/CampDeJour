package palisson.bdeb.qc.ca.bdebgarde;


import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.*;




public class Enfant implements Comparable<Enfant >{
    
    
    private String prenom;
    private boolean saitNager;
    private Sexe sexe;
    private int id;
    private String nom;
    private boolean estPresent;

    private Date dateNaissance;
    private int age;
    
    
    /**
     * Constructeur
     * @param prenom Prénom de l'enfant
     * @param saitNager Si l'enfant sait nager
     * @param sexe Le sexe de l'enfant
     * @param id L'identfiant de l'enfant
     * @param nom Le nom de l'enfant
     * @param estPresent Si l'enfant est présent
     * @param dateNaissance La date de naissance de l'enfant
     */
    public Enfant(String prenom, boolean saitNager, Sexe sexe, int id, String nom, boolean estPresent, Date dateNaissance) {
        this.prenom = prenom;
        this.saitNager = saitNager;
        this.sexe = sexe;
        this.id = id;
        this.nom = nom;
        this.estPresent = estPresent;
        this.dateNaissance = dateNaissance;
    }

    /**
     * Constructeur
     * @param enfant L'objet JSON contenant l'enfant avec tous les attributs propres au premier constructeur, avec "M" pour masculin ou "F" pour féminin et une date de naissance en format "EEE MMM d HH:mm:ss zzz yyyy"
     * 
     */
    public Enfant(JSONObject enfant)
    {
        try {
            this.id = enfant.getInt("id");
            this.prenom = enfant.getString("prenom");
            this.nom = enfant.getString("nom");
            this.sexe = (enfant.getString("sexe").equals("M")) ? Sexe.M : Sexe.F;
            this.saitNager = enfant.getBoolean("saitNager");
            this.estPresent = enfant.getBoolean("estPresent");

            SimpleDateFormat parser=new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy");

            try {
            this.dateNaissance = enfant.has("dateNaissance") ? parser.parse(enfant.getString("dateNaissance")) : new Date();}
            catch(Exception e){this.dateNaissance = new Date(); }

        }
        catch(Exception e) {
            Log.i("Erreur", id + " " + e.getMessage());};
    }



    /**
     * Constructeur
     * @param enfant L'enfant en format JSON valide
     * @throws JSONException 
     */
    public Enfant(String enfant) throws JSONException
    {
        this(new JSONObject(enfant));
    }

    /**
     * Utilisé lors du triage en ordre alphabétique des enfants par leur prénom dans la liste
     * @param o L'autre enfant
     * @return Le résultat de la comparaison
     */
    @Override
    public int compareTo(Enfant o)
    {
        int comparaison = nom.compareTo(o.getPrenom());
        if(comparaison == 0)
            comparaison = prenom.compareTo(o.getNom());

        return comparaison;
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
      SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
      return formatter.format(dateNaissance);
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

    public void setSexeM() {
        this.sexe = Sexe.M;
    }

    public void setSexeF() {
        this.sexe = Sexe.F;
    }




}
