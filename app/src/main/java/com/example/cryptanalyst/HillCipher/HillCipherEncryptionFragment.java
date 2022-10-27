package com.example.cryptanalyst.HillCipher;

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

import java.util.ArrayList;


public class HillCipherEncryptionFragment extends Fragment {

    String plaintext, key;
    EditText input_plaintext, input_key;
    Button encrypt, clear;
    LinearLayout ans, input_layout;
    TextView ans_plaintext, ans_key, ans_cipher;

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
        input_layout = root.findViewById(R.id.input_layout);
        clear = root.findViewById(R.id.clear);

        encrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                plaintext = input_plaintext.getText().toString().replaceAll("[^a-zA-Z]", "").toUpperCase();
                key = input_key.getText().toString().replaceAll("[^a-zA-Z]", "").toUpperCase();

                if (plaintext.isEmpty()) {
                    input_plaintext.setError("Empty");
                    input_plaintext.requestFocus();
                }
                // If phrase length is not an even number, add "Q" to make it even
                /*else if(plaintext.length() % 2 == 1) {
                    input_plaintext.setError("Cannot Form a rectangle matrix");
                    input_plaintext.requestFocus();
                }*/
                else if (key.isEmpty()) {
                    input_key.setError("Empty");
                    input_key.requestFocus();
                } else {
                    HillCipher(plaintext, key);
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

    // Following function generates the
// key matrix for the key string
    static void getKeyMatrix(String key, int keyMatrix[][])
    {
        int k = 0;
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                keyMatrix[i][j] = (key.charAt(k)) % 65;
                k++;
            }
        }

        /**double sq = Math.sqrt(key.length());
        if (sq != (long) sq) {
            input_key.setError("Cannot Form a square matrix");
            input_key.requestFocus();
        }
        int len = (int) sq;
        int[][] keyMatrix = new int[len][len];
        int k = 0;
        for (int i = 0; i < len; i++)
        {
            for (int j = 0; j < len; j++)
            {
                keyMatrix[i][j] = ((int) key.charAt(k)) - 97;
                k++;
            }
        }

        // checks whether the key matrix is valid (det=0)
        int det = keyMatrix[0][0] * keyMatrix[1][1] - keyMatrix[0][1] * keyMatrix[1][0];
        // If det=0, throw exception and terminate
        if(det == 0) {
            input_key.setError("Det equals to zero, invalid key matrix!");
            input_key.requestFocus();
        }

        // This method calculates the reverse key matrix
        int detmod26 = (keyMatrix[0][0] * keyMatrix[1][1] - keyMatrix[0][1] * keyMatrix[1][0]) % 26; // Calc det
        int factor;
        int[][] reverseMatrix = new int[2][2];
        // Find the factor for which is true that
        // factor*det = 1 mod 26
        for(factor=1; factor < 26; factor++)
        {
            if((detmod26 * factor) % 26 == 1)
            {
                break;
            }
        }
        // Calculate the reverse key matrix elements using the factor found
        reverseMatrix[0][0] = keyMatrix[1][1]           * factor % 26;
        reverseMatrix[0][1] = (26 - keyMatrix[0][1])    * factor % 26;
        reverseMatrix[1][0] = (26 - keyMatrix[1][0])    * factor % 26;
        reverseMatrix[1][1] = keyMatrix[0][0]           * factor % 26;

        // checks whether the key matrix is valid (det=0)
        int[][] product = new int[2][2];
        // Find the product matrix of key matrix times reverse key matrix
        product[0][0] = (keyMatrix[0][0]*reverseMatrix[0][0] + keyMatrix[0][1] * reverseMatrix[1][0]) % 26;
        product[0][1] = (keyMatrix[0][0]*reverseMatrix[0][1] + keyMatrix[0][1] * reverseMatrix[1][1]) % 26;
        product[1][0] = (keyMatrix[1][0]*reverseMatrix[0][0] + keyMatrix[1][1] * reverseMatrix[1][0]) % 26;
        product[1][1] = (keyMatrix[1][0]*reverseMatrix[0][1] + keyMatrix[1][1] * reverseMatrix[1][1]) % 26;
        // Check if a=1 and b=0 and c=0 and d=1
        // If not, throw exception and terminate
        if(product[0][0] != 1 || product[0][1] != 0 || product[1][0] != 0 || product[1][1] != 1) {
            input_key.setError("Invalid reverse matrix found!!");
            input_key.requestFocus();
        }*/
    }

    // Following function encrypts the message
    static void encrypt(int cipherMatrix[][],
                        int keyMatrix[][],
                        int messageVector[][])
    {
        int x, i, j;
        for (i = 0; i < 3; i++)
        {
            for (j = 0; j < 1; j++)
            {
                cipherMatrix[i][j] = 0;

                for (x = 0; x < 3; x++)
                {
                    cipherMatrix[i][j] +=
                            keyMatrix[i][x] * messageVector[x][j];
                }

                cipherMatrix[i][j] = cipherMatrix[i][j] % 26;
            }
        }
    }

    // Function to implement Hill Cipher
    void HillCipher(String message, String key)
    {
        // Get key matrix from the key string
        int [][]keyMatrix = new int[3][3];
        getKeyMatrix(key, keyMatrix);

        int [][]messageVector = new int[3][1];

        // Generate vector for the message
        for (int i = 0; i < 3; i++)
            messageVector[i][0] = (message.charAt(i)) % 65;

        int [][]cipherMatrix = new int[3][1];

        // Following function generates
        // the encrypted vector
        encrypt(cipherMatrix, keyMatrix, messageVector);

        String CipherText="";

        // Generate the encrypted text from
        // the encrypted vector
        for (int i = 0; i < 3; i++)
            CipherText += (char)(cipherMatrix[i][0] + 65);

        // Finally print the ciphertext
        ans.setVisibility(View.VISIBLE);
        input_layout.setVisibility(View.GONE);
        ans_plaintext.setText("Plaintext: " + input_plaintext.getText().toString());
        ans_key.setText("Key: " + input_key.getText().toString());
        ans_cipher.setText(CipherText);
        input_plaintext.setText("");
        input_key.setText("");
    }
}