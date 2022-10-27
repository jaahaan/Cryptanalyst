package com.example.cryptanalyst.CaesarCipher;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.cryptanalyst.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Pattern;


public class CaesarDecryptionFragment extends Fragment {

    String cipher, key;
    EditText input_cipher, input_key;
    Button decrypt, clear;
    LinearLayout ans, input_layout;
    TextView ans_plaintext, ans_key, ans_cipher, note;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_decryption, container, false);

        input_cipher = root.findViewById(R.id.input_cipher);
        input_key = root.findViewById(R.id.input_key);

        decrypt = root.findViewById(R.id.decrypt);
        ans = root.findViewById(R.id.ans);
        ans_plaintext = root.findViewById(R.id.plaintext);
        ans_key = root.findViewById(R.id.key);
        ans_cipher = root.findViewById(R.id.ciphertext);
        note = root.findViewById(R.id.note);
        note.setText("Attackers can easily decrypt it using Brute Force Technique!!!");
        input_layout = root.findViewById(R.id.input_layout);
        clear = root.findViewById(R.id.clear);

        ans.setVisibility(View.GONE);
        decrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doDecryption();
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

    private void doDecryption() {
        cipher = input_cipher.getText().toString().replaceAll(" ", "");
        key = input_key.getText().toString().replaceAll(" ", "");
        Pattern pattern = Pattern.compile("[a-z A-Z]+");

        if (input_cipher.getText().toString().isEmpty()) {
            input_cipher.setError("Empty");
            input_cipher.requestFocus();
        } else if (!pattern.matcher(input_cipher.getText().toString()).matches()) {
            input_cipher.setError("Only alphabetic text is allowed!!!");
            input_cipher.requestFocus();
        } else if (key.isEmpty()) {
            input_key.setError("Empty");
            input_key.requestFocus();
        } else {
            final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
            String result = "";
            int finalKeyValue=Integer.parseInt(key);

            for (int i = 0; i < cipher.length(); i++)
            {
                int charPosition = ALPHABET.indexOf(cipher.charAt(i));
                int keyVal = (charPosition - finalKeyValue) % 26;
                if (keyVal < 0)
                {
                    keyVal = ALPHABET.length() + keyVal;
                }
                char replaceVal = ALPHABET.charAt(keyVal);
                result += replaceVal;
            }

            ans.setVisibility(View.VISIBLE);
            input_layout.setVisibility(View.GONE);
            ans_cipher.setText("Ciphertext: "+cipher);
            ans_key.setText("Key: "+key);
            ans_plaintext.setText(result);
            input_cipher.setText("");
            input_key.setText("");
        }

    }
}