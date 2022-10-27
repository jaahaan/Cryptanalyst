package com.example.cryptanalyst;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;

import com.example.cryptanalyst.CaesarCipher.CaesarCipher;
import com.example.cryptanalyst.HillCipher.HillCipher;
import com.example.cryptanalyst.PlayfairCipher.PlayfairCipher;
import com.example.cryptanalyst.RailFenceCipher.RailFence;
import com.example.cryptanalyst.RowTranspositionCiphers.RowTransposition;
import com.example.cryptanalyst.VernamCipher.Vernam;
import com.example.cryptanalyst.VigenèreCipher.Vigenere;

public class MainActivity extends AppCompatActivity {

    private CardView caesar, playfair, railFence, rowTrans, vernam, vigenere, hill, mod, mi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        caesar = findViewById(R.id.caesar);
        playfair = findViewById(R.id.playfair);
        railFence = findViewById(R.id.rail);
        rowTrans = findViewById(R.id.rowTransposition);
        vernam = findViewById(R.id.vernam);
        vigenere = findViewById(R.id.vignere);
        //hill = findViewById(R.id.hillCipher);
        mod = findViewById(R.id.mod);
        mi = findViewById(R.id.MI);

        caesar.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), CaesarCipher.class)));
        playfair.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), PlayfairCipher.class)));
        railFence.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), RailFence.class)));
        rowTrans.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), RowTransposition.class)));
        vernam.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), Vernam.class)));
        vigenere.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), Vigenere.class)));
        //hill.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), HillCipher.class)));
        mod.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), ModuloActivity.class)));
        mi.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), MultiplicativeInverseActivity.class)));

    }
}