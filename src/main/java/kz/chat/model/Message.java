package kz.chat.model;

import java.nio.charset.StandardCharsets;

/** Қарапайым хабар: жіберуші, мәтін, нөмір (counter). */
public class Message {

    private final String sender;
    private final String text;
    private final long counter;

    public Message(String sender, String text, long counter) {
        if (sender == null || sender.isBlank()) {
            throw new IllegalArgumentException("Sender must not be empty");
        }
        if (text == null) {
            throw new IllegalArgumentException("Text must not be null");
        }
        if (counter < 0) {
            throw new IllegalArgumentException("Counter must not be negative");
        }
        this.sender = sender;
        this.text = text;
        this.counter = counter;
    }

    public String getSender() {
        return sender;
    }

    public String getText() {
        return text;
    }

    public long getCounter() {
        return counter;
    }

    /** Қол қоюға арналған байттар (жіберуші + counter + мәтін). */
    public byte[] toBytes() {
        return (sender + "|" + counter + "|" + text).getBytes(StandardCharsets.UTF_8);
    }
}