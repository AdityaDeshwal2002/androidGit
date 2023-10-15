package com.asn8.tempconverter;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import com.asn8.tempconverter.databinding.ActivityMainBinding;
import java.math.BigDecimal;
import java.math.RoundingMode;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this, R.array.weight_spinner, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.lengthChooserSpinner.setAdapter(arrayAdapter);

        binding.lengthChooserSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    binding.rlMilli.setVisibility(View.GONE);
                    binding.rlCenti.setVisibility(View.VISIBLE);
                    binding.rlDeci.setVisibility(View.VISIBLE);
                    binding.calculateButton.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View view) {
                            String c = binding.numberInput.getText().toString();
                            if (c.isEmpty()) {
                                Toast.makeText(getApplicationContext(), "Please enter a number", Toast.LENGTH_SHORT).show();
                            } else {
                                BigDecimal b = new BigDecimal(c);
                                binding.tvCentiNbs.setText(String.valueOf(b.multiply(new BigDecimal("9")).divide(new BigDecimal("5"), 2, RoundingMode.HALF_UP).add(new BigDecimal("32"))));
                                binding.tvDeciNbs.setText(String.valueOf(b.add(new BigDecimal("273.15"))));
                            }
                        }
                    });
                } else if (i == 1) {
                    binding.rlMilli.setVisibility(View.VISIBLE);
                    binding.rlCenti.setVisibility(View.GONE);
                    binding.rlDeci.setVisibility(View.VISIBLE);
                    binding.calculateButton.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View view) {
                            String c = binding.numberInput.getText().toString();
                            if (c.isEmpty()) {
                                Toast.makeText(getApplicationContext(), "Please enter a number", Toast.LENGTH_SHORT).show();
                            } else {
                                BigDecimal b = new BigDecimal(c);
                                binding.tvMilliNbs.setText(String.valueOf(b.subtract(new BigDecimal("32")).multiply(new BigDecimal("5")).divide(new BigDecimal("9"), 2, RoundingMode.HALF_UP)));
                                binding.tvDeciNbs.setText(String.valueOf(b.subtract(new BigDecimal("32"))
                                        .multiply(new BigDecimal("5"))
                                        .divide(new BigDecimal("9"), 2, RoundingMode.HALF_UP)
                                        .add(new BigDecimal("273.15"))));
                            }
                        }
                    });
                } else if (i == 2) {
                    binding.rlMilli.setVisibility(View.VISIBLE);
                    binding.rlCenti.setVisibility(View.VISIBLE);
                    binding.rlDeci.setVisibility(View.GONE);
                    binding.calculateButton.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View view) {
                            String c = binding.numberInput.getText().toString();
                            if (c.isEmpty()) {
                                Toast.makeText(getApplicationContext(), "Please enter a number", Toast.LENGTH_SHORT).show();
                            } else {
                                BigDecimal b = new BigDecimal(c);
                                binding.tvMilliNbs.setText(String.valueOf(b.subtract(new BigDecimal("273.15"))));
                                binding.tvCentiNbs.setText(String.valueOf(b.multiply(new BigDecimal("9"))
                                        .divide(new BigDecimal("5"), 2, RoundingMode.HALF_UP)
                                        .subtract(new BigDecimal("459.67"))));
                            }
                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}

