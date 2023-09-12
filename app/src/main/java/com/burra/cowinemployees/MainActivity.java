package com.burra.cowinemployees;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    LinearLayout device_unpaired_ll_bt;

    private static final String PREFS_NAME = "MyPrefs";
    private static final String PREF_MAC_ID = "mac_id";
    private Handler handler;
    private Runnable dataCheckRunnable;

    ImageView setting_img_kt;
    EditText editTextDatePicker_kt,name_box_kt, phone_box_kt;
     Calendar calendar;
    AlertDialog dialog;
    Button bmi_activity_kt;

    String name, age, gender, macid, phonenumber;
    private Spinner spinner_kt;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setting_img_kt = findViewById(R.id.setting_img);
        editTextDatePicker_kt = findViewById(R.id.editTextDatePicker);
        spinner_kt = findViewById(R.id.spinner);
        bmi_activity_kt = findViewById(R.id.bmi_activity);
        name_box_kt = findViewById(R.id.name_box);
        phone_box_kt = findViewById(R.id.phone_box);
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PREF_MAC_ID, "No Data");
        editor.apply();


        String[] items = {"Please Select Gender", "Male", "Female"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_kt.setAdapter(adapter);

        spinner_kt.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gender = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing here
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

        bmi_activity_kt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name = name_box_kt.getText().toString();
                age = editTextDatePicker_kt.getText().toString();
                phonenumber = phone_box_kt.getText().toString();

                if(name.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Please Enter Your Name", Toast.LENGTH_LONG).show();
                }else if(phonenumber.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Please Enter Your Phone Number", Toast.LENGTH_LONG).show();
                } else if(age.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Please Enter Your Age", Toast.LENGTH_LONG).show();
                }else if(gender.equalsIgnoreCase("Please Select Gender")){
                    Toast.makeText(getApplicationContext(), "Please Select Your Gender", Toast.LENGTH_LONG).show();
                }else if(macid.equalsIgnoreCase("No Data")){
                    Intent intent = new Intent(MainActivity.this, DeviceListActivity.class);
                    startActivity(intent);
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
        if (macid != "No Data") {
            dialog.dismiss();
        }
    }
}