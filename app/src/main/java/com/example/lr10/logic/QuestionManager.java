package com.example.lr10.logic;

import java.util.ArrayList;
import java.util.List;

public class QuestionManager {
    private List<Question> questions;
    private int score;

    public QuestionManager() {
        questions = new ArrayList<>();
        score = 0;
    }

    public void addQuestion(Question question) {
        questions.add(question);
    }

    public Question getQuestion(int index) {
        return questions.get(index);
    }

    public int getTotalQuestions() {
        return questions.size();
    }

    public boolean checkAnswer(int questionIndex, int selectedAnswer) {
        boolean isCorrect = questions.get(questionIndex).getCorrectAnswerIndex() == selectedAnswer;
        if (isCorrect) {
            score++;
        }
        return isCorrect;
    }

    public int getScore() {
        return score;
    }

    public String getRecommendation() {
        if (score == questions.size()) {
            return "Отличный результат!";
        } else if (score >= questions.size() / 2) {
            return "Хорошо, но есть над чем работать.";
        } else {
            return "Рекомендуем повторить материал.";
        }
    }
}

