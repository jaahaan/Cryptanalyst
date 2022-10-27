package com.example.cryptanalyst;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MultiplicativeInverseActivity extends AppCompatActivity {

    String value;
    EditText input_value;
    Button calculate;
    LinearLayout ans_field;
    TextView ans_header, ans, note;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiplicative_inverse);

        input_value= findViewById(R.id.input);
        calculate = findViewById(R.id.calculate);
        ans_field = findViewById(R.id.ans_field);
        ans_header = findViewById(R.id.ans_header);
        ans = findViewById(R.id.ans);
        //note = findViewById(R.id.note);

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                value = input_value.getText().toString();
                if (value.isEmpty()){
                    input_value.setError("Empty");
                    input_value.requestFocus();
                } else {
                    int i = 1;
                    int finalValue=Integer.parseInt(value);
                    try {
                        while(i>0){
                            if ((finalValue*i)%26==1){
                                ans_header.setText("Multiplicative inverse of "+value+ " is");
                                ans.setVisibility(View.VISIBLE);
                                ans.setText(String.valueOf(i));
                                ans_field.setVisibility(View.VISIBLE);
                                break;
                            }
                            else if ((finalValue*i)%26==0){
                                ans_header.setText("There is no Multiplicative inverse of "+value);
                                ans.setVisibility(View.GONE);
                                ans_field.setVisibility(View.VISIBLE);
                                break;
                            }
                            i++;
                        }
                    } catch (Exception e){
                        Toast.makeText(getApplicationContext(), "Something Went Wrong!!!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}