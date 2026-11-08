package kz.chat.model;

/** Қолтаңбасы бар хабар. Message-тен мұрагерлік алады. */
public class SignedMessage extends Message {

    private final byte[] signature;

    public SignedMessage(String sender, String text, long counter, byte[] signature) {
        super(sender, text, counter);
        if (signature == null || signature.length == 0) {
            throw new IllegalArgumentException("Signature must not be empty");
        }
        this.signature = signature.clone();
    }

    public byte[] getSignature() {
        return signature.clone();
    }
}