package com.example.cryptanalyst.RailFenceCipher;

import static java.lang.Math.ceil;
import static java.lang.Math.round;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cryptanalyst.R;

import java.util.regex.Pattern;

public class RailFenceEncryptionFragment extends Fragment {

    String plaintext, key;
    EditText input_plaintext, input_key;
    Button encrypt, clear;
    LinearLayout ans, input_layout;
    TextView ans_plaintext, ans_key, ans_cipher, note;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_encryption, container, false);

        input_plaintext = root.findViewById(R.id.input_plaintext);
        input_key = root.findViewById(R.id.input_key);

        encrypt = root.findViewById(R.id.encrypt);
        ans = root.findViewById(R.id.ans);
        ans_plaintext = root.findViewById(R.id.plaintext);
        ans_key = root.findViewById(R.id.key);
        ans_cipher = root.findViewById(R.id.ciphertext);
        note = root.findViewById(R.id.note);
        note.setText("To adjust length, underscore(_) is used at the end of the plaintext");
        input_layout = root.findViewById(R.id.input_layout);
        clear = root.findViewById(R.id.clear);

        encrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    generateCipher();
                }catch (Exception e){
                    Toast.makeText(getContext(),"ArrayIndexOutOfBoundsException!!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ans.setVisibility(View.GONE);
                input_layout.setVisibility(View.VISIBLE);
            }
        });
        // Inflate the layout for this fragment
        return root;
    }

    private void generateCipher() {
        plaintext = input_plaintext.getText().toString().toUpperCase().replaceAll("[^A-Z]", "");
        key = input_key.getText().toString().replaceAll(" ", "");
        Pattern pattern = Pattern.compile("[a-z A-Z]+");

        if (input_plaintext.getText().toString().isEmpty()) {
            input_plaintext.setError("Empty");
            input_plaintext.requestFocus();
        } else if (!pattern.matcher(input_plaintext.getText().toString()).matches()) {
            input_plaintext.setError("Only alphabetic text is allowed!!!");
            input_plaintext.requestFocus();
        } else if (key.isEmpty()) {
            input_key.setError("Empty");
            input_key.requestFocus();
        } else {
            double finalKeyValue = Integer.parseInt(key), len = plaintext.length();
            double c =  ceil(len / finalKeyValue);

            char mat[][] = new char[(int) finalKeyValue][(int) c];
            int k = 0;

            String cipherText = "";

                for (int i = 0; i < c; i++) {
                    for (int j = 0; j < finalKeyValue; j++) {
                        if (k != len)
                            mat[j][i] = plaintext.charAt(k++);
                        else
                            mat[j][i] = '_';
                    }
                }
                for (int i = 0; i < finalKeyValue; i++) {
                    for (int j = 0; j < c; j++) {
                        cipherText += mat[i][j];
                    }
                }
                ans.setVisibility(View.VISIBLE);
                input_layout.setVisibility(View.GONE);
                ans_plaintext.setText("Plaintext: " + input_plaintext.getText().toString());
                ans_key.setText("Key: " + input_key.getText().toString());
                ans_cipher.setText(cipherText);
                input_plaintext.setText("");
                input_key.setText("");


        }
    }
}