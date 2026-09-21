package com.example.rupamwindoora;

import android.app.Activity;
import android.os.Bundle;
import android.widget.*;
import android.view.View;

public class MainActivity extends Activity {

    String currentInput = "";
    String operator = "";
    double firstValue = 0;
    boolean newInput = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showLockScreen();
    }

    private void showLockScreen() {
        setContentView(R.layout.lock_screen);
        EditText pinBox = findViewById(R.id.pinBox);
        Button unlockBtn = findViewById(R.id.unlockBtn);

        unlockBtn.setOnClickListener(v -> {
            String entered = pinBox.getText().toString();
            if (entered.equals("7769")) {
                showCalculator();
            } else {
                Toast.makeText(this, "ভুল পাসওয়ার্ড", Toast.LENGTH_SHORT).show();
                pinBox.setText("");
            }
        });
    }

    private void showCalculator() {
        setContentView(R.layout.calculator);
        final TextView display = findViewById(R.id.display);

        int[] numberIds = {R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
                R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9};
        String[] numberLabels = {"0","1","2","3","4","5","6","7","8","9"};

        for (int i = 0; i < numberIds.length; i++) {
            Button b = findViewById(numberIds[i]);
            String label = numberLabels[i];
            b.setOnClickListener(v -> {
                if (newInput) { currentInput = ""; newInput = false; }
                currentInput += label;
                display.setText(currentInput);
            });
        }

        Button dot = findViewById(R.id.btnDot);
        dot.setOnClickListener(v -> {
            if (newInput) { currentInput = "0"; newInput = false; }
            if (!currentInput.contains(".")) {
                currentInput += ".";
                display.setText(currentInput);
            }
        });

        Button clear = findViewById(R.id.btnClear);
        clear.setOnClickListener(v -> {
            currentInput = "";
            firstValue = 0;
            operator = "";
            newInput = true;
            display.setText("0");
        });

        int[] opIds = {R.id.btnPlus, R.id.btnMinus, R.id.btnMultiply, R.id.btnDivide};
        String[] opLabels = {"+", "-", "×", "÷"};
        for (int i = 0; i < opIds.length; i++) {
            Button b = findViewById(opIds[i]);
            String op = opLabels[i];
            b.setOnClickListener(v -> {
                if (!currentInput.isEmpty()) {
                    firstValue = Double.parseDouble(currentInput);
                }
                operator = op;
                newInput = true;
            });
        }

        Button equals = findViewById(R.id.btnEquals);
        equals.setOnClickListener(v -> {
            if (!currentInput.isEmpty() && !operator.isEmpty()) {
                double secondValue = Double.parseDouble(currentInput);
                double result = 0;
                switch (operator) {
                    case "+": result = firstValue + secondValue; break;
                    case "-": result = firstValue - secondValue; break;
                    case "×": result = firstValue * secondValue; break;
                    case "÷": result = secondValue != 0 ? firstValue / secondValue : 0; break;
                }
                String resultText = (result == (long) result) ? String.valueOf((long) result) : String.valueOf(result);
                display.setText(resultText);
                currentInput = resultText;
                operator = "";
                newInput = true;
            }
        });
    }
}
