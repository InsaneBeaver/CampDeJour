package palisson.bdeb.qc.ca.bdebgarde;

import android.app.Application;


public class CampDeJour extends Application {

    private static ListeEnfants listeEnfants = new ListeEnfants();
    public static ListeEnfants getListeEnfants() {return listeEnfants;}
    public static void addEnfant(Enfant enfant){if(listeEnfants != null) listeEnfants.ajouterEnfant(enfant);}
}
