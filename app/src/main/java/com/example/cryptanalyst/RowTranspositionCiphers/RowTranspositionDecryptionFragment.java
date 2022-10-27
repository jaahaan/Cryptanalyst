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

public class RowTranspositionDecryptionFragment extends Fragment {

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
        note.setText("To adjust length, underscore(_) is used at the end of the plaintext. Don't repeat a letter in the key");

        input_layout = root.findViewById(R.id.input_layout);
        clear = root.findViewById(R.id.clear);

        ans.setVisibility(View.GONE);
        decrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Pattern pattern = Pattern.compile("[a-z A-Z_]+");

                if (input_cipher.getText().toString().isEmpty()) {
                    input_cipher.setError("Empty");
                    input_cipher.requestFocus();
                } else if (!pattern.matcher(input_cipher.getText().toString()).matches()) {
                    input_cipher.setError("Only alphabetic text is allowed!!!");
                    input_cipher.requestFocus();
                } else if (input_key.getText().toString().isEmpty()) {
                    input_key.setError("Empty");
                    input_key.requestFocus();
                } else {
                    cipher = input_cipher.getText().toString().toUpperCase().replaceAll("[^A-Z_]", "");
                    key = input_key.getText().toString().toUpperCase().replaceAll("[^A-Z]", "");
                    sortedKeyPos = new int[key.length()];
                    sortedKey = key.toCharArray();
                    try {
                        doDecryption(cipher, key);
                    } catch (Exception e) {
                        Toast.makeText(getContext(), "Wrong Input!!", Toast.LENGTH_SHORT).show();
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

    // To reorder data do the sorting on selected key
    public void doProcessOnKey(String key)
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


    // to decrypt the targeted string
    public void doDecryption(String cipher, String selectedKey) {
        int min, i, j, k;
        char key[] = selectedKey.toCharArray();
        char encry[] = cipher.toCharArray();
        char temp;
        doProcessOnKey(selectedKey);
        // Now generating plain message
        int row = cipher.length() / selectedKey.length();
        char pmat[][] = new char[row][(selectedKey.length())];
        int tempcnt = -1;

            for (i = 0; i < selectedKey.length(); i++) {
                for (k = 0; k < selectedKey.length(); k++) {
                    if (i == sortedKeyPos[k]) {
                        break;
                    }
                }
                for (j = 0; j < row; j++) {
                    tempcnt++;
                    pmat[j][k] = encry[tempcnt];
                }
            }

            // store matrix character in to a single string
            char p1[] = new char[row * selectedKey.length()];
            k = 0;
            for (i = 0; i < row; i++) {
                for (j = 0; j < selectedKey.length(); j++) {
                    if (pmat[i][j] != '_') {
                        p1[k++] = pmat[i][j];
                    }
                }
            }
            p1[k++] = '\0';
            ans.setVisibility(View.VISIBLE);
            input_layout.setVisibility(View.GONE);
            ans_cipher.setText("Ciphertext: " + input_cipher.getText().toString());
            ans_key.setText("Key: " + input_key.getText().toString());
            ans_plaintext.setText(new String(p1));
            input_cipher.setText("");
            input_key.setText("");

    }

}