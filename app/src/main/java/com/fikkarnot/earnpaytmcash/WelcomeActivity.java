package com.fikkarnot.earnpaytmcash;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

public class WelcomeActivity extends AppIntro {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        addSlide(AppIntroFragment.newInstance("Welcome here","Earn paytm cash by just watching video", R.drawable.business, Color.parseColor("#d45800")));
        addSlide(AppIntroFragment.newInstance("Earn money by watching Video","You can earn money by watching video ads.", R.drawable.computer, Color.parseColor("#ed9005")));
        addSlide(AppIntroFragment.newInstance("Redeem to your Paytm Wallet","You can redeem coins to paytm cash and will get rewarded to your paytm wallet within 7 Days.", R.drawable.paytm, Color.parseColor("#03ad00")));
        setFadeAnimation();

        sharedPreferences = getApplicationContext().getSharedPreferences("Mypref", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        if (sharedPreferences!=null){

            boolean checkShared = sharedPreferences.getBoolean("check",false);

            if (checkShared==true){

                Intent intent =  new Intent(this,MainActivity.class);
                startActivity(intent);
                finish();

            }
        }

    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        // Do something when users tap on Skip button.
        Intent intent =  new Intent(this,MainActivity.class);
        startActivity(intent);
        editor.putBoolean("check",false).commit();
        finish();
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        // Do something when users tap on Done button.
        Intent intent =  new Intent(this,MainActivity.class);
        startActivity(intent);
        editor.putBoolean("check",true).commit();
        finish();
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
        // Do something when the slide changes.
    }
}
