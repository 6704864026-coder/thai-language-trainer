package org.global.academy; // Your correct package

/**
 * This class holds data for a flashcard.
 * All graphics (Swing/AWT) code has been removed.
 */
public class Flashcard {

    private String front;
    private String back;
    private boolean learned;

    public Flashcard(String front, String back) {
        this.front = front;
        this.back = back;
        this.learned = false;
    }

    // Getters are needed for Gson to see the data
    public String getFront() {
        return front;
    }

    public String getBack() {
        return back;
    }

    public boolean isLearned() {
        return learned;
    }

    // Setter for 'learned' (optional but good to have)
    public void setLearned(boolean learned) {
        this.learned = learned;
    }
}