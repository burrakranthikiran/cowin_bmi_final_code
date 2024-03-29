package com.burra.cowinemployees;


import static android.Manifest.permission.BLUETOOTH_CONNECT;
import static android.Manifest.permission.BLUETOOTH_SCAN;
import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    LinearLayout device_unpaired_ll_bt;

    private static final String PREFS_NAME = "MyPrefs";
    private static final String PREF_MAC_ID = "mac_id";
    private Handler handler;
    private Runnable dataCheckRunnable;

    ImageView setting_img_kt, bt_status_kt;
    EditText editTextDatePicker_kt,name_box_kt, phone_box_kt, age_box_kt;
    Calendar calendar;
    AlertDialog dialog;
    Button bmi_activity_kt;

    CardView bc_card_icon_ct, card_view_update_bt;

    String name, age, gender = "No Data", macid, phonenumber;
    private static final int REQUEST_BLUETOOTH_PERMISSION = 1;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 200;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setting_img_kt = findViewById(R.id.setting_img);
        bt_status_kt = findViewById(R.id.bt_status);
        editTextDatePicker_kt = findViewById(R.id.editTextDatePicker);
        age_box_kt = findViewById(R.id.age_box);
        bmi_activity_kt = findViewById(R.id.bmi_activity);
        name_box_kt = findViewById(R.id.name_box);
        phone_box_kt = findViewById(R.id.phone_box);
        bc_card_icon_ct = findViewById(R.id.bc_card_icon);
        card_view_update_bt = findViewById(R.id.card_view_update);
        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        RadioButton radioButtonOption1 = findViewById(R.id.radioButtonOption1);
        RadioButton radioButtonOption2 = findViewById(R.id.radioButtonOption2);
        checkAndRequestBluetoothPermission();

        
        
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // Handle the selection of the Radio Buttons here
                if (checkedId == R.id.radioButtonOption1) {
                    gender = "Male";
                } else if (checkedId == R.id.radioButtonOption2) {
                    gender = "Female";
                }
            }
        });





        final DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateEditText();
            }
        };



        editTextDatePicker_kt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }

            private void showDatePickerDialog() {
                if (calendar == null) {
                    calendar = Calendar.getInstance();
                }
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        MainActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                calendar.set(Calendar.YEAR, year);
                                calendar.set(Calendar.MONTH, monthOfYear);
                                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                                updateEditText();
                            }
                        },
                        year,
                        month,
                        dayOfMonth
                );
                datePickerDialog.show();
            }
        });



        setting_img_kt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(intent);
            }
        });

        bt_status_kt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DeviceListActivity.class);
                startActivity(intent);
            }
        });

        bmi_activity_kt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name = name_box_kt.getText().toString();

                age = age_box_kt.getText().toString();
                phonenumber = phone_box_kt.getText().toString();

                if(name.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Please Enter Your Name", Toast.LENGTH_LONG).show();
                }else if(phonenumber.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Please Enter Your Phone Number", Toast.LENGTH_LONG).show();
                } else if(age.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Please Enter Your Age", Toast.LENGTH_LONG).show();
                }else if(gender.equalsIgnoreCase("No Data")){
                    Toast.makeText(getApplicationContext(), "Please Select Your Gender", Toast.LENGTH_LONG).show();
                }else if(macid.equalsIgnoreCase("No Data")){
                    Toast.makeText(getApplicationContext(), "Please pair Device First", Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent(MainActivity.this,BluetoothActivity.class);
                    intent.putExtra("name", name);
                    intent.putExtra("age", age);
                    intent.putExtra("phonenumber", phonenumber);
                    intent.putExtra("gender", gender);
                    intent.putExtra("macid", macid);
                    startActivity(intent);
                }

            }
        });

        card_view_update_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(macid.equalsIgnoreCase("No Data")){
                    Toast.makeText(getApplicationContext(), "Please pair Device First", Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent(MainActivity.this,setting_update.class);
                    intent.putExtra("name", name);
                    intent.putExtra("age", age);
                    intent.putExtra("phonenumber", phonenumber);
                    intent.putExtra("gender", gender);
                    intent.putExtra("macid", macid);
                    startActivity(intent);
                }
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View customView = getLayoutInflater().inflate(R.layout.custom_dialog_layout, null);
        builder.setView(customView);
        dialog = builder.create();
        LinearLayout device_unpaired_ll_kt = customView.findViewById(R.id.device_unpaired_ll);
        device_unpaired_ll_kt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DeviceListActivity.class);
                startActivity(intent);
            }
        });
