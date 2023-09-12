package com.burra.cowinemployees;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class SettingActivity extends AppCompatActivity {

    ImageView back_icon_kt;
    LinearLayout linearLayout2_kt,linearLayout3_kt,linearLayout4_kt;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        back_icon_kt = findViewById(R.id.back_icon);
        linearLayout2_kt = findViewById(R.id.linearLayout2_UI);
        linearLayout3_kt = findViewById(R.id.linearLayout3_UI);
        linearLayout4_kt = findViewById(R.id.linearLayout4_UI);


        linearLayout4_kt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingActivity.this, UserRecords.class);
                startActivity(intent);

            }
        });



        linearLayout2_kt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this, DeviceListActivity.class);
                startActivity(intent);
            }
        });

        back_icon_kt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}