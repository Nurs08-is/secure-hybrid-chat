package kz.chat.exception;

/** Қолтаңба жарамсыз болғанда шығады. */
public class InvalidSignatureException extends Exception {
    public InvalidSignatureException(String message) {
        super(message);
    }
}