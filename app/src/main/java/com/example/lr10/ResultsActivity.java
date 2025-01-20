package com.example.lr10;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ResultsActivity extends AppCompatActivity {

    private TextView scoreTextView;
    private TextView recommendationTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        // Подключаем текстовые поля для отображения результатов
        scoreTextView = findViewById(R.id.scoreTextView);
        recommendationTextView = findViewById(R.id.recommendationTextView);

        // Получаем данные из Intent
        Intent intent = getIntent();
        int correctAnswers = intent.getIntExtra("correctAnswers", 0);
        int totalQuestions = intent.getIntExtra("totalQuestions", 0);

        // Вычисляем итоговый результат
        int score = correctAnswers * 100 / totalQuestions;

        // Отображаем результаты
        scoreTextView.setText("Правильных ответов: " + correctAnswers + " из " + totalQuestions);
        recommendationTextView.setText(getRecommendation(score));
    }

    // Метод для получения рекомендаций в зависимости от процента правильных ответов
    private String getRecommendation(int score) {
        if (score == 100) {
            return "Отличный результат!";
        } else if (score >= 50) {
            return "Хорошо, но есть над чем работать.";
        } else {
            return "Рекомендуем повторить материал.";
        }
    }
}
