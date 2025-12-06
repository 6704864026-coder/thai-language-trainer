package org.global.academy;

public class ThaiConsonantFlashcard extends Flashcard {
    private String details;
    private String classGroup;

    public ThaiConsonantFlashcard(String front, String back, String details, String classGroup) {
        super(front, back);
        this.details = details;
        this.classGroup = classGroup;
    }
}