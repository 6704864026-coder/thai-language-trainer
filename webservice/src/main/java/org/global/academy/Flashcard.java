package org.global.academy;

public class Flashcard {
    protected String front;
    protected String back;

    public Flashcard(String front, String back) {
        this.front = front;
        this.back = back;
    }
    
    // Getters are needed for GSON to convert to JSON
    public String getFront() { return front; }
    public String getBack() { return back; }
}