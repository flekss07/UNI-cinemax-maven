import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
/**
 * Classe che si occupa della cifratura e la decifratura delle password,
 * sfruttando l'algoritmo AES(Advanced Encryption Standard)
 * <h1>Classe di cifratura delle password
 * </h1>
 * <p>
 * La classe utilizza una chiave di cifratura di 128 bit per la cifratura e la decifratura
 * <p/>
 * */
public class AESencrypt {
    /**
     * Chiave di cifratura utilizzata dall'algoritmo AES
     * Lunghezza 128 bit (16 caratteri)
     * */
    private static final String FIXED_KEY = "cinemax2026key!"; // 16 caratteri per AES-128
    /**
     * Cifra una password in input usando AES
     *
     * @param password Password inserita da utente (password in chiaro)
     * @return Password cifrata codificata in Base64
     * @throws Exception Se si verifica un errore durante la cifratura
     * */
    // Prende in input una password e restituisce la password codificata
    public static String encrypt(String password) throws Exception {
        SecretKeySpec chiaveSegreta = new SecretKeySpec(
                FIXED_KEY.getBytes(), 0, 16, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, chiaveSegreta);
        byte[] encryptedpass = cipher.doFinal(password.getBytes());
        return Base64.getEncoder().encodeToString(encryptedpass);
    }

    /**
     * Decifra una password cifrata con AES
     *
     * @param passwordCodificata password cifrata in formato base64
     * @return Password password originale decifrata
     * @throws Exception Se si verifica un errore durante la decifratura
     * */
    // Prende in input una password codificata e restituisce la password originale
    public static String decrypt(String passwordCodificata) throws Exception {
        SecretKeySpec chiaveSegreta = new SecretKeySpec(
                FIXED_KEY.getBytes(), 0, 16, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, chiaveSegreta);
        byte[] decodedPassword = Base64.getDecoder().decode(passwordCodificata);
        byte[] decryptedPassword = cipher.doFinal(decodedPassword);
        return new String(decryptedPassword);
    }
}