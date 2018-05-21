package palisson.bdeb.qc.ca.bdebgarde;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.*;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

public class CryptoAES
{
    private final static int TAILLEBRUIT = 16;
    private SecretKeySpec skeySpec;
    private SecretKey secretKey;
    
    /**
     * Constructeur. Génère une clé aléatoirement.
     */
    public CryptoAES()
    {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(128);
            secretKey = keyGen.generateKey();
            skeySpec = new SecretKeySpec(secretKey.getEncoded(), "AES");
        }
        catch(Exception e) {}

    }
    
    /**
     * Constructeur. Utilise la clé donnée.
     * @param cle La clé, encodée en base 64.
     */
    public CryptoAES(String cle)
    {
        skeySpec = new SecretKeySpec(Base64Coder.decode(cle), 0, 16, "AES");
    }


    /**
     * Pour avoir la clé, encodée en base 64
     * @return La clé
     */
    public String getCle()
    {
        return Base64Coder.encode(secretKey.getEncoded());
    }
    
    /**
     * Sert à encrypter un message
     * @param message Le message
     * @return Le message codé
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws InvalidAlgorithmParameterException
     * @throws InvalidKeyException 
     */
    public String encryption(String message) throws NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, InvalidKeyException
    {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");

        SecureRandom randomSecureRandom = SecureRandom.getInstance("SHA1PRNG");
        byte[] iv = new byte[cipher.getBlockSize()];
        randomSecureRandom.nextBytes(iv);
        IvParameterSpec ivParams = new IvParameterSpec(iv);
        
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, ivParams);

        for(int i = 0; i < TAILLEBRUIT; i++)
            message += (char)(new Random()).nextInt(128);

        return Base64Coder.encode(iv) + Base64Coder.encode(cipher.doFinal(message.getBytes()));
    }
    
    /**
     * Sert à décoder un message
     * @param messageCode Le message codé
     * @return Le message
     * @throws InvalidAlgorithmParameterException
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException 
     */
    public String decryption(String messageCode) throws InvalidAlgorithmParameterException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, BadPaddingException, IllegalBlockSizeException
    {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
        int tailleVecteur = 24;
        String ivChaine = messageCode.substring(0, tailleVecteur);
        String messageChaine = messageCode.substring(tailleVecteur, messageCode.length());
        
        IvParameterSpec ivParams = new IvParameterSpec(Base64Coder.decode(ivChaine));
        
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, ivParams);
        String messageDecode = new String(cipher.doFinal(Base64Coder.decode(messageChaine)));
        return messageDecode.substring(0, messageDecode.length() - TAILLEBRUIT);
    }
}
