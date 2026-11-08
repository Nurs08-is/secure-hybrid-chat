package kz.chat.crypto;

import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AesGcmTest {

    private final CryptoService crypto = new CryptoService();

    @Test
    void decryptReturnsOriginalMessage() throws Exception {
        SecretKey key = crypto.generateAesKey();
        byte[] message = "Сәлем, әлем!".getBytes(StandardCharsets.UTF_8);

        byte[] encrypted = crypto.encryptAesGcm(message, key);
        byte[] decrypted = crypto.decryptAesGcm(encrypted, key);

        assertArrayEquals(message, decrypted);
    }

    @Test
    void sameMessageGivesDifferentCiphertextBecauseOfNewIv() throws Exception {
        SecretKey key = crypto.generateAesKey();
        byte[] message = "hello".getBytes(StandardCharsets.UTF_8);

        byte[] first = crypto.encryptAesGcm(message, key);
        byte[] second = crypto.encryptAesGcm(message, key);

        assertFalse(Arrays.equals(first, second));
    }

    @Test
    void tamperedMessageIsRejected() throws Exception {
        SecretKey key = crypto.generateAesKey();
        byte[] encrypted = crypto.encryptAesGcm("secret".getBytes(StandardCharsets.UTF_8), key);

        encrypted[encrypted.length - 1] ^= 1; // соңғы байтты өзгертеміз

        assertThrows(GeneralSecurityException.class, () -> crypto.decryptAesGcm(encrypted, key));
    }

    @Test
    void wrongKeyIsRejected() throws Exception {
        byte[] encrypted = crypto.encryptAesGcm("secret".getBytes(StandardCharsets.UTF_8), crypto.generateAesKey());
        SecretKey otherKey = crypto.generateAesKey();

        assertThrows(GeneralSecurityException.class, () -> crypto.decryptAesGcm(encrypted, otherKey));
    }
}