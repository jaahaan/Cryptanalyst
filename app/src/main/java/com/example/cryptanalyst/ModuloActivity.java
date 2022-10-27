package com.example.cryptanalyst;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ModuloActivity extends AppCompatActivity {

    String integer, modulo;
    EditText input_integer, input_modulo;
    Button calculate;
    LinearLayout ans_field;
    TextView ans_header, ans, note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modulo);

        input_integer= findViewById(R.id.integer);
        input_modulo= findViewById(R.id.modulo);
        calculate = findViewById(R.id.calculate);
        ans_field = findViewById(R.id.ans_field);
        ans = findViewById(R.id.ans);
        //note = findViewById(R.id.note);

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                integer = input_integer.getText().toString();
                modulo = input_modulo.getText().toString();
                if (integer.isEmpty()){
                    input_integer.setError("Empty");
                    input_integer.requestFocus();
                } if (modulo.isEmpty()){
                    input_modulo.setError("Empty");
                    input_modulo.requestFocus();
                } else {
                    try {
                        int finalIntegerValue=Integer.parseInt(integer);
                        int finalModuloValue=Integer.parseInt(modulo);
                        ans_field.setVisibility(View.VISIBLE);
                        ans.setText(String.valueOf(finalIntegerValue%finalModuloValue));
                    } catch (Exception e){
                        Toast.makeText(getApplicationContext(), "Something Went Wrong!!!", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

    }
}