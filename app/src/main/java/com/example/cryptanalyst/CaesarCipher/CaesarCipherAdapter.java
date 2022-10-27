package com.example.cryptanalyst.CaesarCipher;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class CaesarCipherAdapter extends FragmentPagerAdapter {

    private Context context;
    private int totalTabs;

    public CaesarCipherAdapter(FragmentManager fm, Context context, int totalTabs) {
        super(fm);
        this.context=context;
        this.totalTabs=totalTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                CaesarEncryptionFragment encryptionFragment = new CaesarEncryptionFragment();
                return encryptionFragment;
            case 1:
                CaesarDecryptionFragment decryptionFragment = new CaesarDecryptionFragment();
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
