package com.example.cryptanalyst.HillCipher;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.cryptanalyst.CaesarCipher.CaesarDecryptionFragment;
import com.example.cryptanalyst.CaesarCipher.CaesarEncryptionFragment;

public class HillCipherAdapter extends FragmentPagerAdapter {

    private Context context;
    private int totalTabs;

    public HillCipherAdapter(FragmentManager fm, Context context, int totalTabs) {
        super(fm);
        this.context=context;
        this.totalTabs=totalTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                HillCipherEncryptionFragment encryptionFragment = new HillCipherEncryptionFragment();
                return encryptionFragment;
            case 1:
                HillCipherDecryptionFragment decryptionFragment = new HillCipherDecryptionFragment();
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
