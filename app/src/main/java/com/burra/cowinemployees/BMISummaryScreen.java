package com.burra.cowinemployees;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;
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
    String recommandation_data;

    String leanBodyMass;

    String metabolic_age;

    int age_ct;

    static double min_threshold=18.0f,max_threshold=25.0f, under_weight=0,over_weight=0,bmi_value=26f,heightMtr_value=0;

    Date currentDate;

    private TextView countdownTextView;

    private CountDownTimer countDownTimer;


    TextView final_name_dt,final_age_dt,final_gender_dt,final_phone_dt, final_height_dt,final_weight_dt,
            final_bmi_dt, final_bodyfat_dt, final_totalbodywater_dt,final_bmr_dt,final_spo2_dt, final_heartrate_dt,
            final_bloodpressure_dt,final_nutration_dt,final_recommandation_dt, bmi_status_color_dt;
    Button fianl_save_share_dt;
    @SuppressLint("MissingInflatedId")
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
        countdownTextView = findViewById(R.id.countdownTextView);
        long initialTime = 60000; // 1 minute
        startCountdown(initialTime);



//        final_name_dt = findViewById(R.id.final_name_tv);
//        final_age_dt = findViewById(R.id.final_age_tv);
//        final_gender_dt = findViewById(R.id.final_gender_tv);
//        final_phone_dt = findViewById(R.id.final_phone_tv);
        final_height_dt = findViewById(R.id.final_height_tv);
        final_weight_dt = findViewById(R.id.final_weight_tv);
        final_bmi_dt = findViewById(R.id.final_bmi_tv);
        final_bodyfat_dt = findViewById(R.id.final_bodyfat_tv);
        final_bmr_dt = findViewById(R.id.final_bmr_tv);
        final_totalbodywater_dt = findViewById(R.id.final_totalbodywater_tv);
        final_spo2_dt = findViewById(R.id.final_spo2_tv);
        final_heartrate_dt = findViewById(R.id.final_heartrate_tv);
        final_bloodpressure_dt = findViewById(R.id.final_bloodpressure_tv);
        fianl_save_share_dt = findViewById(R.id.save_share);
//        final_nutration_dt = findViewById(R.id.final_nutration_tv);
//        final_recommandation_dt = findViewById(R.id.final_recommandation_tv);
        bmi_status_color_dt = findViewById(R.id.bmi_status_color);



        age_ct = Integer.parseInt(age);



        DecimalFormat df = new DecimalFormat("#.##");


        bodyfat = (double)((double)1.20f * Double.parseDouble(bmi) ) + (double)((double) 0.23f * age_ct);
        if (Double.parseDouble(height) != 0 && age_ct != 0) {
            Log.e("3333333333333333333", gender);

            if (gender.equalsIgnoreCase("Male")) {
                bodyfat = bodyfat - 16.2;
                double tempTotalBodyWater = Math.abs(2.447 - (0.09156 * age_ct + 0.1074 * Double.parseDouble(height) + 0.3362 * Double.parseDouble(weight)));
                totalbodywater = String.valueOf((tempTotalBodyWater * 100.0) / Double.parseDouble(weight));
                System.out.println(totalbodywater);
            }

            if (gender.equalsIgnoreCase("Female")) {
                bodyfat = bodyfat - 5.4;
                double tempTotalBodyWater = -2.097 + ((0.1069 * Double.parseDouble(height)) + (0.2466 * Double.parseDouble(weight)));
                totalbodywater = String.valueOf((tempTotalBodyWater * 100.0) / Double.parseDouble(weight));
                System.out.println(totalbodywater);
            }

            bmr = String.valueOf(370.0 + (21.6 * (1 - (bodyfat / 100.0)) * Double.parseDouble(weight)));
        }
        if(Double.parseDouble(bmi) < 18.0){
            nutr_status = "UNDERWEIGHT";
            recommandation_data = "Add more calories and protein to regular diet, for example, including egg, banana in morning breakfast, etc.. can HELP in Increase the weight.";
        }else if(Double.parseDouble(bmi) <= 25.0){
            nutr_status = "NORMAL";
            recommandation_data = "Your Healthy";
        }else if(Double.parseDouble(bmi) <= 30.0){
            nutr_status = "OVERWEIGHT";
            recommandation_data = "Choose Minimally processed , whole food-whole grains, vegetables, fruits, nuts, healthful sources of protein (fish, poultry, beans), and plant oils.";
        } else if(Double.parseDouble(bmi) > 30.0){
            nutr_status = "OBESE";
        }


