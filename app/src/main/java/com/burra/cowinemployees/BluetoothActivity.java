package com.burra.cowinemployees;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class BluetoothActivity extends AppCompatActivity {
    String name, age, gender, macid, phonenumber, bmi, height,weight, spo2,heart_rate,blood_pressure;
    private BluetoothAdapter bluetoothAdapter;
    private BluetoothDevice targetDevice;
    private BluetoothSocket bluetoothSocket;
    private OutputStream outputStream;
    private InputStream inputStream;
    private final UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    TextView bmi_value_bt, weight_value_bt, height_value_bt;
    LinearLayout text_data_bt, device_status_bt, device_status_soon_bt;

    EditText spo2_bt, heart_rate_bt, blood_pressure_bt;

    Button calculate_activity_ct, device_status_cnt_admin_ct, device_status_soon_cnt_admin_ct,device_status_soon_skip_ct;

    ScrollView bmi_scr_ct;

    int deviceSubStatus;

    @SuppressLint({"MissingPermission", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        age = intent.getStringExtra("age");
        gender = intent.getStringExtra("gender");
        phonenumber = intent.getStringExtra("phonenumber");
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
        blood_pressure_bt = findViewById(R.id.blood_pressure_et);
        device_status_bt = findViewById(R.id.device_status);
        device_status_soon_bt = findViewById(R.id.device_status_soon);
        device_status_cnt_admin_ct = findViewById(R.id.device_status_cnt_admin);
        device_status_soon_cnt_admin_ct = findViewById(R.id.device_status_soon_cnt_admin);
        device_status_soon_skip_ct = findViewById(R.id.device_status_soon_skip);


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





        /**
         * Server SubDescription Status
         */

        try {
            JSONObject jsonBody = new JSONObject();

            jsonBody.put("macid", macid);


            // Define the URL of the API endpoint
            String url = "https://cowinserver.cowinbmi.io/api/bmi_status";

            // Create a JsonObjectRequest with the POST method and JSON body
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.POST,
                    url,
                    jsonBody,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                // Assuming 'response' is a String containing your JSON response
                                JSONObject jsonResponse = new JSONObject(String.valueOf(response));

                                // Extract the device_sub_status value
                                deviceSubStatus = jsonResponse.getInt("device_sub_status");
                                Log.d("9999999999999999999", String.valueOf(deviceSubStatus));

                                if(deviceSubStatus == 0){
                                    device_status_bt.setVisibility(View.VISIBLE);
                                    device_status_soon_bt.setVisibility(View.GONE);
                                    text_data_bt.setVisibility(View.GONE);
                                    bmi_scr_ct.setVisibility(View.GONE);
                                    calculate_activity_ct.setVisibility(View.GONE);
                                }else if (deviceSubStatus >= 1 && deviceSubStatus <= 25){
                                    device_status_bt.setVisibility(View.GONE);
                                    device_status_soon_bt.setVisibility(View.VISIBLE);
                                    text_data_bt.setVisibility(View.GONE);
                                    bmi_scr_ct.setVisibility(View.GONE);
                                    calculate_activity_ct.setVisibility(View.GONE);
                                } else{
                                    device_status_bt.setVisibility(View.GONE);
                                    device_status_soon_bt.setVisibility(View.GONE);
                                    text_data_bt.setVisibility(View.VISIBLE);
                                    bmi_scr_ct.setVisibility(View.VISIBLE);
                                    calculate_activity_ct.setVisibility(View.VISIBLE);
                                }


                                // Now you can use the 'deviceSubStatus' variable as needed
                                Log.d("Device Sub Status", String.valueOf(deviceSubStatus));

                            } catch (JSONException e) {
                                e.printStackTrace();
                                // Handle any JSON parsing errors here
                            }
                        }
                    },
                    new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // Handle any errors that occur
                            Log.e("Error", error.toString());
                        }
                    }
            );

            // Add the request to the Volley request queue
            RequestQueue requestQueue = Volley.newRequestQueue(BluetoothActivity.this);
            requestQueue.add(jsonObjectRequest);

        } catch (JSONException e) {
            e.printStackTrace();
        }


        String commandsText = "$51,1124,128,#\\r\\n";
        String[] commands = commandsText.split("\n");
        connectToDevice(commands);



        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            // Device doesn't support Bluetooth
            return;
        }

        // Check Bluetooth state and prompt user to enable if necessary
        if (!bluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, 1);
        }

        calculate_activity_ct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BluetoothActivity.this, BMISummaryScreen.class);
                spo2 = spo2_bt.getText().toString();
                blood_pressure = blood_pressure_bt.getText().toString();
                heart_rate = heart_rate_bt.getText().toString();
                if(spo2.isEmpty()){
                    spo2 = "Na";
                }
                if(blood_pressure.isEmpty()){
                    blood_pressure = "Na";
                }
                if(heart_rate.isEmpty()){
                    heart_rate = "Na";
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
                startActivity(intent);
            }
        });
    }


    private void connectToDevice(final String[] commands) {
        new Thread(new Runnable() {
            @SuppressLint("MissingPermission")
            @Override
            public void run() {
                targetDevice = bluetoothAdapter.getRemoteDevice(macid);
                try {
                    bluetoothSocket = targetDevice.createRfcommSocketToServiceRecord(uuid);
                    bluetoothSocket.connect();
                    outputStream = bluetoothSocket.getOutputStream();
                    inputStream = bluetoothSocket.getInputStream();

                    for (String command : commands) {
                        command += "\r\n"; // Add line termination
                        try {
                            outputStream.write(command.getBytes());
                            outputStream.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        // Continuously read and log responses
                        byte[] buffer = new byte[1024];
                        int bytes;
                        try {
                            while ((bytes = inputStream.read(buffer)) != -1) {
                                final String response = new String(buffer, 0, bytes);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        String[] parts = response.split(",");
                                        if (parts.length > 0 && parts[0].equals("$42")) {
                                            bmi = parts[6];
                                            height = parts[4];
                                            weight = parts[5];
                                            bmi_value_bt.setText(parts[6]+"Cal/per day");
                                            height_value_bt.setText(parts[4]+"Cm");
                                            weight_value_bt.setText(parts[5]+"KG");
                                            if(deviceSubStatus == 0){
                                                device_status_bt.setVisibility(View.VISIBLE);
                                                device_status_soon_bt.setVisibility(View.GONE);
                                                text_data_bt.setVisibility(View.GONE);
                                                bmi_scr_ct.setVisibility(View.GONE);
                                                calculate_activity_ct.setVisibility(View.GONE);
                                            }else{
                                                device_status_bt.setVisibility(View.GONE);
                                                device_status_soon_bt.setVisibility(View.GONE);
                                                text_data_bt.setVisibility(View.GONE);
                                                bmi_scr_ct.setVisibility(View.VISIBLE);
                                                calculate_activity_ct.setVisibility(View.VISIBLE);
                                            }
                                        }


                                    }
                                });
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void closeConnection() {
        try {
            if (outputStream != null) {
                outputStream.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
            if (bluetoothSocket != null) {
                bluetoothSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        closeConnection();
    }
}
