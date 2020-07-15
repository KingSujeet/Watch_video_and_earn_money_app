package com.fikkarnot.earnpaytmcash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RedeemActivity extends AppCompatActivity {

    // variables

    EditText editTextt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_redeem);

        // Initializing

        TextView score = findViewById(R.id.textView2);

        editTextt = findViewById(R.id.score_edt);


        // getting Total score from ShredPreference storage
        final SharedPreferences preferences = this.getSharedPreferences("myScore",getApplicationContext().MODE_PRIVATE);
        final int  coinsScore = preferences.getInt("key",0);
        score.setText(coinsScore +"");

        // this will close current activity
        findViewById(R.id.back_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });


        // This is the rule for Withdrawing money
        findViewById(R.id.Withdraw).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (editTextt.getText().toString().equals("") && (coinsScore !=250 || coinsScore !=500)  ){

                    Toast.makeText(getApplicationContext(),"You dont have enough balance to withdraw",Toast.LENGTH_LONG).show();
                }
                if (coinsScore == 250 || coinsScore == 500 && (editTextt.getText().toString().equals("250") || editTextt.getText().toString().equals("500")) ){

                    Toast.makeText(getApplicationContext(),"We have Received your request and you will get your reward within 7 bussiness days",Toast.LENGTH_LONG).show();
                    editTextt.setText("");
                }
                if (!editTextt.getText().toString().equals("250") || !editTextt.getText().toString().equals("500")){

                    Toast.makeText(getApplicationContext(),"You dont have enough balance to withdraw",Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}
