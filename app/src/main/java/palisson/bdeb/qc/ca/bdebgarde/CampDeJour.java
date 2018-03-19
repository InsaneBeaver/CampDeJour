package palisson.bdeb.qc.ca.bdebgarde;

import android.app.Application;
import android.widget.Toast;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.security.*;
import java.util.HashMap;


public class CampDeJour extends Application {
    public static void init()
    {
        try {digest = MessageDigest.getInstance("SHA-256");} catch(Exception e) {}

    }

    private static MessageDigest digest;

    public static HashMap<String, ArrayList<Enfant>> parents = new HashMap<>();
    public static boolean isListeVide(){return listeEnfants.getListe().isEmpty();}
    private static ListeEnfants listeEnfants = new ListeEnfants();
    public static ListeEnfants getListeEnfants() {return listeEnfants;}
    public static void addEnfant(Enfant enfant){if(listeEnfants != null) listeEnfants.addEnfant(enfant);}

    public static String getHash(String chaine)
    {
        try {
        digest = MessageDigest.getInstance("SHA-256");} catch(Exception e) {}
        return new String(digest.digest(chaine.getBytes(StandardCharsets.UTF_8)));

    }

    public static boolean hashEstConnu(String hash)
    {
        return parents.containsKey(hash);
    }

    // Temporaire
    public static void ajouterParent(String motDePasse, ArrayList<Enfant> enfants)
    {
        parents.put(getHash(motDePasse), (ArrayList<Enfant>)enfants.clone());
    }

    public static ArrayList<Enfant> getEnfants(String hash)
    {
        if(!parents.containsKey(hash))
            return null;

        return (ArrayList<Enfant>)parents.get(hash);
    }
}
