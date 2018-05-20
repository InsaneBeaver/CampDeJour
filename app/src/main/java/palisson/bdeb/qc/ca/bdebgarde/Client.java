package palisson.bdeb.qc.ca.bdebgarde;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.concurrent.LinkedBlockingQueue;
import android.content.Context;



public class Client implements Runnable {

    private final String adresseServeur;
    private final int port;
    private final LinkedBlockingQueue<AEnvoyer> commandesAEnvoyer = new LinkedBlockingQueue<>();
    private RSAEncryption encryption;

    private CryptoAES cryptoAES;
    private CryptoAES cryptoAESTemp;
    private String cleSymetriqueEncryptee;


    private final static String ASSETCLEPUBLIQUESERV = "clepubliqueserv";


    private final int timeout = 1000;

    interface SurChangementDeConnexion
    {
        void surDeconnexion();
        void surConnexion();
    };


    private final SurChangementDeConnexion surChangementDeConnexion;

    Socket socket;


    /**
     * Constructeur
     * @param contexte contexte qui sera utilisé pour lire le fichier de clé publique
     * @param surChangementDeConnexion Action à effectuer si l'état de la connexion avec le serveur change
     * @throws PackageManager.NameNotFoundException
     */
    public Client(Context contexte, SurChangementDeConnexion surChangementDeConnexion) throws PackageManager.NameNotFoundException
    {
        try
        {
            cryptoAESTemp = new CryptoAES();
            encryption = new RSAEncryption(contexte.getAssets().open(ASSETCLEPUBLIQUESERV));

            cleSymetriqueEncryptee = encryption.encrypter(cryptoAESTemp.getCle());


        }
        catch(Exception e)
        {
            Log.i("Erreur", e.getMessage());
        }

        ApplicationInfo ai = contexte.getPackageManager().getApplicationInfo(contexte.getPackageName(), PackageManager.GET_META_DATA);
        Bundle bundle = ai.metaData;

        this.adresseServeur = bundle.getString("adresseserveur");
        this.port = bundle.getInt("port");

        this.surChangementDeConnexion = surChangementDeConnexion;


    }


    interface SurReception
    {
        void operation(String[] reponse);
        void surErreurConnexion();
    };


    class AEnvoyer
    {
        public AEnvoyer(String message, SurReception surReception)
        {
            this.message = message;
            this.surReception = surReception;
        }

        public final String message;
        public final SurReception surReception;

    };


    public void envoyerMessage(String message, SurReception surReception)
    {
        commandesAEnvoyer.add(new AEnvoyer(message, surReception));
    }

    DataOutputStream versServeur;
    BufferedReader duServeur;
    boolean estConnecte = false;

    @Override
    public void run()
    {
        while(true)
        {
            boolean aDeconnecte = false;
            while(true)
            {
                while(!commandesAEnvoyer.isEmpty())
                    commandesAEnvoyer.poll().surReception.surErreurConnexion();

                estConnecte = effectuerConnexion();
                if(estConnecte)
                    break;

                if(!aDeconnecte) surChangementDeConnexion.surDeconnexion();
                aDeconnecte = true;
                try {
                    Thread.sleep(5000);
                }
                catch(Exception e) {}

            }
            CampDeJour.afficherToast("...");
            surChangementDeConnexion.surConnexion();

            try
            {
                ecouter();
            }
            catch(SocketException e) {Log.i("foo", e.getMessage()); estConnecte = false; }
        }

    }

    /**
     * Essae de se connecter au serveur
     * @return Si la connexion a réussi
     */
    private boolean effectuerConnexion()
    {
        /**
         * Protocole:
         * 1) Le client encrypte une clé AES avec RSA et l'envoie au serveur
         * 2) Le serveur crée une clé AES, l'encrypte avec la clé AES du client et lui envoie
         * 3) Les deux côtés communiquent avec la clé AES du serveur.
         *
         * Échange entièrement sécuritaire. On ne pourrait pas directement se servir de la première clé AES du client parce qu'un attaquant n'aurait qu'à répéter
         * tous les messages envoyés par un client donné.
         */
        try {
            Log.i("La cle", "" + cleSymetriqueEncryptee);
            socket = new Socket();
            socket.connect(new InetSocketAddress(adresseServeur, port), timeout);
            versServeur = new DataOutputStream(socket.getOutputStream());
            duServeur = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            versServeur.writeBytes(cleSymetriqueEncryptee + '\n');
            String reponse = duServeur.readLine();
            cryptoAES = new CryptoAES(cryptoAESTemp.decryption(reponse));
            return true;
        }
        catch(Exception e) { Log.i("Erreur", e.getMessage()); return false;}
    }

    /**
     * Envoie le message directement au serveur
     * @param message Le message à envoyer
     * @return La réponse
     * @throws Exception
     */
    private String[] envoyerAuServeur(String message) throws Exception
    {
        String messageEncrypte = cryptoAES.encryption(message);
        versServeur.writeBytes(messageEncrypte + "\n");
        String[] reponses = new String[128];
        int iReponses = 0;
        String reponse;
        while(!(reponse = duServeur.readLine()).isEmpty())
        {
            reponses[iReponses] = cryptoAES.decryption(reponse);
            Log.i("Reponse", reponses[iReponses]);
            iReponses++;
        }
        return reponses;
    }

    /**
     * Attend que l'on donne un message à envoyer au client, envoie le message et fait l'action appropriée avec la réponse
     * @throws SocketException
     */
    private void ecouter() throws SocketException
    {
        String[] reponses;
        AEnvoyer aEnvoyer = null;
        int compteur = 0;
        try {
            while (true) {
                if (!commandesAEnvoyer.isEmpty()) {
                    aEnvoyer = commandesAEnvoyer.poll();
                    reponses = envoyerAuServeur(aEnvoyer.message);
                    aEnvoyer.surReception.operation(reponses);
                    commandesAEnvoyer.remove(0);
                    compteur = 1;
                    aEnvoyer = null;
                    try {
                        Thread.sleep(10);
                    } catch (Exception e) {
                    }
                }
                else
                {
                    compteur++;
                    if(compteur % 500 == 0) {
                        envoyerAuServeur("ping");
                        compteur = 0;
                    }
                    else
                        try {
                            Thread.sleep(5);
                        }
                        catch (Exception e) {
                        }

                }

            }
        }
        catch(IOException e)
        {
            if(aEnvoyer != null)
                aEnvoyer.surReception.surErreurConnexion();
        }
        catch(Exception e) {CampDeJour.afficherToast(e.getMessage());}

    }

}
