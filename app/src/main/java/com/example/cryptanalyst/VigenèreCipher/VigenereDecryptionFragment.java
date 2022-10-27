package com.example.cryptanalyst.VigenèreCipher;

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

public class VigenereDecryptionFragment extends Fragment {

    String cipher, key;
    EditText input_cipher, input_key;
    Button decrypt, clear;
    LinearLayout ans, input_layout;
    TextView ans_plaintext, ans_key, ans_cipher, note;
    private static char sortedKey[];
    private static int sortedKeyPos[];

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_alpha_key_decryption, container, false);

        input_cipher = root.findViewById(R.id.input_cipher);
        input_key = root.findViewById(R.id.input_key);

        decrypt = root.findViewById(R.id.decrypt);
        ans = root.findViewById(R.id.ans);
        ans_plaintext = root.findViewById(R.id.plaintext);
        ans_key = root.findViewById(R.id.key);
        ans_cipher = root.findViewById(R.id.ciphertext);
        note = root.findViewById(R.id.note);
        note.setVisibility(View.GONE);
        input_layout = root.findViewById(R.id.input_layout);
        clear = root.findViewById(R.id.clear);

        ans.setVisibility(View.GONE);
        decrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cipher = input_cipher.getText().toString().toUpperCase().replaceAll("[^A-Z]", "");
                key = input_key.getText().toString().toUpperCase().replaceAll("[^A-Z]", "");
                sortedKeyPos = new int[key.length()];
                sortedKey = key.toCharArray();
                Pattern pattern = Pattern.compile("[a-z A-Z]+");

                if (input_cipher.getText().toString().isEmpty()) {
                    input_cipher.setError("Empty");
                    input_cipher.requestFocus();
                } else if (!pattern.matcher(input_cipher.getText().toString()).matches()) {
                    input_cipher.setError("Only alphabetic text is allowed!!!");
                    input_cipher.requestFocus();
                } else if (input_key.getText().toString().isEmpty()) {
                    input_key.setError("Empty");
                    input_key.requestFocus();
                } else if (!pattern.matcher(input_key.getText().toString()).matches()) {
                    input_key.setError("Only alphabetic Key is allowed!!!");
                    input_key.requestFocus();
                } else {
                    try {
                        doDecryption(cipher, key);
                    } catch (Exception e){
                        Toast.makeText(getContext(), "Something Went Wrong!!!", Toast.LENGTH_SHORT).show();
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

    private void doDecryption(String text, final String key)
    {
        String result = "";
        text = text.toUpperCase();

        for (int i = 0, j = 0; i < text.length(); i++)
        {
            char c = text.charAt(i);
            if (c < 'A' || c > 'Z')
                continue;
            result += (char) ((c - key.charAt(j) + 26) % 26 + 'A');
            j = ++j % key.length();
        }


        ans.setVisibility(View.VISIBLE);
        input_layout.setVisibility(View.GONE);
        ans_cipher.setText("Ciphertext: "+input_cipher.getText().toString());
        ans_key.setText("Key: "+input_key.getText().toString());
        ans_plaintext.setText(result);
        input_cipher.setText("");
        input_key.setText("");
    }

}