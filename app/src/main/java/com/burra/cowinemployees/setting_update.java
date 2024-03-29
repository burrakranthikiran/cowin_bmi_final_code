package com.burra.cowinemployees;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.burra.modelclass.BluetoothManager;

public class setting_update extends AppCompatActivity {
    private BluetoothManager bluetoothManager;
    String macid;
    CardView back_icon_ct;
    EditText name_box_ct, phone_box_ct, model_box_ct,weight_factor_box_ct, weight_correction_box_ct;
    SeekBar device_height_seekbar_ct, device_height_correction_seekbar_ct;
    TextView device_height_textbox_ct,device_height_correction_textbox_ct;
    Button update_device_setting_ct;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        macid = intent.getStringExtra("macid");
        setContentView(R.layout.activity_setting_update);
        back_icon_ct = findViewById(R.id.back_icon);
        name_box_ct = findViewById(R.id.name_box);
        phone_box_ct = findViewById(R.id.phone_box);
        model_box_ct = findViewById(R.id.model_box);
        weight_factor_box_ct = findViewById(R.id.weight_factor_box);
        weight_correction_box_ct = findViewById(R.id.weight_correction_box);
        device_height_seekbar_ct = findViewById(R.id.device_height_seekbar);
        device_height_correction_seekbar_ct = findViewById(R.id.device_height_correction_seekbar);
        device_height_textbox_ct = findViewById(R.id.device_height_textbox);
        device_height_correction_textbox_ct = findViewById(R.id.device_height_correction_textbox);
        update_device_setting_ct = findViewById(R.id.update_device_setting);
        bluetoothManager = new BluetoothManager(this);
        if (bluetoothManager.connectToDevice(macid)) {
            String command = "$51,1124,128,#\\r\\n";
            bluetoothManager.sendCommand(command);
            // Connection successful, enable the send button
            // You can also handle UI changes here
        } else {
            // Connection failed, handle the error
        }
        bluetoothManager.setOnDataReceivedListener(new BluetoothManager.OnDataReceivedListener() {
            @Override
            public void onDataReceived(final String data) {
                // Handle received data on the UI thread
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("eeeeeeeeeeeeeeeeeeeeeeeeeee",data);
                        String[] parts = data.split(",");
                        if (parts.length > 0 && parts[0].equals("$51")) {
                            String command = "$32,1124,1,#\\r\\n";
                            bluetoothManager.sendCommand(command);
                        }
                        if(parts.length > 0 && parts[0].equals("$32")){
                            name_box_ct.setText(parts[1]);
                            phone_box_ct.setText(parts[2]);
                            model_box_ct.setText(parts[3]);
                            weight_factor_box_ct.setText(parts[6]);
                            weight_correction_box_ct.setText(parts[7]);
                            device_height_seekbar_ct.setProgress(Integer.parseInt(parts[4]));
                            device_height_correction_seekbar_ct.setProgress(Integer.parseInt(parts[5]));
                            device_height_textbox_ct.setText(parts[4]+" "+"CM");
                            device_height_correction_textbox_ct.setText(parts[5]+" "+"CM");
                        }

                    }
                });
            }
        });

        device_height_seekbar_ct.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                device_height_textbox_ct.setText(i+" "+"CM");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        device_height_correction_seekbar_ct.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                device_height_correction_textbox_ct.setText(i+" "+"CM");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        back_icon_ct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bluetoothManager.disconnect();
                finish();
            }
        });
        update_device_setting_ct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String[] device_height_textbox_ct_value = device_height_textbox_ct.getText().toString().split(" ");
                String[] device_height_correction_textbox_ct_value = device_height_correction_textbox_ct.getText().toString().split(" ");
                String command = "$31,"+name_box_ct.getText().toString()+","+phone_box_ct.getText().toString()+","+model_box_ct.getText().toString()+","+device_height_textbox_ct_value[0]+","+device_height_correction_textbox_ct_value[0]+","+weight_factor_box_ct.getText().toString()+","+weight_correction_box_ct.getText().toString()+",0,#\\r\\n";
                Log.e("aaaaaaaaaaaaaaaa",command);
                bluetoothManager.sendCommand(command);
                bluetoothManager.disconnect();
                finish();
            }
        });
    }
    @Override
    public void onBackPressed() {
        bluetoothManager.disconnect();
        finish();
    }
}