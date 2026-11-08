package kz.chat.exception;

/** Ескі хабар қайта жіберілгенде шығады (replay шабуылы). */
public class ReplayAttackException extends Exception {
    public ReplayAttackException(String message) {
        super(message);
    }
}