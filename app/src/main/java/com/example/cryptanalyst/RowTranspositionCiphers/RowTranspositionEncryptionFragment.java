package com.example.cryptanalyst.RowTranspositionCiphers;

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

public class RowTranspositionEncryptionFragment extends Fragment {

    String plaintext, key;
    EditText input_plaintext, input_key;
    Button encrypt, clear;
    LinearLayout ans, input_layout;
    TextView ans_plaintext, ans_key, ans_cipher, note;
    private static char sortedKey[];
    private static int sortedKeyPos[];

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
        note.setText("To adjust length, underscore(_) is used at the end of the plaintext. Don't repeat a letter in the key");

        input_layout = root.findViewById(R.id.input_layout);
        clear = root.findViewById(R.id.clear);

        encrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generateCipher();
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
        Pattern pattern = Pattern.compile("[a-z A-Z]+");
        plaintext = input_plaintext.getText().toString().toUpperCase().replaceAll("[^A-Z]", "");
        key = input_key.getText().toString().toUpperCase().replaceAll("[^A-Z]", "");
        sortedKeyPos = new int[key.length()];
        sortedKey = key.toCharArray();
        //Create an empty string
        String newstr = "";

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
            input_key.setError("Only alphabetic key is allowed!!!");
            input_key.requestFocus();

        } else {
            // Traverse the string and check for the repeated characters
            try {
                doEncryption(plaintext, key);
            } catch (Exception e) {
                Toast.makeText(getContext(), "Letter Repetition in the key is not allowed!!! " +e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    // To reorder data do the sorting on selected key
    public static void doProcessOnKey(String key)
    {
        // Find position of each character in selected key and arrange it on
        // alphabetical order
        int min, i, j;
        char orginalKey[] = key.toCharArray();
        char temp;

        // First Sort the array of selected key
        for (i = 0; i < key.length(); i++)
        {
            min = i;
            for (j = i; j < key.length(); j++)
            {
                if (sortedKey[min] > sortedKey[j])
                {
                    min = j;
                }
            }
            if (min != i)
            {
                temp = sortedKey[i];
                sortedKey[i] = sortedKey[min];
                sortedKey[min] = temp;
            }
        }
        // Fill the position of array according to alphabetical order
        for (i = 0; i < key.length(); i++)
        {
            for (j = 0; j < key.length(); j++)
            {
                if (orginalKey[i] == sortedKey[j])
                    sortedKeyPos[i] = j;
            }
        }
    }

    // to encrypt the targeted string
    public void doEncryption(String plainText, String key)
    {
        int i, j;

        doProcessOnKey(key);
        // Generate encrypted message by doing encryption using Transpotion
        // Cipher
        int row = plainText.length() / key.length();
        int extrabit = plainText.length() % key.length();
        int exrow = (extrabit == 0) ? 0 : 1;
        int rowtemp = -1, coltemp = -1;
        int totallen = (row + exrow) * key.length();
        char pmat[][] = new char[(row + exrow)][(key.length())];
        char encry[] = new char[totallen];
        int tempcnt = -1;
        row = 0;

            for (i = 0; i < totallen; i++)
            {
                coltemp++;
                if (i < plainText.length())
                {
                    if (coltemp == (key.length()))
                    {
                        row++;
                        coltemp = 0;
                    }
                    pmat[row][coltemp] = plainText.charAt(i);
                }
                else
                { // do the padding ...
                    pmat[row][coltemp] = '_';
                }
            }
            int len = -1, k;
            for (i = 0; i < key.length(); i++)
            {
                for (k = 0; k < key.length(); k++)
                {
                    if (i == sortedKeyPos[k])
                    {
                        break;
                    }
                }
                for (j = 0; j <= row; j++)
                {
                    len++;
                    encry[len] = pmat[j][k];
                }
            }
            String cipherText = new String(encry);

            ans.setVisibility(View.VISIBLE);
            input_layout.setVisibility(View.GONE);
            ans_plaintext.setText("Plaintext: " + input_plaintext.getText().toString());
            ans_key.setText("Key: " + input_key.getText().toString());
            ans_cipher.setText(cipherText);
            input_plaintext.setText("");
            input_key.setText("");

    }
}