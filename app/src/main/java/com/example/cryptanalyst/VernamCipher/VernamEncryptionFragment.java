package com.example.cryptanalyst.VernamCipher;

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

public class VernamEncryptionFragment extends Fragment {

    String plaintext, key;
    EditText input_plaintext, input_key;
    Button encrypt, clear;
    LinearLayout ans, input_layout;
    TextView ans_plaintext, ans_key, ans_cipher, note;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_alpha_key_encryption, container, false);

        input_plaintext = root.findViewById(R.id.input_plaintext);
        input_key = root.findViewById(R.id.input_key);

        encrypt = root.findViewById(R.id.encrypt);
        ans = root.findViewById(R.id.ans);
        ans_plaintext = root.findViewById(R.id.plaintext);
        ans_key = root.findViewById(R.id.key);
        ans_cipher = root.findViewById(R.id.ciphertext);
        note = root.findViewById(R.id.note);
        note.setText("Plaintext and key length must be same!!!");
        input_layout = root.findViewById(R.id.input_layout);
        clear = root.findViewById(R.id.clear);

        encrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                plaintext = input_plaintext.getText().toString().toUpperCase().replaceAll("[^A-Z]", "");
                key = input_key.getText().toString().toUpperCase().replaceAll("[^A-Z]", "");

                Pattern pattern = Pattern.compile("[a-z A-Z]+");

                if (input_plaintext.getText().toString().isEmpty()) {
                    input_plaintext.setError("Empty");
                    input_plaintext.requestFocus();
                } else if (!pattern.matcher(input_plaintext.getText().toString()).matches()) {
                    input_plaintext.setError("Only alphabetic text is allowed!!!");
                    input_plaintext.requestFocus();
                } else if (input_key.getText().toString().isEmpty()) {
                    input_key.setError("Empty");
                    input_key.requestFocus();
                } else if (!pattern.matcher(input_key.getText().toString()).matches()) {
                    input_key.setError("Only alphabetic Key is allowed!!!");
                    input_key.requestFocus();
                } else if (key.length() != plaintext.length()) {
                    input_key.setError("Plaintext and key length must be same!!!");
                    input_key.requestFocus();
                } else {
                    try {
                        doEncryption(plaintext, key);
                    } catch (Exception e){
                        Toast.makeText(getContext(), "Something Went Wrong!!! "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
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

    private void doEncryption(String text, String key) {

        // Initializing cipherText
        String cipherText = "";

        // Initialize cipher array of key length
        // which stores the sum of corresponding no.'s
        // of plainText and key.
        int cipher[] = new int[key.length()];


            for (int i = 0; i < key.length(); i++) {
                cipher[i] = text.charAt(i) - 'A'
                        + key.charAt(i)
                        - 'A';
            }

            // If the sum is greater than 25
            // subtract 26 from it
            // and store that resulting value
            for (int i = 0; i < key.length(); i++) {
                if (cipher[i] > 25) {
                    cipher[i] = cipher[i] - 26;
                }
            }

            // Converting the no.'s into integers

            // Convert these integers to corresponding
            // characters and add them up to cipherText
            for (int i = 0; i < key.length(); i++) {
                int x = cipher[i] + 'A';
                cipherText += (char) x;
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