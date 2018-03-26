package palisson.bdeb.qc.ca.bdebgarde;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import java.io.File;



public class ServiceClient extends Service {

    private static final int port = 2006;
    private static final String adresseServeur = "192.168.56.1";
    private static RSAEncryption encryption;
    private static RSADecryption decryption;
    private final static String FICHIERCLEPUBLIQUECLIENT = "clientclepublique";
    private final static String FICHIERCLEPRIVEECLIENT = "clientcleprivee";
    private final static String ASSETCLEPUBLIQUESERV = "clepubliqueserv";
    public Client client;

    public ServiceClient() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent,int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        try {
            if (!(new File(FICHIERCLEPUBLIQUECLIENT)).exists()) {
                RSAUtil.initialiserCles(FICHIERCLEPUBLIQUECLIENT, FICHIERCLEPRIVEECLIENT, this);
            }
            getAssets().open(ASSETCLEPUBLIQUESERV);
            encryption = new RSAEncryption(getAssets().open(ASSETCLEPUBLIQUESERV));
            decryption = new RSADecryption(RSAUtil.lireFichier(FICHIERCLEPRIVEECLIENT, this));
        }
        catch(Exception e) {}
        client = new Client(adresseServeur, port, encryption, decryption, FICHIERCLEPUBLIQUECLIENT,this);
        Thread leThread = new Thread(client);
        leThread.start();
        CampDeJour.client = client;

        return START_STICKY;
    }
}
