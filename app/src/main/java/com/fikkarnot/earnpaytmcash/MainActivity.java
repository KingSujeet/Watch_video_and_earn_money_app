package com.fikkarnot.earnpaytmcash;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends Activity implements RewardedVideoAdListener {

    // variables or instances

    private RewardedVideoAd mAd;
    private TextView score;
    private int coinsScore = 0;
    Calendar calendar;
    int year;
    int month;
    int day;
    String todaystring;
    SharedPreferences timepref;
    boolean currentday;
    Button btn_daily;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);


        // initializing instances or connecting instances to activity_main.xml Views.
        score = findViewById(R.id.earnedCoins);
        btn_daily = findViewById(R.id.dailyBonus);
        btn_daily.setEnabled(false);


        // fetching or Retrieving Total score from sharedpreference(SharedPreference is the type of storage)
        final SharedPreferences preferences = this.getSharedPreferences("myScore",Context.MODE_PRIVATE);
        coinsScore = preferences.getInt("key",0);
        score.setText("Earned Coins : " + coinsScore);

        // Initializing admob reward video ad
        MobileAds.initialize(getApplicationContext(),"ca-app-pub-3940256099942544/5224354917"); // Enter your own Admob rewar ad Id

        mAd = MobileAds.getRewardedVideoAdInstance(this);

        // adding listener
        mAd.setRewardedVideoAdListener(this);

        // this method will load video in background
        loadRewardVideoAd();

        // initializing variables for daily bonus
        calendar= Calendar.getInstance();
        year=calendar.get(Calendar.YEAR);
        month=calendar.get(Calendar.MONTH);
        day=calendar.get(Calendar.DAY_OF_MONTH);


        todaystring= year+ "" + month + "" + day + "";
        Context context = getApplicationContext();
        timepref=context.getSharedPreferences("REWARD",0);
        currentday=timepref.getBoolean(todaystring,false);


        // it will check the todays date and check if the user got daily bonus or not
        if (!currentday && isZoneAutomatic(getApplicationContext()) && isTimeAutomatic(getApplicationContext())) {
            btn_daily.setEnabled(true);
        }
        else {
            Toast.makeText(getApplicationContext(), "Your daily reward is over!", Toast.LENGTH_SHORT).show();

        }


            // button for daily Bonus
            btn_daily.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!currentday && isZoneAutomatic(getApplicationContext()) && isTimeAutomatic(getApplicationContext())) {

                        Toast.makeText(getApplicationContext(), "Daily reward granted!", Toast.LENGTH_SHORT).show();
                        // Do your stuff here
                        coinsScore = coinsScore + 10;
                        SharedPreferences preferences1 = getApplicationContext().getSharedPreferences("myScore",Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences1.edit();
                        editor.putInt("key",coinsScore);
                        editor.commit();
                        score.setText("Earned Coins : " + coinsScore);

                        // saving the date
                        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("SAVING", Context.MODE_PRIVATE);
                        SharedPreferences.Editor edt = sharedPreferences.edit();
                        edt.putInt("mypoints", 10);
                        edt.apply();
                        Toast.makeText(getApplicationContext(), "You have earned daily bonus : 10", Toast.LENGTH_SHORT).show();
                        SharedPreferences.Editor timedaily = timepref.edit();
                        timedaily.putBoolean(todaystring, true);
                        timedaily.apply();
                        btn_daily.setEnabled(false);

                    }


                }
            });

        // button for sharing app with your for friends
        findViewById(R.id.shareApp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                try{

                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT,"Earn paytm cash by watching video");
                    String shareMessage = "\n Earn 50 Rs. free Paytm cash\n\n";
                    shareMessage = shareMessage + "https://play.google.com/store/apps/EarnPaytmCash";
                    shareIntent.putExtra(Intent.EXTRA_TEXT,shareMessage);
                    startActivity(Intent.createChooser(shareIntent,"choose one"));

                }catch (Exception e){

                    Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });





    }
    // this is for daily bonus
    public static boolean isTimeAutomatic(Context c) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return Settings.Global.getInt(c.getContentResolver(), Settings.Global.AUTO_TIME, 0) == 1;
        } else {
            return android.provider.Settings.System.getInt(c.getContentResolver(), android.provider.Settings.System.AUTO_TIME, 0) == 1;
        }
    }

// this is for daily bonus
    public static boolean isZoneAutomatic(Context c) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return Settings.Global.getInt(c.getContentResolver(), Settings.Global.AUTO_TIME_ZONE, 0) == 1;
        } else {
            return android.provider.Settings.System.getInt(c.getContentResolver(), android.provider.Settings.System.AUTO_TIME, 0) == 1;
        }
    }

    // this method will load ad in background

    private void loadRewardVideoAd(){

        if (!mAd.isLoaded()){

            mAd.loadAd("ca-app-pub-3940256099942544/5224354917",new AdRequest.Builder().build());
        }
    }

    // this method will start video
    public void startVideoAd(View view) {

        if (mAd.isLoaded()){

            mAd.show();
        }
    }

    @Override
    public void onRewardedVideoAdLoaded() {

    }

    @Override
    public void onRewardedVideoAdOpened() {

    }

    @Override
    public void onRewardedVideoStarted() {


    }

    @Override
    public void onRewardedVideoAdClosed() {
       loadRewardVideoAd();
    }

    // this method will do what action perform after users get rewarded
    @Override
    public void onRewarded(RewardItem rewardItem) {

        coinsScore = coinsScore + rewardItem.getAmount();

       SharedPreferences preferences = this.getSharedPreferences("myScore",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("key",coinsScore);
        editor.commit();
        score.setText("Earned Coins : " + coinsScore);
        Toast.makeText(getApplicationContext(),"you have earned 10 coins",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onRewardedVideoAdLeftApplication() {

    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {

        Toast.makeText(getApplicationContext(),"Please try again",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onRewardedVideoCompleted() {

    }

    @Override
    protected void onPause() {

        mAd.pause(this);
        super.onPause();
    }

    @Override
    protected void onResume() {

        mAd.resume(this);
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        mAd.destroy(this);
        super.onDestroy();
    }

    // it will go to Redeem Activity
    public void onCLick(View view) {


        Intent intent = new Intent(getApplicationContext(),RedeemActivity.class);
        startActivity(intent);
    }
}
