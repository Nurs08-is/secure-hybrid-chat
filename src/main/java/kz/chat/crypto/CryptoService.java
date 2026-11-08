package kz.chat.crypto;

import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;

/**
 * Криптография қызметі. Қазір: RSA кілт жұбын жасау.
 * Кейін осында AES-GCM және RSA-OAEP қосылады.
 */
public class CryptoService {

    private static final String RSA = "RSA";
    private static final int RSA_KEY_SIZE = 2048;

    /** Жаңа RSA кілт жұбын (ашық + жабық) жасайды. */
    public KeyPair generateRsaKeyPair() throws GeneralSecurityException {
        KeyPairGenerator generator = KeyPairGenerator.getInstance(RSA);
        generator.initialize(RSA_KEY_SIZE, new SecureRandom());
        return generator.generateKeyPair();
    }
}
