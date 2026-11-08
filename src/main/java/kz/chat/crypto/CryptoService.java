package kz.chat.crypto;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.util.Arrays;

/**
 * Криптография қызметі: RSA кілт жұбы және AES-GCM шифрлау.
 */
public class CryptoService {

    private static final String RSA = "RSA";
    private static final int RSA_KEY_SIZE = 2048;

    private static final String AES = "AES";
    private static final int AES_KEY_SIZE = 256;
    private static final String AES_GCM = "AES/GCM/NoPadding";
    private static final int GCM_IV_LENGTH = 12;   // байт
    private static final int GCM_TAG_BITS = 128;

    private final SecureRandom random = new SecureRandom();

    /** Жаңа RSA кілт жұбын (ашық + жабық) жасайды. */
    public KeyPair generateRsaKeyPair() throws GeneralSecurityException {
        KeyPairGenerator generator = KeyPairGenerator.getInstance(RSA);
        generator.initialize(RSA_KEY_SIZE, random);
        return generator.generateKeyPair();
    }

    /** Жаңа AES сессия кілтін жасайды. */
    public SecretKey generateAesKey() throws GeneralSecurityException {
        KeyGenerator generator = KeyGenerator.getInstance(AES);
        generator.init(AES_KEY_SIZE, random);
        return generator.generateKey();
    }

    /** AES-GCM-мен шифрлайды. Нәтиже: IV (12 байт) + шифрланған дерек + tag. */
    public byte[] encryptAesGcm(byte[] plaintext, SecretKey key) throws GeneralSecurityException {
        byte[] iv = new byte[GCM_IV_LENGTH];
        random.nextBytes(iv); // әр хабарға жаңа IV

        Cipher cipher = Cipher.getInstance(AES_GCM);
        cipher.init(Cipher.ENCRYPT_MODE, key, new GCMParameterSpec(GCM_TAG_BITS, iv));
        byte[] ciphertext = cipher.doFinal(plaintext);

        byte[] result = new byte[iv.length + ciphertext.length];
        System.arraycopy(iv, 0, result, 0, iv.length);
        System.arraycopy(ciphertext, 0, result, iv.length, ciphertext.length);
        return result;
    }

    /** AES-GCM-мен ашады. Дерек өзгертілсе немесе кілт қате болса, exception шығады. */
    public byte[] decryptAesGcm(byte[] data, SecretKey key) throws GeneralSecurityException {
        if (data.length < GCM_IV_LENGTH + GCM_TAG_BITS / 8) {
            throw new GeneralSecurityException("Encrypted data is too short");
        }
        byte[] iv = Arrays.copyOfRange(data, 0, GCM_IV_LENGTH);
        byte[] ciphertext = Arrays.copyOfRange(data, GCM_IV_LENGTH, data.length);

        Cipher cipher = Cipher.getInstance(AES_GCM);
        cipher.init(Cipher.DECRYPT_MODE, key, new GCMParameterSpec(GCM_TAG_BITS, iv));
        return cipher.doFinal(ciphertext);
    }
}