//        dialog.show();
        handler = new Handler(Looper.getMainLooper());
        dataCheckRunnable = new Runnable() {
            @Override
            public void run() {
                readAndDisplaySharedPreferencesData();
                handler.postDelayed(this, 1000); // Repeat every 1 second (adjust as needed)
            }
        };

    }

    private void checkAndRequestBluetoothPermission() {
        int locationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        int permissionStorage = ContextCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE);
        int phonePermission = ContextCompat.checkSelfPermission(this,READ_PHONE_STATE);
        int bluetooth_permission = ContextCompat.checkSelfPermission(this,BLUETOOTH_CONNECT);
        int bluetooth_permission_2 = ContextCompat.checkSelfPermission(this,BLUETOOTH_SCAN);

        List<String> listPermissionsNeeded = new ArrayList<>();
        if (locationPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (permissionStorage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if(phonePermission != PackageManager.PERMISSION_GRANTED){
            listPermissionsNeeded.add(READ_PHONE_STATE);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if(bluetooth_permission_2 != PackageManager.PERMISSION_GRANTED){
                listPermissionsNeeded.add(BLUETOOTH_SCAN);
            }
            if(bluetooth_permission != PackageManager.PERMISSION_GRANTED){
                listPermissionsNeeded.add(BLUETOOTH_CONNECT);
            }

        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),REQUEST_ID_MULTIPLE_PERMISSIONS);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            // For Android 12 and above, use BLUETOOTH_CONNECT permission
            if (ContextCompat.checkSelfPermission(this, BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{
                        BLUETOOTH_CONNECT
                }, REQUEST_BLUETOOTH_PERMISSION);
            } else {

            }
        } else {
            // For Android 11 and below, use BLUETOOTH permission
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.BLUETOOTH
                }, REQUEST_BLUETOOTH_PERMISSION);
            } else {

            }
        }
    }

    private void updateEditText() {
        String myFormat = "MM/dd/yyyy"; // Change as needed
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        editTextDatePicker_kt.setText(sdf.format(calendar.getTime()));
    }

    @Override
    protected void onResume() {
        super.onResume();
        handler.post(dataCheckRunnable); // Start the background data check
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(dataCheckRunnable); // Stop the background data check
    }
    private void readAndDisplaySharedPreferencesData() {
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
            macid = preferences.getString(PREF_MAC_ID, null);
        if (macid != null) {
            Log.e("11111111111111111111",macid);
            if (macid.equalsIgnoreCase("No Data")) {
                bt_status_kt.setImageResource(R.drawable.bt_disct);
                int redColor = ContextCompat.getColor(this, R.color.red_color_bc);
                bc_card_icon_ct.setCardBackgroundColor(redColor);
                card_view_update_bt.setVisibility(View.VISIBLE);
            } else {
                int redColor = ContextCompat.getColor(this, R.color.green_color_bc);
                bc_card_icon_ct.setCardBackgroundColor(redColor);
                bt_status_kt.setImageResource(R.drawable.bt_ct);
                card_view_update_bt.setVisibility(View.VISIBLE);
            }
        } else {
            Log.e("11111111111111111111","no_null");
            bt_status_kt.setImageResource(R.drawable.bt_disct);
            int redColor = ContextCompat.getColor(this, R.color.red_color);
            bc_card_icon_ct.setCardBackgroundColor(redColor);
            card_view_update_bt.setVisibility(View.GONE);
            // Handle the case where macid is null
            // You can log an error or take appropriate action here
        }


    }
    @Override
    public void onBackPressed() {
//        // Do nothing or perform some custom action
//        // To prevent the default back behavior, don't call super.onBackPressed()
//        Intent intent = new Intent(BMISummaryScreen.this, MainActivity.class);
//        startActivity(intent);
    }
}