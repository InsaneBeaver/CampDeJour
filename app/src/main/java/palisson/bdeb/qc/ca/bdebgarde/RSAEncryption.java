
package palisson.bdeb.qc.ca.bdebgarde;


import android.content.Context;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.*;
import java.util.*;
import javax.crypto.Cipher;
public class RSAEncryption {

    PublicKey clePublique;
    public final static int TAILLE_BRUIT = 16; // Caractères aléatoires mis à la fin du message. 16 caractères => une chance sur 5192296858534827628530496329220096 qu'un message soit encrypté de la même façon deux fois.
    public static byte[] lireFichier(InputStream stream) throws IOException
    {
        DataInputStream fichier = new DataInputStream(new BufferedInputStream(stream));
        byte[] bytes = new byte[stream.available()];
        fichier.readFully(bytes);
        return bytes;
    }


    public RSAEncryption(InputStream fichierClePublique) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException
    {
        this(lireFichier(fichierClePublique));
    }

    public RSAEncryption(byte[] clePublique) throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        KeyFactory kf = KeyFactory.getInstance("RSA");

        X509EncodedKeySpec spec1 = new X509EncodedKeySpec(clePublique);
        this.clePublique = kf.generatePublic(spec1);

    }

    public String encrypter(String message)
    {
        // Mettre du bruit
        Random r = new Random();
        for(int i = 0; i <TAILLE_BRUIT; i++)
            message += (char)r.nextInt(128);

        byte[] encrypted = null;
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, clePublique);
            encrypted = cipher.doFinal(message.getBytes());
        }
        catch(Exception e) {}


        return Base64Coder.encode(encrypted);

    }

}