package com.example.calculatorrsproject;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.ezylang.evalex.EvaluationException;
import com.ezylang.evalex.Expression;
import com.ezylang.evalex.data.EvaluationValue;
import com.ezylang.evalex.parser.ParseException;

public class MainActivity extends AppCompatActivity {

    private EditText inputEdittext,outputEdittext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initializeViews();

    }

    public void buttonClicked(View view) throws EvaluationException, ParseException {
        Button button = (Button) view;
        String textButton = button.getText().toString();
        String oldInputText = inputEdittext.getText().toString();

        if(textButton.equalsIgnoreCase("C"))
        {
            int length = oldInputText.length();
            if(length > 1)
            {
                String result =  oldInputText.substring(0,length-1);
                inputEdittext.setText(result);
            }
            else {
                inputEdittext.setText("");
            }
        }
        else if (textButton.equalsIgnoreCase("D"))
        {
            inputEdittext.setText("");
            outputEdittext.setText("");
        }
        else if (textButton.equalsIgnoreCase("X"))
        {
            inputEdittext.setText(oldInputText + "*");
        }
        else if (textButton.equalsIgnoreCase("="))
        {
            if(oldInputText.trim().equalsIgnoreCase(""))
            {
                inputEdittext.setText(outputEdittext.getText().toString());
                outputEdittext.setText("");
            }
            // Calculating the expression
            try {
                Expression expression = new Expression(oldInputText);
                EvaluationValue result = expression.evaluate();
                outputEdittext.setText(result.getStringValue());
                inputEdittext.setText("");
            } catch (EvaluationException e) {
                Toast.makeText(this,"Cannot Evaluate Expression",Toast.LENGTH_SHORT).show();
            } catch (ParseException e) {
                Toast.makeText(this,"Invalid Expression",Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            inputEdittext.setText(oldInputText + textButton);
        }
    }

    public void initializeViews()
    {
        inputEdittext = findViewById(R.id.inputTextField);
        outputEdittext = findViewById(R.id.outputTextField);
    }
}