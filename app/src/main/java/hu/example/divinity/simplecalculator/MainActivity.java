package hu.example.divinity.simplecalculator;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

    private TextView result;

    private String operator;

    private Set<String> numbers;

    private Set<String> operators;

    private String[] valueArray;

    private Boolean operatorClicked = false;

    private Boolean negativeNum = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        result = (TextView) findViewById(R.id.result);
    }


    private void initNumbers() {
        numbers = new HashSet<String>();
        for (int i = 0; i < 10; i++) {
            numbers.add(Integer.toString(i));
        }
    }

    private void initOperators() {
        operators = new HashSet<String>();
        String[] ops = { "+", "-", "*", "/" };
        for (String operator : ops) {
            operators.add(operator);
        }
    }

    public void handleClick(View view) {
        Button clicked = (Button) view;
        String value = clicked.getText().toString();
        String operator = containOperator(result.getText().toString());

        Log.d("##### operatorClicked: ", operatorClicked.toString());
        Log.d("##### clicked: ", value);

        if (operatorClicked && !isNumerical(value)) {
                value = result.getText().toString().substring(0,result.getText().toString().length()-1) + value;
        } else if (isNumerical(value)) {
            if (!isDefaultResult(result.getText().toString())) {
                value = result.getText().toString() + value;
                operatorClicked = false;
            }
        } else if (isOperator(value)) {
            operatorClicked = true;
            if (!isDefaultResult(result.getText().toString())) {
                if (operator == null) {
                    operator = value;
                    value = result.getText().toString() + value;
                } else {
                    Log.d("##### RESULT after ", result.getText().toString());
                    if (negativeNum) {
                        valueArray =  result.getText().toString().substring(1,result.getText().toString().length()).split((Pattern.quote(operator)));
                        double a = Double.parseDouble("-" +valueArray[0]), b = Double.parseDouble(valueArray[1]);
                        value = calculate(a,b,operator) + value;
                    } else {
                        valueArray =  result.getText().toString().split((Pattern.quote(operator)));
                        double a = Double.parseDouble(valueArray[0]), b = Double.parseDouble(valueArray[1]);
                        value = calculate(a,b,operator) + value;
                    }

                    operator = null;
                    valueArray = null;
                }
            }
        } else if (isClear(value)) {
            value = "Eredmény";
        } else {
            if (!isDefaultResult(result.getText().toString())) {
                if (operator != null) {
                    if (negativeNum) {
                        valueArray =  result.getText().toString().substring(1,result.getText().toString().length()).split((Pattern.quote(operator)));
                        double a = Double.parseDouble("-" + valueArray[0]), b = Double.parseDouble(valueArray[1]);
                        value = calculate(a,b,operator);
                    } else {
                        valueArray =  result.getText().toString().split((Pattern.quote(operator)));
                        double a = Double.parseDouble(valueArray[0]), b = Double.parseDouble(valueArray[1]);
                        value = calculate(a,b,operator);
                    }

                } else value = result.getText().toString();
                operator = null;
                valueArray = null;
            } else value = "Eredmény";
        }
        result.setText(value);
    }

    private boolean isClear(String value) {
        return value.equals("CE");
    }


    private boolean isOperator(String value) {
        if (operators == null) {
            initOperators();
        }
        return operators.contains(value);
    }
    private String containOperator(String value) {
        if (operators == null) {
            initOperators();
        }

        if (Character.toString(value.charAt(0)).equals("-")) {
            negativeNum = true;
            for(int i = 1; i < value.length(); i++) {
                if(operators.contains(Character.toString(value.charAt(i)))) {
                    Log.d("##### containOperator: ", Character.toString(value.charAt(i)));
                    return Character.toString(value.charAt(i));

                }
            }
        } else {
            negativeNum = false;
            for(int i = 0; i < value.length(); i++) {
                if(operators.contains(Character.toString(value.charAt(i)))) {
                    Log.d("##### containOperator: ", Character.toString(value.charAt(i)));
                    return Character.toString(value.charAt(i));

                }
            }
        }


        return null;
    }
    private boolean isDefaultResult(String value) {
        return value.equals("Eredmény");
    }

    private boolean isNumerical(String value) {
        if (numbers == null) {
            initNumbers();
        }
        return numbers.contains(value);
    }

    private String calculate( double a, double b, String operator) {
        String value = null;
        Log.d("a: ", String.valueOf(a));
        Log.d("b: ", String.valueOf(b));
        Log.d("Operator: ", operator);
        if (operator.equals("+")) {
            value = Double.toString(a + b);
        } else if (operator.equals("-")) {
            value = Double.toString(a - b);
        } else if (operator.equals("*")) {
            value = Double.toString(a * b);
        } else if (operator.equals("/")) {
            value = Double.toString(a / b);
        }
        return value;
    }
}
