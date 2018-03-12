package palisson.bdeb.qc.ca.bdebgarde;

import android.app.Application;
import java.util.ArrayList;


public class CampDeJour extends Application {

    private static ListeEnfants listeEnfants = new ListeEnfants();
    public static ListeEnfants getListeEnfants() {return listeEnfants;}
    public static void addEnfant(Enfant enfant){if(listeEnfants != null) listeEnfants.addEnfant(enfant);}
    public static ArrayList<Enfant> getEnfants(String mdpParent) {return listeEnfants.getListe(); }
    public static boolean estBonMotDePasse(String mdpParent) {return mdpParent.equals("2");}
}
