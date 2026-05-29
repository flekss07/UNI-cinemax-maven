import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
/**
 * Classe di cifratura delle password
 * Classe che si occupa della cifratura e la decifratura delle password,
 * sfruttando l'algoritmo AES(Advanced Encryption Standard)
 *
 * @author Scalone Lorenzo
 * */
public class AESencrypt {
    /**
     * <p>Chiave di cifratura utilizzata dall'algoritmo AES
     * lunghezza 128 bit (16 caratteri)</p>
     * <code>FIXED_KEY</code>
     * */
    private static final String FIXED_KEY = "cinemax2026key!!"; // 16 caratteri per AES-128
    /**
     * @param password Password inserita da utente (password in chiaro)
     * @return Password cifrata codificata in Base64
     * @throws Exception Eccezione lanciata se si verifica un errore durante la cifratura della password
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
     * @param passwordCodificata password cifrata in formato base64
     * @return Password password originale decifrata (password in chiaro)
     * @throws Exception Eccezione lanciata se si verifica un errore durante la decifratura della password
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