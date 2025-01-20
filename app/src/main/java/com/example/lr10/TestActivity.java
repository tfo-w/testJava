package com.example.lr10;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;
import com.example.lr10.logic.QuestionManager;
import com.example.lr10.logic.Question;

public class TestActivity extends AppCompatActivity {

    private TextView questionTextView;
    private RadioGroup answersGroup;
    private RadioButton answer1, answer2, answer3;
    private Button nextButton, prevButton, finishButton;
    private QuestionManager questionManager;
    private int currentQuestionIndex = 0;
    private List<Integer> selectedAnswers; // Сохраняем ответы пользователя

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        // Инициализация View
        ImageView questionImageView = findViewById(R.id.questionImageView);
        answersGroup = findViewById(R.id.answersGroup);
        answer1 = findViewById(R.id.answer1);
        answer2 = findViewById(R.id.answer2);
        answer3 = findViewById(R.id.answer3);
        nextButton = findViewById(R.id.nextButton);
        prevButton = findViewById(R.id.prevButton);
        finishButton = findViewById(R.id.finishButton);

        // Инициализация менеджера вопросов и списка выбранных ответов
        questionManager = initializeQuestions();
        selectedAnswers = new ArrayList<>();
        for (int i = 0; i < questionManager.getTotalQuestions(); i++) {
            selectedAnswers.add(-1); // -1 означает, что ответ не выбран
        }

        // Установка первого вопроса
        displayQuestion(currentQuestionIndex);

        // Обработчики кнопок
        nextButton.setOnClickListener(v -> moveToNextQuestion());
        prevButton.setOnClickListener(v -> moveToPreviousQuestion());
        finishButton.setOnClickListener(v -> finishTest());
    }

    private QuestionManager initializeQuestions() {
        QuestionManager manager = new QuestionManager();

        // Добавляем вопросы с изображениями (теперь без четвёртого параметра)
        manager.addQuestion(new Question("q1", "В какой из указанных стран наибольшая средняя плотность населения на 1 км²?",
                new String[]{"Великобритания", "Япония", "Ливан", "Бахрейн"}, 1));
        manager.addQuestion(new Question("q2", "В какой из указанных стран наибольшая протяженность береговой линии?",
                new String[]{"Китай", "Россия", "Канада"}, 2));
        manager.addQuestion(new Question("q3", "Условная точка в Мировом океане, наиболее удалённая от какой-либо суши на Земле",
                new String[]{"Точка Немо", "Остров Ноль", "Бездна Челленджера"}, 0));
        manager.addQuestion(new Question("q4", "Крайняя восточная точка Африки",
                new String[]{"Мыс Бланко", "Мыс Рас-Хафун", "Мыс Игольный"}, 1));
        manager.addQuestion(new Question("q5", "Межгорная впадина, которая является самой низкой точкой Северной Америки",
                new String[]{"Долина Смерти", "Маракайбо", "Гиссарская долина"}, 0));
        manager.addQuestion(new Question("q6", "Какая из этих стран НЕ граничит с Италией?",
                new String[]{"Германия", "Австрия", "Франция"}, 1));

        return manager;
    }

    private void displayQuestion(int index) {
        Question question = questionManager.getQuestion(index);

        // Устанавливаем изображение вопроса
        ImageView questionImageView = findViewById(R.id.questionImageView);
        int imageResId = getResources().getIdentifier(question.getImageName(), "drawable", getPackageName());
        if (imageResId != 0) {
            questionImageView.setImageResource(imageResId);
        } else {
            questionImageView.setImageResource(R.drawable.default_image); // На случай отсутствия картинки
        }

        // Устанавливаем текст вопроса
        TextView questionTextView = findViewById(R.id.questionTextView);
        questionTextView.setText(question.getQuestionText());  // Получаем текст вопроса из объекта Question

        // Устанавливаем ответы
        String[] answers = question.getAnswers();
        answer1.setText(answers[0]);
        answer2.setText(answers[1]);
        answer3.setText(answers[2]);

        // Устанавливаем ранее выбранный ответ
        if (selectedAnswers.get(index) != -1) {
            int selectedAnswerIndex = selectedAnswers.get(index);
            ((RadioButton) answersGroup.getChildAt(selectedAnswerIndex)).setChecked(true);
        } else {
            answersGroup.clearCheck();
        }

        // Обновляем состояние кнопок
        prevButton.setEnabled(index > 0);
        nextButton.setEnabled(index < questionManager.getTotalQuestions() - 1);
    }

    private void moveToNextQuestion() {
        saveSelectedAnswer();
        if (currentQuestionIndex < questionManager.getTotalQuestions() - 1) {
            currentQuestionIndex++;
            displayQuestion(currentQuestionIndex);
        }
    }

    private void moveToPreviousQuestion() {
        saveSelectedAnswer();
        if (currentQuestionIndex > 0) {
            currentQuestionIndex--;
            displayQuestion(currentQuestionIndex);
        }
    }

    private void saveSelectedAnswer() {
        int selectedId = answersGroup.getCheckedRadioButtonId();
        if (selectedId != -1) {
            int selectedIndex = answersGroup.indexOfChild(findViewById(selectedId));
            selectedAnswers.set(currentQuestionIndex, selectedIndex);
        }
    }

    private void finishTest() {
        saveSelectedAnswer();

        int correctAnswers = 0;
        for (int i = 0; i < questionManager.getTotalQuestions(); i++) {
            if (selectedAnswers.get(i) != -1 &&
                    questionManager.checkAnswer(i, selectedAnswers.get(i))) {
                correctAnswers++;
            }
        }

        Intent intent = new Intent(this, ResultsActivity.class);
        intent.putExtra("correctAnswers", correctAnswers);
        intent.putExtra("totalQuestions", questionManager.getTotalQuestions());
        startActivity(intent);
        finish();
    }
}
