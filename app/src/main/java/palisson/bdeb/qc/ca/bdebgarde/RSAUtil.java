/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package palisson.bdeb.qc.ca.bdebgarde;

import android.content.Context;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.io.*;

public class RSAUtil {
    
    public static void initialiserCles(String fichierClePublique, String fichierClePrivee, Context context) throws Exception
    {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(TAILLE_CLE);
        KeyPair kpair = kpg.genKeyPair();


        byte[] publicKeyBytes = kpair.getPrivate().getEncoded();
        FileOutputStream fos = context.openFileOutput(fichierClePublique, Context.MODE_PRIVATE);

        fos.write(kpair.getPublic().getEncoded());
        fos.close();
        
        fos = context.openFileOutput(fichierClePrivee, Context.MODE_PRIVATE);
        fos.write(kpair.getPrivate().getEncoded());
        fos.close();
    }
    
    public static byte[] lireFichier(InputStream stream) throws IOException
    {
        DataInputStream fichier = new DataInputStream(new BufferedInputStream(stream));
        byte[] bytes = new byte[stream.available()];
        fichier.readFully(bytes);
        return bytes;
    }

    public static byte[] lireFichier(String chemin, Context context) throws IOException
    {
        return lireFichier(context.openFileInput(chemin));
    }


    public final static int TAILLE_BRUIT = 16; // Caractères aléatoires mis à la fin du message. 16 caractères => une chance sur 5192296858534827628530496329220096 qu'un message soit encrypté de la même façon deux fois.
    public final static int TAILLE_CLE = 2048;
}
           
