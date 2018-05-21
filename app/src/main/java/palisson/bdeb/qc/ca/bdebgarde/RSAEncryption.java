package palisson.bdeb.qc.ca.bdebgarde;



import java.io.*;
import java.security.*;
import java.security.spec.*;
import java.util.*;
import javax.crypto.Cipher;

public class RSAEncryption {

    PublicKey clePublique;
    public final static int TAILLE_BRUIT = 16; // Caractères aléatoires mis à la fin du message. 16 caractères => une chance sur 5192296858534827628530496329220096 qu'un message soit encrypté de la même façon deux fois.
    
    /**
     * Lit un fichier binaire
     * @param stream Le stream du fichier
     * @return Le contenu du fichier
     * @throws IOException 
     */
    public static byte[] lireFichier(InputStream stream) throws IOException
    {
        DataInputStream fichier = new DataInputStream(new BufferedInputStream(stream));
        byte[] bytes = new byte[stream.available()];
        fichier.readFully(bytes);
        return bytes;
    }

    /**
     * Constructeur
     * @param fichierClePublique L'inputstream du fichier
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException 
     */
    public RSAEncryption(InputStream fichierClePublique) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException
    {
        this(lireFichier(fichierClePublique));
    }

    /**
     * Constructeur
     * @param clePublique le contenu de la clé publique
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException 
     */
    public RSAEncryption(byte[] clePublique) throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        KeyFactory kf = KeyFactory.getInstance("RSA");

        X509EncodedKeySpec spec1 = new X509EncodedKeySpec(clePublique);
        this.clePublique = kf.generatePublic(spec1);

    }

    /**
     * Sert à encrypter un message
     * @param message Le message
     * @return Le message codé
     */
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