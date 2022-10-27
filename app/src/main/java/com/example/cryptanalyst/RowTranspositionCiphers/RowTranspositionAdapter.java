package com.example.cryptanalyst.RowTranspositionCiphers;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.cryptanalyst.RailFenceCipher.RailFenceDecryptionFragment;
import com.example.cryptanalyst.RailFenceCipher.RailFenceEncryptionFragment;

public class RowTranspositionAdapter extends FragmentPagerAdapter {
    private Context context;
    private int totalTabs;

    public RowTranspositionAdapter(FragmentManager fm, Context context, int totalTabs) {
        super(fm);
        this.context = context;
        this.totalTabs = totalTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                RowTranspositionEncryptionFragment encryptionFragment = new RowTranspositionEncryptionFragment();
                return encryptionFragment;
            case 1:
                RowTranspositionDecryptionFragment decryptionFragment = new RowTranspositionDecryptionFragment();
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

