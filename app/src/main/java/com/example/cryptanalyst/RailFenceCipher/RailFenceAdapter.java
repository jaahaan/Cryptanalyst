package com.example.cryptanalyst.RailFenceCipher;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class RailFenceAdapter extends FragmentPagerAdapter {
    private Context context;
    private int totalTabs;

    public RailFenceAdapter(FragmentManager fm, Context context, int totalTabs) {
        super(fm);
        this.context = context;
        this.totalTabs = totalTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                RailFenceEncryptionFragment encryptionFragment = new RailFenceEncryptionFragment();
                return encryptionFragment;
            case 1:
                RailFenceDecryptionFragment decryptionFragment = new RailFenceDecryptionFragment();
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

