package kz.chat.crypto;

import org.junit.jupiter.api.Test;

import java.security.KeyPair;
import java.security.interfaces.RSAPublicKey;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CryptoServiceTest {

    private final CryptoService crypto = new CryptoService();

    @Test
    void generatesPublicAndPrivateKey() throws Exception {
        KeyPair pair = crypto.generateRsaKeyPair();
        assertNotNull(pair.getPublic());
        assertNotNull(pair.getPrivate());
    }

    @Test
    void keyAlgorithmIsRsa() throws Exception {
        KeyPair pair = crypto.generateRsaKeyPair();
        assertEquals("RSA", pair.getPublic().getAlgorithm());
    }

    @Test
    void keySizeIs2048Bits() throws Exception {
        RSAPublicKey key = (RSAPublicKey) crypto.generateRsaKeyPair().getPublic();
        assertEquals(2048, key.getModulus().bitLength());
    }

    @Test
    void eachKeyPairIsDifferent() throws Exception {
        byte[] first = crypto.generateRsaKeyPair().getPublic().getEncoded();
        byte[] second = crypto.generateRsaKeyPair().getPublic().getEncoded();
        assertNotEquals(Arrays.toString(first), Arrays.toString(second));
    }
}