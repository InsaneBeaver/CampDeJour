package palisson.bdeb.qc.ca.bdebgarde;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;



public class Client implements Runnable {

    private final String adresseServeur;
    private final int port;
    private final LinkedBlockingQueue<AEnvoyer> commandesAEnvoyer = new LinkedBlockingQueue<>();
    private final RSAEncryption encryption;
    private final RSADecryption decryption;
    private final String fichierClePubliqueClient;
    private final ServiceClient contexte;

    Socket socket;
    public Client(String adresseServeur, int port, RSAEncryption encryption, RSADecryption decryption, String fichierClePubliqueClient, ServiceClient contexte)
    {
        this.adresseServeur = adresseServeur;
        this.port = port;
        this.encryption = encryption;
        this.decryption = decryption;
        this.fichierClePubliqueClient = fichierClePubliqueClient;
        this.contexte = contexte;
    }


    interface SurReception
    {
        void operation(String reponse);
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

    @Override
    public void run()
    {
        try {
            socket = new Socket(adresseServeur, port);
            DataOutputStream versServeur = new DataOutputStream(socket.getOutputStream());
            BufferedReader duServeur = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            byte[] clePublique = RSAUtil.lireFichier(fichierClePubliqueClient, contexte);

            String cleEncodee = Base64Coder.encode(clePublique);
            versServeur.writeBytes(cleEncodee + '\n');

            // Authentification du serveur
            String reponse = duServeur.readLine();
            if(!encryption.authentifierSignature(reponse, cleEncodee))
                return;


            while(true)
            {
                if(!commandesAEnvoyer.isEmpty())
                {
                    AEnvoyer aEnvoyer = commandesAEnvoyer.poll();
                    String message = encryption.encrypter(aEnvoyer.message);
                    versServeur.writeBytes(message + "\n");
                    reponse = decryption.decrypter(duServeur.readLine());
                    aEnvoyer.surReception.operation(reponse);
                    commandesAEnvoyer.remove(0);
                }
            }

        }
        catch(Exception e) {}


    }

}