//        if(Math.round(Double.parseDouble(bmi)) < 18){
//            nutr_status = "UNDERWEIGHT";
//        }else if(Math.round(Double.parseDouble(bmi)) <= 25){
//            nutr_status = "NORMAL";
//        }else if(Math.round(Double.parseDouble(bmi)) <= 30){
//            nutr_status = "OVERWEIGHT";
//        } else if(Math.round(Double.parseDouble(bmi)) > 30){
//            nutr_status = "OBESE";
//        }
        if (Double.parseDouble(height) != 0) {
            heightMtr_value = Double.parseDouble(height) / 100;
            heightMtr_value *= heightMtr_value;

            if (Double.parseDouble(bmi) < min_threshold) {
                over_weight = 0;
                under_weight = (min_threshold - Double.parseDouble(bmi)) * heightMtr_value;
                nutration = nutr_status + ": Underweight " + df.format(under_weight) + " kg";
                Log.e("ccccccccccccccccc", "under_weight");
            } else if (Double.parseDouble(bmi) > max_threshold) {
                under_weight = 0;
                over_weight = (Double.parseDouble(bmi) - max_threshold) * heightMtr_value;
                nutration = nutr_status + ": Overweight " + df.format(over_weight) + " kg";
                Log.e("ccccccccccccccccc", "over_weight");
            } else {
                nutration = nutr_status + ": Normal Weight";
            }
        } else {
            // Handle the case when height is zero or invalid
            nutration = "Invalid height input.";
        }

        leanBodyMass = String.valueOf(Double.parseDouble(weight) - (double)((Double.parseDouble(weight) * bodyfat)/100));

        if(gender.equalsIgnoreCase("male")){
            metabolic_age = String.valueOf((double)(((double)88.362f + ((double)13.397f * Double.parseDouble(weight)) + (double)((double)4.799f * Double.parseDouble(height) )) - Double.parseDouble(bmr))/(double)5.677f);
        }else {
            metabolic_age = String.valueOf((double)(((double)447.593f + ((double)9.247f * (double)Double.parseDouble(weight)) + (double)((double)3.098f * Double.parseDouble(height)) - Double.parseDouble(bmr))/(double)4.33f));
        }




        final_height_dt.setText(height +" "+"cm");
        final_weight_dt.setText(weight +" "+"Kg");
        final_bmi_dt.setText(bmi +""+"KG/M2");
        final_bodyfat_dt.setText(df.format(Double.parseDouble(String.valueOf(bodyfat)))+" "+"%");
        final_bmr_dt.setText(df.format(Double.parseDouble(bmr))+" "+"%");
        final_totalbodywater_dt.setText(df.format(Double.parseDouble(totalbodywater))+"Cal/per day");
        final_spo2_dt.setText(spo2);
        final_heartrate_dt.setText(heart_rate);
        final_bloodpressure_dt.setText(blood_pressure);
        if(Double.parseDouble(bmi) < 18.5){
            bmi_status_color_dt.setBackgroundColor(getResources().getColor(R.color.red_color_bc));
            bmi_status_color_dt.setText("Underweight");
            bmi_status_color_dt.setTextColor(getResources().getColor(R.color.red_color));
        }else if(Double.parseDouble(bmi) >= 18.5 && Double.parseDouble(bmi) <= 24.9){
            bmi_status_color_dt.setBackgroundColor(getResources().getColor(R.color.green_color_bc));
            bmi_status_color_dt.setText("Normal Weight");
            bmi_status_color_dt.setTextColor(getResources().getColor(R.color.green_color));
        } else if (Double.parseDouble(bmi) >= 25) {
            bmi_status_color_dt.setBackgroundColor(getResources().getColor(R.color.red_color_bc));
            bmi_status_color_dt.setText("Overweight");
            bmi_status_color_dt.setTextColor(getResources().getColor(R.color.red_color));
        }


        fianl_save_share_dt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelCountdown();
                Log.e("KKKKKKKKKKKKK", String.valueOf(currentDate));
                Intent intent = new Intent(BMISummaryScreen.this, PdfCoverterActivity.class);
                intent.putExtra("bmi_name", "cowin_bmi");
                intent.putExtra("name_patient_r", name);
                intent.putExtra("age_value_r", age_ct);
                intent.putExtra("formattedDate", String.valueOf(currentDate));
                intent.putExtra("gender_value_r", gender);
                intent.putExtra("height_value_r", height);
                intent.putExtra("weight_value_r", weight);
                intent.putExtra("bmi_value_r", bmi);
                intent.putExtra("body_FatPercentage_value_r", df.format(Double.parseDouble(String.valueOf(bodyfat))));
                intent.putExtra("total_body_water_value_r", df.format(Double.parseDouble(totalbodywater)));
                intent.putExtra("bmr_value_r", df.format(Double.parseDouble(bmr)));
                intent.putExtra("nutr_status", nutr_status);
                intent.putExtra("over_weight", df.format(over_weight));
                intent.putExtra("under_weight", df.format(under_weight));
                intent.putExtra("spo2", spo2);
                intent.putExtra("blood_pressure", blood_pressure);
                intent.putExtra("heart_rate", heart_rate);
                intent.putExtra("leanBodyMass", leanBodyMass);
                intent.putExtra("metabolic_age", metabolic_age);
                intent.putExtra("recommandation_data", recommandation_data);
                startActivity(intent);
            }
        });

