package kz.chat.crypto;

import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.security.KeyPair;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SignatureServiceTest {

    private final CryptoService crypto = new CryptoService();
    private final SignatureService signer = new SignatureService();

    @Test
    void validSignatureIsAccepted() throws Exception {
        KeyPair pair = crypto.generateRsaKeyPair();
        byte[] message = "Сәлем".getBytes(StandardCharsets.UTF_8);

        byte[] signature = signer.sign(message, pair.getPrivate());

        assertTrue(signer.verify(message, signature, pair.getPublic()));
    }

    @Test
    void changedMessageIsRejected() throws Exception {
        KeyPair pair = crypto.generateRsaKeyPair();
        byte[] signature = signer.sign("Сәлем".getBytes(StandardCharsets.UTF_8), pair.getPrivate());

        byte[] changed = "Сәлем!".getBytes(StandardCharsets.UTF_8);

        assertFalse(signer.verify(changed, signature, pair.getPublic()));
    }

    @Test
    void signatureFromOtherUserIsRejected() throws Exception {
        KeyPair alice = crypto.generateRsaKeyPair();
        KeyPair attacker = crypto.generateRsaKeyPair();
        byte[] message = "Сәлем".getBytes(StandardCharsets.UTF_8);

        byte[] forged = signer.sign(message, attacker.getPrivate());

        assertFalse(signer.verify(message, forged, alice.getPublic()));
    }
}
