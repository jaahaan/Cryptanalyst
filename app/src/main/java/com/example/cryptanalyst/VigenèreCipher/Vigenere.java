package com.example.cryptanalyst.VigenèreCipher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.TextView;

import com.example.cryptanalyst.R;
import com.example.cryptanalyst.RowTranspositionCiphers.RowTranspositionAdapter;
import com.google.android.material.tabs.TabLayout;

public class Vigenere extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cipher);
        textView = findViewById(R.id.textView);
        textView.setText("Vigenere Cipher");
        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);

        tabLayout.addTab(tabLayout.newTab().setText("Encryption"));
        tabLayout.addTab(tabLayout.newTab().setText("Decryption"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        final VigenereAdapter adapter = new VigenereAdapter(getSupportFragmentManager(), this, tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

    }
}