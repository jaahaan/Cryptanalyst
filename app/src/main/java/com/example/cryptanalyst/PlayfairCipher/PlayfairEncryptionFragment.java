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

public class PlayfairEncryptionFragment extends Fragment {


    String plainText, key;

    //length of digraph array
    private int length = 0;
    //creates a matrix for Playfair cipher
    private String [][] table;
    EditText input_plaintext, input_key;
    TextInputLayout input_key_layout;

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
        note.setText("For consecutive same letter X is added between them and for alone case Z is added at the end");
        input_layout = root.findViewById(R.id.input_layout);
        clear = root.findViewById(R.id.clear);

        ans.setVisibility(View.GONE);
        encrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                } else {
                    plainText = input_plaintext.getText().toString().toUpperCase().replaceAll("[^A-Z]", "");
                    key = input_key.getText().toString().toUpperCase().replaceAll("[^A-Z]", "");
                    try {
                        PlayfairCipher(plainText, key);
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

    //---------------encryption logic-----------------
    //encodes the digraph input with the cipher's specifications
    private String[] encodeDigraph(String di[])
    {
        String[] encipher = new String[length];
        for(int i = 0; i < length; i++)
        {
            char a = di[i].charAt(0);
            char b = di[i].charAt(1);
            int r1 = (int) getPoint(a).x;
            int r2 = (int) getPoint(b).x;
            int c1 = (int) getPoint(a).y;
            int c2 = (int) getPoint(b).y;
            //executes if the letters of digraph appear in the same row
            //in such case shift columns to right
            if(r1 == r2)
            {
                c1 = (c1 + 1) % 5;
                c2 = (c2 + 1) % 5;
            }
            //executes if the letters of digraph appear in the same column
            //in such case shift rows down
            else if(c1 == c2)
            {
                r1 = (r1 + 1) % 5;
                r2 = (r2 + 1) % 5;
            }
            //executes if the letters of digraph appear in the different row and different column
            //in such case swap the first column with the second column
            else
            {
                int temp = c1;
                c1 = c2;
                c2 = temp;
            }
            //performs the table look-up and puts those values into the encoded array
            encipher[i] = table[r1][c1] + "" + table[r2][c2];
        }
        return encipher;
    }
    private void PlayfairCipher(String plainText, String key)
    {

        table = this.cipherTable(key);

        //encodes and then decodes the encoded message
        String output = cipher(plainText);

        //output the results to user
        this.keyTable(table);
        this.printResults(output);
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
    //cipher: takes input (all upper-case), encodes it, and returns the output
    private String cipher(String in)
    {
        length = (int) in.length() / 2 + in.length() % 2;

        //insert x between double-letter digraphs & redefines "length"
        for(int i = 0; i < (length - 1); i++)
        {
            if(in.charAt(2 * i) == in.charAt(2 * i + 1))
            {
                in = new StringBuffer(in).insert(2 * i + 1, 'X').toString();
                length = (int) in.length() / 2 + in.length() % 2;
            }
        }

        //------------makes plaintext of even length--------------
        //creates an array of digraphs
        String[] digraph = new String[length];
        //loop iterates over the plaintext
        for(int j = 0; j < length ; j++)
        {
            //checks the plaintext is of even length or not
            if(j == (length - 1) && in.length() / 2 == (length - 1))
            //if not addends Z at the end of the plaintext
                in = in + "Z";
            digraph[j] = in.charAt(2 * j) +""+ in.charAt(2 * j + 1);
        }
        //encodes the digraphs and returns the output
        String out = "";
        String[] encDigraphs = new String[length];
        encDigraphs = encodeDigraph(digraph);
        for(int k = 0; k < length; k++)
            out = out + encDigraphs[k];
        return out;
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
    private void keyTable(String[][] printTable)
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
    }
    //method that prints all the results
    private void printResults(String encipher)
    {

        ans.setVisibility(View.VISIBLE);
        input_layout.setVisibility(View.GONE);
        ans_plaintext.setText("Plaintext: " + input_plaintext.getText().toString());
        ans_key.setText("Key: " + input_key.getText().toString());
        ans_cipher.setText(encipher);
        input_plaintext.setText("");
        input_key.setText("");

    }
}

