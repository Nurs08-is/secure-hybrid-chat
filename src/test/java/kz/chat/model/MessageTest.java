package kz.chat.model;

import kz.chat.crypto.CryptoService;
import kz.chat.crypto.SignatureService;
import org.junit.jupiter.api.Test;

import java.security.KeyPair;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MessageTest {

    @Test
    void emptySenderIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> new Message(" ", "hi", 1));
    }

    @Test
    void negativeCounterIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> new Message("alice", "hi", -1));
    }

    @Test
    void differentCounterGivesDifferentBytes() {
        Message first = new Message("alice", "hi", 1);
        Message second = new Message("alice", "hi", 2);
        assertFalse(java.util.Arrays.equals(first.toBytes(), second.toBytes()));
    }

    @Test
    void signedMessageKeepsMessageFieldsAndSignature() {
        byte[] sig = {1, 2, 3};
        SignedMessage signed = new SignedMessage("alice", "hi", 5, sig);
        assertEquals("alice", signed.getSender());
        assertEquals(5, signed.getCounter());
        assertArrayEquals(sig, signed.getSignature());
    }

    @Test
    void signedMessageCanBeVerified() throws Exception {
        CryptoService crypto = new CryptoService();
        SignatureService signer = new SignatureService();
        KeyPair pair = crypto.generateRsaKeyPair();

        Message message = new Message("alice", "hi", 1);
        byte[] signature = signer.sign(message.toBytes(), pair.getPrivate());
        SignedMessage signed = new SignedMessage("alice", "hi", 1, signature);

        assertTrue(signer.verify(signed.toBytes(), signed.getSignature(), pair.getPublic()));
    }
}