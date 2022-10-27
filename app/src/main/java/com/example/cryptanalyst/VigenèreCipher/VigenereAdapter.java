package com.example.cryptanalyst.VigenèreCipher;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.cryptanalyst.RowTranspositionCiphers.RowTranspositionDecryptionFragment;
import com.example.cryptanalyst.RowTranspositionCiphers.RowTranspositionEncryptionFragment;

public class VigenereAdapter extends FragmentPagerAdapter {
    private Context context;
    private int totalTabs;

    public VigenereAdapter(FragmentManager fm, Context context, int totalTabs) {
        super(fm);
        this.context = context;
        this.totalTabs = totalTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                VigenereEncryptionFragment encryptionFragment = new VigenereEncryptionFragment();
                return encryptionFragment;
            case 1:
                VigenereDecryptionFragment decryptionFragment = new VigenereDecryptionFragment();
                return decryptionFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}

