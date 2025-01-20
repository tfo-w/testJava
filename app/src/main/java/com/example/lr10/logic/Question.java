package com.example.lr10.logic;

public class Question {
    private String questionText;
    private String imageName; // Имя файла изображения
    private String[] answers;
    private int correctAnswerIndex;

    public Question(String imageName, String questionText, String[] answers, int correctAnswerIndex) {
        this.imageName = imageName;
        this.questionText = questionText;
        this.answers = answers;
        this.correctAnswerIndex = correctAnswerIndex;
    }

    public String getQuestionText() {
        return questionText;
    }

    public String[] getAnswers() {
        return answers;
    }

    public int getCorrectAnswerIndex() {
        return correctAnswerIndex;
    }

    public String getImageName() {
        return imageName;
    }
}

