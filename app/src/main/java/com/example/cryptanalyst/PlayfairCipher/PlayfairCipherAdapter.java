package com.example.cryptanalyst.PlayfairCipher;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class PlayfairCipherAdapter extends FragmentPagerAdapter {

    private Context context;
    private int totalTabs;

    public PlayfairCipherAdapter(FragmentManager fm, Context context, int totalTabs) {
        super(fm);
        this.context = context;
        this.totalTabs = totalTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                PlayfairEncryptionFragment encryptionFragment = new PlayfairEncryptionFragment();
                return encryptionFragment;
            case 1:
                PlayfairDecryptionFragment decryptionFragment = new PlayfairDecryptionFragment();
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
