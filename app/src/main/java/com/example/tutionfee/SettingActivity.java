package com.example.tutionfee;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class SettingActivity extends AppCompatActivity {
Switch change;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
    change=findViewById(R.id.change);

    change.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if(change.isChecked()){
                setAppLanguage("fr");
            }
            else{
                setAppLanguage("en");
            }
        }
    });
    }
    private void setAppLanguage(String languageCode){
        Locale locale=new Locale(languageCode);
        Configuration configuration=getResources().getConfiguration();
        configuration.setLocale(locale);
        getResources().updateConfiguration(configuration,getResources().getDisplayMetrics());
        recreate();
    }
}