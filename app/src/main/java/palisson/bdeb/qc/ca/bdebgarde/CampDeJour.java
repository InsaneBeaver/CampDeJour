package palisson.bdeb.qc.ca.bdebgarde;

import android.app.Application;


public class CampDeJour extends Application {

    private static ListeEnfants listeEnfants = null;
    public static ListeEnfants getListeEnfants() {return listeEnfants;}
    public static void addEnfant(Enfant enfant){listeEnfants.ajouterEnfant(enfant);}
}