//        fianl_save_share_dt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                try {
//                    JSONObject jsonBody = new JSONObject();
//                    jsonBody.put("current_date", currentDate);
//                    jsonBody.put("macid", macid);
//                    jsonBody.put("name", name);
//                    jsonBody.put("age", age_ct);
//                    jsonBody.put("gender", gender);
//                    jsonBody.put("phone", phonenumber);
//                    jsonBody.put("height", height);
//                    jsonBody.put("weight", weight);
//                    jsonBody.put("bmi", bmi);
//                    jsonBody.put("bodyfat", df.format(Double.parseDouble(String.valueOf(bodyfat))));
//                    jsonBody.put("totalbodywater", df.format(Double.parseDouble(totalbodywater)));
//                    jsonBody.put("bmr", df.format(Double.parseDouble(bmr)));
//                    jsonBody.put("spo2", spo2);
//                    jsonBody.put("heartrate", heart_rate);
//                    jsonBody.put("bloodpressure", blood_pressure);
//                    jsonBody.put("nutritional_status", nutr_status);
//                    jsonBody.put("over_weight", df.format(under_weight));
//                    jsonBody.put("under_weight", df.format(over_weight));
//                    String url = "https://verified-ready-starfish.ngrok-free.app/api/report_bmi";
//                    // Create a JsonObjectRequest with the POST method and JSON body
//                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
//                            Request.Method.POST,  // Use POST method for sending data
//                            url,
//                            jsonBody,
//                            new Response.Listener<JSONObject>() {
//                                @Override
//                                public void onResponse(JSONObject response) {
//                                    // Check the content type of the response (if available)
//                                    String contentType = response.optString("Content-Type");
//                                    Log.e("33333333333333333333333", contentType);
//
//                                    if (contentType != null && contentType.contains("application/pdf")) {
//                                        // Handle PDF response
//                                        downloadAndSavePDF(response.toString());
//                                    } else {
//                                        // Handle JSON response
//                                        Log.d("Response", response.toString());
//
//                                        // Assuming the server responds with a download URL for the PDF
//                                        String pdfDownloadUrl = response.optString("pdf_url");
//
//                                        // Download and save the PDF locally
//                                        if (pdfDownloadUrl != null && !pdfDownloadUrl.isEmpty()) {
//                                            downloadAndSavePDF(pdfDownloadUrl);
//                                        } else {
//                                            Log.e("Error", "PDF URL is missing in the response");
//                                        }
//                                    }
//                                }
//                            },
//                            new Response.ErrorListener() {
//                                @Override
//                                public void onErrorResponse(VolleyError error) {
//                                    // Handle any errors that occur
//                                    Log.e("Error", error.toString());
//                                }
//                            }
//                    );
//
//
//                    // Add the request to the Volley request queue
//                    RequestQueue requestQueue = Volley.newRequestQueue(BMISummaryScreen.this);
//                    requestQueue.add(jsonObjectRequest);
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//            private void downloadAndSavePDF(String pdfUrl) {
//                try {
//                    // Create a File object to save the PDF in the internal downloads folder
//                    File downloadsDir = getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
//                    File pdfFile = new File(downloadsDir, "kranthi.pdf");
//
//                    // Initialize the input and output streams
//                    InputStream inputStream = new URL(pdfUrl).openStream();
//                    OutputStream outputStream = new FileOutputStream(pdfFile);
//
//                    // Read and write the PDF data
//                    byte[] buffer = new byte[1024];
//                    int bytesRead;
//                    while ((bytesRead = inputStream.read(buffer)) != -1) {
//                        outputStream.write(buffer, 0, bytesRead);
//                    }
//
//                    // Close the streams
//                    inputStream.close();
//                    outputStream.close();
//
//                    Log.d("PDF", "PDF downloaded and saved to: " + pdfFile.getAbsolutePath());
//
//                    // You can now open or display the PDF file as needed
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    Log.e("Error", "Failed to download and save PDF");
//                }
//            }
//        });







    }

    private void startCountdown(long millisInFuture) {
        countDownTimer = new CountDownTimer(millisInFuture, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // Update the TextView with the remaining time
                long seconds = millisUntilFinished / 1000;
                long minutes = seconds / 60;
                long hours = minutes / 60;
                String timeFormatted = String.format("%02d:%02d:%02d", hours % 24, minutes % 60, seconds % 60);
                countdownTextView.setText(timeFormatted);
            }

            @Override
            public void onFinish() {
                // Handle countdown finished
                countdownTextView.setText("00:00:00");
                Intent intent = new Intent(BMISummaryScreen.this, MainActivity.class);
                startActivity(intent);
            }
        };
        countDownTimer.start();
    }

    // Method to cancel the countdown timer
    public void cancelCountdown() {
        Log.e("333333333333333333", "stage1");
        if (countDownTimer != null) {
            Log.e("333333333333333333", "stage2");
            countDownTimer.cancel();
        }
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
    @Override
    public void onBackPressed() {
//        // Do nothing or perform some custom action
//        // To prevent the default back behavior, don't call super.onBackPressed()
//        Intent intent = new Intent(BMISummaryScreen.this, MainActivity.class);
//        startActivity(intent);
    }
}