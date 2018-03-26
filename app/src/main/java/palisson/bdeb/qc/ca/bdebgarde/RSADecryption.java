
package palisson.bdeb.qc.ca.bdebgarde;


import java.io.IOException;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import javax.crypto.Cipher;


public class RSADecryption {
     PrivateKey clePrivee;
     
    public RSADecryption(InputStream fichierClePrivee) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException
    {
        this(RSAUtil.lireFichier(fichierClePrivee));
    }

    public final static String getHash(byte[] chaine)
    {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            String hash = Base64Coder.encode(digest.digest(chaine));
            return hash;
        }
        catch(Exception e) {}
        return "";
    }

    public RSADecryption(byte[] clePrivee) throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        KeyFactory kf = KeyFactory.getInstance("RSA");

        PKCS8EncodedKeySpec spec2 = new  PKCS8EncodedKeySpec(clePrivee);
        this.clePrivee = kf.generatePrivate(spec2);
    }
    
        public String decrypter(String message) throws Exception
    {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, clePrivee);
        String messageAvecBruit = new String(cipher.doFinal(Base64Coder.decode(message)));

        // Enlever le bruit
        return messageAvecBruit.substring(0, messageAvecBruit.length() - RSAUtil.TAILLE_BRUIT);
    }
        
    public String creerSignature(String clePubliqueAutre) throws Exception
    {
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(clePrivee);

        signature.update(clePubliqueAutre.getBytes());
        return Base64Coder.encode(signature.sign());
    }
}
