package com.asn8.calculator;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText editTextInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextInput = findViewById(R.id.editTextInput);

        editTextInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(editTextInput.getWindowToken(), 0);
                }
            }
        });
    }

    public void onButtonClick(View view) {
        Button button = (Button) view;
        String buttonText = button.getText().toString();
        editTextInput.append(buttonText);
    }
    public void onACClick(View view) {
        editTextInput.setText("");
    }

    public void onBsClick(View view) {
        String currentText = editTextInput.getText().toString();
        if (!currentText.isEmpty()) {
            String newText = currentText.substring(0, currentText.length() - 1);
            editTextInput.setText(newText);
        }
    }

    public void onEqsButtonClick(View view) {
        try {
            String input = editTextInput.getText().toString();
            evaluateJavaScriptExpression(input);
        } catch (Exception e) {
            editTextInput.setText("Error");
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void evaluateJavaScriptExpression(String expression) {
        WebView webView = new WebView(this);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new JSInterface(), "AndroidFunction");

        String html = "<html><body><script>var result = eval('" + expression + "'); AndroidFunction.setResult(result);</script></body></html>";
        webView.loadData(html, "text/html", null);
    }

    class JSInterface {
        @JavascriptInterface
        public void setResult(final String result) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        double parsedResult = Double.parseDouble(result);
                        editTextInput.setText(String.valueOf(parsedResult));
                    } catch (NumberFormatException e) {
                        editTextInput.setText("Error");
                    }
                }
            });
        }
    }
}
