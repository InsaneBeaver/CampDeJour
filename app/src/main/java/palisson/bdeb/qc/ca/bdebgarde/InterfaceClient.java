package palisson.bdeb.qc.ca.bdebgarde;


import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

import java.lang.reflect.Type;
import java.util.Arrays;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class InterfaceClient {
    private static Client client;

    public static boolean estConnecte()
    {
        return client.estConnecte;
    }

    class Session
    {
        /**
         * Contructeur
         * @param motDePasse Mot de passe de la personne
         */
        public Session(String motDePasse)
        {
            this.motDePasse = motDePasse;
        }
        public ArrayList<Enfant> listeEnfants = new ArrayList<>();
        public String motDePasse;
    }
    private Session session;

    public ArrayList<Enfant> getListeEnfants()
    {
        System.out.println("INTERFACE " + this);
        System.out.println("SESSION " + session);
        return (ArrayList<Enfant>)session.listeEnfants.clone();
    }
    public String getMotDePasse()
    {
        return session.motDePasse;
    }
    
    /**
     * Supprime la session actuelle
     */
    public void supprimerSession()
    {
        session = null;
    }


    private final static String COMMANDELISTE = "liste %s";
    private final static String COMMANDESETPRESENCE = "setpresence %s %d %d";

    /**
     * Initialise le client, qui est la connexion avec le serveur
     * @param contexte
     * @throws android.content.pm.PackageManager.NameNotFoundException 
     */
    public static void initialiserClient(Context contexte) throws PackageManager.NameNotFoundException
    {
        client = new Client(contexte, new Client.SurChangementDeConnexion() {
            @Override
            public void surDeconnexion() {
                CampDeJour.afficherToast("Le serveur est inaccessible");

            }
            @Override
            public void surConnexion() {
                CampDeJour.afficherToast("Connexion avec le serveur réussie");

            }
        });

        Thread thread = new Thread(client);
        thread.start();
    }

    private boolean estEnIdentification = false;

    /**
     * Accomplit l'identification
     * @param motDePasse Le mot de passe
     * @param surIdentification L'action à accomplir lorsque l'identification est réussie
     * @param surErreurIdentification L'action à accomplie lorsque l'identification échoue
     */
    public void identification(final String motDePasse, final Runnable surIdentification, final Runnable surErreurIdentification)
    {
        if(estEnIdentification) return;
        estEnIdentification = true;

        client.envoyerMessage(String.format(COMMANDELISTE, motDePasse), new Client.SurReception() {
            @Override
            public void operation(String reponse[]) {
                System.out.println(reponse.length);
                estEnIdentification = false;
                if(reponse.length == 0) {
                    if(surErreurIdentification != null)
                        CampDeJour.execAsync(surErreurIdentification);

                    // TODO: MESSAGE PAR DÉFAUT
                }

                else
                {
                    try {
                        InterfaceClient.this.session = new Session(motDePasse);

                        for (int i = 0; i < reponse.length && reponse[i] != null; i++) {
                            System.out.println(reponse[i]);
                            Enfant enfant = new Enfant(reponse[i]);
                            System.out.println(enfant.getNom());
                            session.listeEnfants.add(enfant);
                        }

                        Collections.sort(session.listeEnfants);

                        estEnIdentification = false;
                        CampDeJour.execAsync(surIdentification);
                    }
                    catch(Exception e) {
                        Log.i("Erreur", e.getMessage());}

                }

            }

            @Override
            public void surErreurConnexion() {
                CampDeJour.afficherToast("Erreur: Pas de connexion avec le serveur. Tente de se reconnecter.");
                estEnIdentification = false;
            }
        });
    }



    interface ChangerPresence
    {
        void changerPresence(boolean presence);
    };

    /**
     * Sert à modifier la présence de l'enfant 
     * @param enfant L'enfant
     * @param presence Le nouvel état
     * @param changerPresence Fonction pour modifier l'aspect graphique
     */
    public void setPresence(final Enfant enfant, final boolean presence, final ChangerPresence changerPresence)
    {
        client.envoyerMessage(String.format(COMMANDESETPRESENCE, session.motDePasse, enfant.getId(), presence ? 1 : 0), new Client.SurReception() {
            @Override
            public void operation(String[] reponse) {
                enfant.setEstPresent(presence);
            }

            @Override
            public void surErreurConnexion() {
                CampDeJour.afficherToast("Erreur: Pas de connexion avec le serveur. Tente de se reconnecter.");
                changerPresence.changerPresence(enfant.isEstPresent());
            }
        });
    }
}
