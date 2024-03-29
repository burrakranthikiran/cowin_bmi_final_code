package com.burra.cowinemployees;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.burra.modelclass.BluetoothManager;

public class BluetoothActivity extends AppCompatActivity {
    String name, age, gender, macid, phonenumber, bmi, height,weight, spo2,heart_rate,blood_pressure;

    private BluetoothManager bluetoothManager;

    TextView bmi_value_bt, weight_value_bt, height_value_bt, spo2_bt, heart_rate_bt;
    LinearLayout text_data_bt, device_status_bt, device_status_soon_bt;

    EditText sys_value_ct, dis_value_ct;


    Button calculate_activity_ct, device_status_cnt_admin_ct, device_status_soon_cnt_admin_ct,device_status_soon_skip_ct;

    ScrollView bmi_scr_ct;

    int deviceSubStatus;

    SeekBar spo2_seek_bar_kt, heart_rate_seek_bar_kt;

    @SuppressLint({"MissingPermission", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        age = intent.getStringExtra("age");
        gender = intent.getStringExtra("gender");
        phonenumber = intent.getStringExtra("phonenumber");
        Log.e("55555555555555",age);
        Log.e("55555555555555",gender);
        macid = intent.getStringExtra("macid");
        setContentView(R.layout.activity_bluetooth);
        bmi_value_bt = findViewById(R.id.bmi_value);
        weight_value_bt = findViewById(R.id.weight_value);
        height_value_bt = findViewById(R.id.height_value);
        calculate_activity_ct = findViewById(R.id.calculate_activity);
        bmi_scr_ct = findViewById(R.id.bmi_scr);
        text_data_bt = findViewById(R.id.text_data);
        spo2_bt = findViewById(R.id.spo2_et);
        heart_rate_bt = findViewById(R.id.heart_rate_et);
        device_status_bt = findViewById(R.id.device_status);
        device_status_soon_bt = findViewById(R.id.device_status_soon);
        device_status_cnt_admin_ct = findViewById(R.id.device_status_cnt_admin);
        device_status_soon_cnt_admin_ct = findViewById(R.id.device_status_soon_cnt_admin);
        device_status_soon_skip_ct = findViewById(R.id.device_status_soon_skip);
        spo2_seek_bar_kt = findViewById(R.id.spo2_seek_bar);
        heart_rate_seek_bar_kt = findViewById(R.id.heart_rate_seek_bar);
        sys_value_ct = findViewById(R.id.sys_value);
        dis_value_ct = findViewById(R.id.dis_value);
        bluetoothManager = new BluetoothManager(this);
        device_status_bt.setVisibility(View.GONE);
        device_status_soon_bt.setVisibility(View.GONE);
        text_data_bt.setVisibility(View.VISIBLE);
        bmi_scr_ct.setVisibility(View.GONE);
        calculate_activity_ct.setVisibility(View.GONE);

        if (bluetoothManager.connectToDevice(macid)) {
            String command = "$51,1124,128,#\\r\\n";
            bluetoothManager.sendCommand(command);
            // Connection successful, enable the send button
            // You can also handle UI changes here
        } else {
            // Connection failed, handle the error
        }

        spo2_seek_bar_kt.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Update the TextView with the current progress value
//                textView.setText("Progress: " + progress);
                Log.e("1111111111111111111111", String.valueOf(progress));
                spo2_bt.setText(progress+" "+"%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Called when the user starts dragging the thumb
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Called when the user releases the thumb
            }
        });

        heart_rate_seek_bar_kt.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Update the TextView with the current progress value
//                textView.setText("Progress: " + progress);
                Log.e("1111111111111111111111", String.valueOf(progress));
                heart_rate_bt.setText(progress+" "+"bpm");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Called when the user starts dragging the thumb
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Called when the user releases the thumb
            }
        });



        device_status_cnt_admin_ct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://api.whatsapp.com/send?phone=919398772387&text=Hello Team")));
            }
        });
        device_status_soon_cnt_admin_ct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://api.whatsapp.com/send?phone=917702597518&text=Hello Team")));
            }
        });

        device_status_soon_skip_ct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                device_status_bt.setVisibility(View.GONE);
                device_status_soon_bt.setVisibility(View.GONE);
                text_data_bt.setVisibility(View.VISIBLE);
            }
        });





        bluetoothManager.setOnDataReceivedListener(new BluetoothManager.OnDataReceivedListener() {
            @Override
            public void onDataReceived(final String data) {
                // Handle received data on the UI thread
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("Kranthi",data);

                        String[] parts = data.split(",");
                        if (parts.length > 0 && parts[0].equals("$42")) {
                            bmi = parts[6];
                            height = parts[4];
                            weight = parts[5];
                            bmi_value_bt.setText(parts[6]+"Cal/per day");
                            height_value_bt.setText(parts[4]+"Cm");
                            weight_value_bt.setText(parts[5]+"KG");
//                            if(deviceSubStatus == 0){
//                                device_status_bt.setVisibility(View.VISIBLE);
//                                device_status_soon_bt.setVisibility(View.GONE);
//                                text_data_bt.setVisibility(View.GONE);
//                                bmi_scr_ct.setVisibility(View.GONE);
//                                calculate_activity_ct.setVisibility(View.GONE);
//                            }else{
                                device_status_bt.setVisibility(View.GONE);
                                device_status_soon_bt.setVisibility(View.GONE);
                                text_data_bt.setVisibility(View.GONE);
                                bmi_scr_ct.setVisibility(View.VISIBLE);
                                calculate_activity_ct.setVisibility(View.VISIBLE);
//                            }
                        }

                    }
                });
            }
        });


        calculate_activity_ct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BluetoothActivity.this, BMISummaryScreen.class);
                spo2 = spo2_bt.getText().toString();

                heart_rate = heart_rate_bt.getText().toString();
                String dis_value = dis_value_ct.getText().toString();
                String sys_value = sys_value_ct.getText().toString();
                if(dis_value.isEmpty() && sys_value.isEmpty()){
                    blood_pressure = 0+"/"+0+" "+"mmHg";
                }else if(dis_value.isEmpty()){
                    blood_pressure = 0+"/"+0+" "+"mmHg";
                }else if(sys_value.isEmpty()){
                    blood_pressure = 0+"/"+0+" "+"mmHg";
                }else{
                    blood_pressure = sys_value+"/"+dis_value+" "+"mmHg";
                }



                if(spo2.isEmpty()){
                    spo2 = "0 %";
                }else{
                    String[] spo2_parts = spo2.split(":");
                    spo2 = spo2_parts[0];
                }
                if(heart_rate.isEmpty()){
                    heart_rate = "0 bpm";
                }else{
                    String[] heart_rate_parts = heart_rate.split(":");
                    heart_rate = heart_rate_parts[0];
                }

                intent.putExtra("name", name);
                intent.putExtra("age", age);
                intent.putExtra("phonenumber", phonenumber);
                intent.putExtra("gender", gender);
                intent.putExtra("macid", macid);
                intent.putExtra("bmi", bmi);
                intent.putExtra("height", height);
                intent.putExtra("weight", weight);
                intent.putExtra("spo2", spo2);
                intent.putExtra("blood_pressure", blood_pressure);
                intent.putExtra("heart_rate", heart_rate);
                bluetoothManager.disconnect();
                startActivity(intent);
            }
        });
    }



    @Override
    public void onBackPressed() {
        // Do nothing or perform some custom action
        // To prevent the default back behavior, don't call super.onBackPressed()
    }

}
