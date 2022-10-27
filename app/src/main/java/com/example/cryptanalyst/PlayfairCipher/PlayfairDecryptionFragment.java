package com.example.cryptanalyst.PlayfairCipher;

import android.graphics.Point;
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
import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Pattern;

public class PlayfairDecryptionFragment extends Fragment {

    //creates a matrix for Playfair cipher
    private String [][] table;
    String cipher,key;
    EditText input_cipher, input_key;
    TextInputLayout input_key_layout;
    Button decrypt, clear;
    LinearLayout ans, input_layout;
    TextView ans_plaintext, ans_key, ans_cipher, note;
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
        note.setText("For consecutive same letter X is added between them and for alone case Z is added at the end");
        input_layout = root.findViewById(R.id.input_layout);
        clear = root.findViewById(R.id.clear);

        ans.setVisibility(View.GONE);
        decrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Pattern pattern = Pattern.compile("[a-z A-Z]+");
                cipher = input_cipher.getText().toString().toUpperCase().replaceAll("[^A-Z]", "");
                key = input_key.getText().toString().toLowerCase().toUpperCase().replaceAll("[^A-Z]", "");

                if (input_cipher.getText().toString().isEmpty()) {
                    input_cipher.setError("Empty");
                    input_cipher.requestFocus();
                } else if (!pattern.matcher(input_cipher.getText().toString()).matches()) {
                    input_cipher.setError("Only alphabetic text is allowed!!!");
                    input_cipher.requestFocus();
                } else if (cipher.length()%2!=0) {
                    input_cipher.setError("Ciphertext length must be even!!!");
                    input_cipher.requestFocus();
                } else if (input_key.getText().toString().isEmpty()) {
                    input_key.setError("Empty");
                    input_key.requestFocus();
                } else if (!pattern.matcher(input_key.getText().toString()).matches()) {
                    input_key.setError("Only alphabetic Key is allowed!!!");
                    input_key.requestFocus();
                } else {

                    try {
                        PlayfairCipher( cipher, key);
                    } catch (Exception e){
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
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

    private void PlayfairCipher(String cipher, String key)
    {

        table = this.cipherTable(key);

        //decodes the message
        String dec = decode(cipher);

        //output the results to user
        ans.setVisibility(View.VISIBLE);
        input_layout.setVisibility(View.GONE);
        ans_cipher.setText("Ciphertext: "+input_cipher.getText().toString());
        ans_key.setText("Key: "+input_key.getText().toString());
        ans_plaintext.setText(dec);
        input_cipher.setText("");
        input_key.setText("");
        //this.keyTable(table);
    }

    //creates the cipher table based on some input string (already parsed)
    private String[][] cipherTable(String key)
    {
        //creates a matrix of 5*5
        String[][] playfairTable = new String[5][5];
        String keyString = key + "ABCDEFGHIKLMNOPQRSTUVWXYZ";
        //fill string array with empty string
        for(int i = 0; i < 5; i++)
            for(int j = 0; j < 5; j++)
                playfairTable[i][j] = "";
        for(int k = 0; k < keyString.length(); k++)
        {
            boolean repeat = false;
            boolean used = false;
            for(int i = 0; i < 5; i++)
            {
                for(int j = 0; j < 5; j++)
                {
                    if(playfairTable[i][j].equals("" + keyString.charAt(k)))
                    {
                        repeat = true;
                    }
                    else if(playfairTable[i][j].equals("") && !repeat && !used)
                    {
                        playfairTable[i][j] = "" + keyString.charAt(k);
                        used = true;
                    }
                }
            }
        }
        return playfairTable;
    }

    //-----------------------decryption logic---------------------
    // decodes the output given from the cipher and decode methods
    private String decode(String out)
    {
        String decoded = "";
        for(int i = 0; i < out.length() / 2; i++)
        {
            char a = out.charAt(2*i);
            char b = out.charAt(2*i+1);
            int r1 = (int) getPoint(a).x;
            int r2 = (int) getPoint(b).x;
            int c1 = (int) getPoint(a).y;
            int c2 = (int) getPoint(b).y;
            if(r1 == r2)
            {
                c1 = (c1 + 4) % 5;
                c2 = (c2 + 4) % 5;
            }
            else if(c1 == c2)
            {
                r1 = (r1 + 4) % 5;
                r2 = (r2 + 4) % 5;
            }
            else
            {
                //swapping logic
                int temp = c1;
                c1 = c2;
                c2 = temp;
            }
            decoded = decoded + table[r1][c1] + table[r2][c2];
        }
        //returns the decoded message
        return decoded;
    }

    // returns a point containing the row and column of the letter
    private Point getPoint(char c)
    {
        Point pt = new Point(0,0);
        for(int i = 0; i < 5; i++)
            for(int j = 0; j < 5; j++)
                if(c == table[i][j].charAt(0))
                    pt = new Point(i,j);
        return pt;
    }

    //function prints the key-table in matrix form for playfair cipher
    /**private void keyTable(String[][] printTable)
    {
        System.out.println("Playfair Cipher Key Matrix: ");
        System.out.println();
        //loop iterates for rows
        for(int i = 0; i < 5; i++)
        {
            //loop iterates for column
            for(int j = 0; j < 5; j++)
            {
                //prints the key-table in matrix form
                System.out.print(printTable[i][j]+" ");
            }
            System.out.println();
        }
        System.out.println();
    }*/
}