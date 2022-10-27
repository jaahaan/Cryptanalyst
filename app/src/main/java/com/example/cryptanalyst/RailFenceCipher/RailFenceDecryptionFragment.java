package com.example.cryptanalyst.RailFenceCipher;

import static java.lang.Math.ceil;

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


public class RailFenceDecryptionFragment extends Fragment {

    private String cipher, key;
    private EditText input_cipher, input_key;
    private Button decrypt, clear;
    private LinearLayout ans, input_layout;
    private TextView ans_plaintext, ans_key, ans_cipher, note;

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
        note.setText("To adjust length, underscore(_) is used at the end of the plaintext");
        input_layout = root.findViewById(R.id.input_layout);
        clear = root.findViewById(R.id.clear);

        ans.setVisibility(View.GONE);
        decrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    generatePlaintext();
                }catch (Exception e){
                    Toast.makeText(getContext(), "ArrayIndexOutOfBoundsException!!!  "+e.getMessage(), Toast.LENGTH_SHORT).show();
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

    private void generatePlaintext() {
        cipher = input_cipher.getText().toString().toUpperCase().replaceAll("[^A-Z_]", "");
        key = input_key.getText().toString().replaceAll(" ", "");
        Pattern pattern = Pattern.compile("[a-z A-Z_]+");

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
            double finalKeyValue = Integer.parseInt(key), len = cipher.length();
            double c =  ceil(len / finalKeyValue);

            char mat[][] = new char[(int) finalKeyValue][(int) c];
            int k = 0;
            String plainText="";
                for(int i=0;i< finalKeyValue;i++)
                {
                    for(int j=0;j< c;j++)
                    {
                        mat[i][j]=cipher.charAt(k++);
                    }
                }
                for(int i=0;i< c;i++)
                {
                    for(int j=0;j< finalKeyValue;j++)
                    {
                        plainText+=mat[j][i];
                    }
                }
                ans.setVisibility(View.VISIBLE);
                input_layout.setVisibility(View.GONE);
                ans_cipher.setText("Ciphertext: "+input_cipher.getText().toString());
                ans_key.setText("Key: "+input_key.getText().toString());
                ans_plaintext.setText(plainText);
                input_cipher.setText("");
                input_key.setText("");


        }

    }

}