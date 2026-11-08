package kz.chat.crypto;

import java.security.GeneralSecurityException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;

/**
 * Цифрлық қолтаңба қызметі (SHA256withRSA): ЭЦП қағидасы.
 * Жабық кілтпен қол қояды, ашық кілтпен тексереді.
 */
public class SignatureService {

    private static final String ALGORITHM = "SHA256withRSA";

    /** Деректің хэшіне жабық кілтпен қол қояды. */
    public byte[] sign(byte[] data, PrivateKey privateKey) throws GeneralSecurityException {
        Signature signature = Signature.getInstance(ALGORITHM);
        signature.initSign(privateKey);
        signature.update(data);
        return signature.sign();
    }

    /** Қолтаңбаны ашық кілтпен тексереді. Сәйкес болса true. */
    public boolean verify(byte[] data, byte[] signatureBytes, PublicKey publicKey)
            throws GeneralSecurityException {
        Signature signature = Signature.getInstance(ALGORITHM);
        signature.initVerify(publicKey);
        signature.update(data);
        return signature.verify(signatureBytes);
    }
}