package com.burra.cowinemployees;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class BMISummaryScreen extends AppCompatActivity {
    String name;
    String age;
    String gender;
    String macid;
    String phonenumber;
    String bmi;
    String height;
    String weight;
    String spo2;
    String heart_rate;
    String blood_pressure;
    Double bodyfat;
    String totalbodywater;
    String bmr;
    String nutration;
    String recommandation;

    String nutr_status;

    int age_ct;

    static double min_threshold=18.0f,max_threshold=25.0f, under_weight=0,over_weight=0,bmi_value=26f,heightMtr_value=0;

    Date currentDate;

    TextView final_name_dt,final_age_dt,final_gender_dt,final_phone_dt, final_height_dt,final_weight_dt,
            final_bmi_dt, final_bodyfat_dt, final_totalbodywater_dt,final_bmr_dt,final_spo2_dt, final_heartrate_dt,
            final_bloodpressure_dt,final_nutration_dt,final_recommandation_dt;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmisummary_screen);
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        age = intent.getStringExtra("age");
        gender = intent.getStringExtra("gender");
        phonenumber = intent.getStringExtra("phonenumber");
        macid = intent.getStringExtra("macid");
        bmi = intent.getStringExtra("bmi");
        height = intent.getStringExtra("height");
        weight = intent.getStringExtra("weight");
        spo2 = intent.getStringExtra("spo2");
        heart_rate = intent.getStringExtra("heart_rate");
        blood_pressure = intent.getStringExtra("blood_pressure");



        final_name_dt = findViewById(R.id.final_name_tv);
        final_age_dt = findViewById(R.id.final_age_tv);
        final_gender_dt = findViewById(R.id.final_gender_tv);
        final_phone_dt = findViewById(R.id.final_phone_tv);
        final_height_dt = findViewById(R.id.final_height_tv);
        final_weight_dt = findViewById(R.id.final_weight_tv);
        final_bmi_dt = findViewById(R.id.final_bmi_tv);
        final_bodyfat_dt = findViewById(R.id.final_bodyfat_tv);
        final_bmr_dt = findViewById(R.id.final_bmr_tv);
        final_totalbodywater_dt = findViewById(R.id.final_totalbodywater_tv);
        final_spo2_dt = findViewById(R.id.final_spo2_tv);
        final_heartrate_dt = findViewById(R.id.final_heartrate_tv);
        final_bloodpressure_dt = findViewById(R.id.final_bloodpressure_tv);
        final_nutration_dt = findViewById(R.id.final_nutration_tv);
        final_recommandation_dt = findViewById(R.id.final_recommandation_tv);

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

        try {
            // Parse the birthdate string into a Date object
            Date birthdate = sdf.parse(age);

            // Get the current date
            currentDate = new Date();

            // Calculate the age
            age_ct = calculateAge(birthdate, currentDate);

            // Display the age
            System.out.println("Age: " + age);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        DecimalFormat df = new DecimalFormat("#.##");


        bodyfat = (double)((double)1.20f * Double.parseDouble(bmi) ) + (double)((double) 0.23f * age_ct);

        if(Double.parseDouble(height)!=0 && age_ct!=0){

            Log.e("3333333333333333333",gender);
            if (gender.equalsIgnoreCase("Male")) {
                bodyfat = bodyfat - (double)16.2f;
                totalbodywater = String.valueOf(Math. abs((double)2.447f - (double)(((double)0.09156f * age_ct) + ((double)0.1074f * Double.parseDouble(height)) + ((double)0.3362f * Double.parseDouble(weight)))));
                System.out.println(totalbodywater);
                totalbodywater = String.valueOf(( ( Double.parseDouble(totalbodywater) * (double)100.0f) /  Double.parseDouble(weight)));
            }

            if (gender.equalsIgnoreCase("Female")) {
                bodyfat = bodyfat - (double)5.4f;
                totalbodywater = String.valueOf(-(double)2.097f + (double)( ((double)0.1069f * Double.parseDouble(height)) + ((double)0.2466f * Double.parseDouble(weight))));
                totalbodywater = String.valueOf(( ( Double.parseDouble(totalbodywater)* (double)100.0f) /  Double.parseDouble(weight)));
                System.out.println(totalbodywater);
            }

            bmr = String.valueOf((double)370.0f + ((double)21.6f * (double)(1 - (bodyfat/(double)100.0f))*Double.parseDouble(weight)));

        }

        if(Math.round(Double.parseDouble(bmi)) < 18){
            nutr_status = "UNDERWEIGHT";
        }else if(Math.round( Double.parseDouble(bmi)) <= 25){
            nutr_status = "NORMAL";
        }else if(Math.round( Double.parseDouble(bmi)) <= 30){
            nutr_status = "OVERWEIGHT";
        } else if(Math.round( Double.parseDouble(bmi)) > 30){
            nutr_status = "OBESE";
        }

        if(Double.parseDouble(height) != 0){

            if(Double.parseDouble(bmi) < min_threshold)
            {
                heightMtr_value = (double)Double.parseDouble(height)/100;
                heightMtr_value*=heightMtr_value;
                if(heightMtr_value != 0)
                {
                    under_weight = (min_threshold - Double.parseDouble(bmi)) * (heightMtr_value);
                    nutration = nutr_status + ":" + df.format(under_weight);
                }
            }
            if(Double.parseDouble(bmi) > max_threshold)
            {
                heightMtr_value = (double)Double.parseDouble(height)/100;
                heightMtr_value*=heightMtr_value;
                if(heightMtr_value != 0)
                {
                    over_weight = ((Double.parseDouble(bmi)) - max_threshold) * (heightMtr_value);
                    nutration = nutr_status + ":" + df.format(over_weight);
                }
            }

        }




        final_name_dt.setText(name);
        final_age_dt.setText(String.valueOf(age_ct));
        final_gender_dt.setText(gender);
        final_phone_dt.setText(phonenumber);
        final_height_dt.setText(height);
        final_weight_dt.setText(weight);
        final_bmi_dt.setText(bmi);
        final_bodyfat_dt.setText(df.format(Double.parseDouble(String.valueOf(bodyfat))));
        final_bmr_dt.setText(df.format(Double.parseDouble(bmr)));
        final_totalbodywater_dt.setText(df.format(Double.parseDouble(totalbodywater)));
        final_spo2_dt.setText(spo2);
        final_heartrate_dt.setText(heart_rate);
        final_bloodpressure_dt.setText(blood_pressure);
        final_nutration_dt.setText(nutration);
        final_recommandation_dt.setText(recommandation);


        new CountDownTimer(1000, 1000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {

                try {
                    JSONObject jsonBody = new JSONObject();
                    jsonBody.put("current_date", currentDate);
                    jsonBody.put("macid", macid);
                    jsonBody.put("name", name);
                    jsonBody.put("age", age_ct);
                    jsonBody.put("gender", gender);
                    jsonBody.put("phone", phonenumber);
                    jsonBody.put("height", height);
                    jsonBody.put("weight", weight);
                    jsonBody.put("bmi", bmi);
                    jsonBody.put("bodyfat", df.format(Double.parseDouble(String.valueOf(bodyfat))));
                    jsonBody.put("totalbodywater", df.format(Double.parseDouble(totalbodywater)));
                    jsonBody.put("bmr", df.format(Double.parseDouble(bmr)));
                    jsonBody.put("spo2", spo2);
                    jsonBody.put("heartrate", heart_rate);
                    jsonBody.put("bloodpressure", blood_pressure);
                    jsonBody.put("nutritional_status",nutr_status);
                    jsonBody.put("over_weight", df.format(under_weight));
                    jsonBody.put("under_weight", df.format(over_weight));

                    // Define the URL of the API endpoint
                    String url = "https://cowinserver.cowinbmi.io/api/bmi_data_insert";

                    // Create a JsonObjectRequest with the POST method and JSON body
                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                            Request.Method.POST,
                            url,
                            jsonBody,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    // Handle the response from the server
                                    Log.d("Response", response.toString());
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
                    RequestQueue requestQueue = Volley.newRequestQueue(BMISummaryScreen.this);
                    requestQueue.add(jsonObjectRequest);

                } catch (JSONException e) {
                    e.printStackTrace();
                }




            }
        }.start();







    }

    public static int calculateAge(Date birthdate, Date currentDate) {
        Calendar birthCalendar = Calendar.getInstance();
        birthCalendar.setTime(birthdate);

        Calendar currentCalendar = Calendar.getInstance();
        currentCalendar.setTime(currentDate);

        int years = currentCalendar.get(Calendar.YEAR) - birthCalendar.get(Calendar.YEAR);

        if (currentCalendar.get(Calendar.DAY_OF_YEAR) < birthCalendar.get(Calendar.DAY_OF_YEAR)) {
            years--;
        }

        return years;
    }
}