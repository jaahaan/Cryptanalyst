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

public class CaesarEncryptionFragment extends Fragment {

    String plainText, key;
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
        note.setText("Julius Caesar user a key of 3 to communicate with his officers!!!");
        input_layout = root.findViewById(R.id.input_layout);
        clear = root.findViewById(R.id.clear);

        encrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doEncryption();
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
    private void doEncryption() {
        plainText = input_plaintext.getText().toString().toUpperCase().replaceAll("[^A-Z]", "");
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
            StringBuffer result= new StringBuffer();
            int finalKeyValue=Integer.parseInt(key);
            for (int i=0; i<plainText.length(); i++)
            {
                char ch = (char)(((int)plainText.charAt(i) +
                        finalKeyValue - 65) % 26 + 65);
                result.append(ch);
            }

            /**
            final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
            String result = "";
            int finalKeyValue=Integer.parseInt(key);

            for (int i = 0; i < plaintext.length(); i++)
            {
                int charPosition = ALPHABET.indexOf(plaintext.charAt(i));
                int keyVal = (charPosition + finalKeyValue) % 26;
                if (keyVal < 0)
                {
                    keyVal = ALPHABET.length() + keyVal;
                }
                char replaceVal = ALPHABET.charAt(keyVal);
                result += replaceVal;
            }*/

            ans.setVisibility(View.VISIBLE);
            input_layout.setVisibility(View.GONE);
            ans_plaintext.setText("Plaintext: " + input_plaintext.getText().toString());
            ans_key.setText("Key: " + input_key.getText().toString());
            ans_cipher.setText(result);
            input_plaintext.setText("");
            input_key.setText("");
        }

    }
}