package com.burra.cowinemployees;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class PdfCoverter extends AppCompatActivity {
    static WebView webView;

    ImageView back_btn;

    Button button;

    String bmi_name,name_patient_r, formattedDate, gender_value_r,body_FatPercentage_value_r, total_body_water_value_r, bmr_value_r,nutr_status, over_weight, under_weight ;
    double age_value_r,height_value_r,weight_value_r, bmi_value_r;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_coverter);
        Intent intent = getIntent();
        bmi_name = intent.getStringExtra("bmi_name");
        name_patient_r = intent.getStringExtra("name_patient_r");
        age_value_r = intent.getDoubleExtra("age_value_r", 0);
        formattedDate = intent.getStringExtra("formattedDate");
        gender_value_r = intent.getStringExtra("gender_value_r");
        height_value_r = intent.getDoubleExtra("height_value_r",0);
        weight_value_r = intent.getDoubleExtra("weight_value_r",0);
        bmi_value_r = intent.getDoubleExtra("bmi_value_r",0);
        body_FatPercentage_value_r = intent.getStringExtra("body_FatPercentage_value_r");
        total_body_water_value_r = intent.getStringExtra("total_body_water_value_r");
        bmr_value_r = intent.getStringExtra("bmr_value_r");
        nutr_status = intent.getStringExtra("nutr_status");
        over_weight = intent.getStringExtra("over_weight");
        under_weight = intent.getStringExtra("under_weight");


        webView = findViewById(R.id.webView);
        button = findViewById(R.id.button);
//        back_btn = findViewById(R.id.back);
//        back_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a file reference
                File pdfFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "cowin.pdf");

                // Get the content URI using FileProvider
                Uri pdfUri = FileProvider.getUriForFile(getApplicationContext(), "de.kai_morich.simple_bluetooth_terminal", pdfFile);

                // Create an intent with action and data
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("application/pdf"); // Set the MIME type of the file
                shareIntent.putExtra(Intent.EXTRA_STREAM, pdfUri); // Attach the content URI

                    // Grant temporary read permission to the receiving app
                shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                // Show the chooser dialog
                startActivity(Intent.createChooser(shareIntent, "Share PDF using"));
            }
        });
        webView.setVisibility(View.VISIBLE);
        webView.loadDataWithBaseURL(null, "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\" />\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\" />\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />\n" +
                "    <title>Document</title>\n" +
                "    <style>\n" +
                "        @media print {\n" +
                "            @page {\n" +
                "                size: A4;\n" +
                "            }\n" +
                "        }\n" +
                "        \n" +
                "        html,\n" +
                "        body {\n" +
                "            height: 297mm;\n" +
                "            width: 210mm;\n" +
                "            margin: 0;\n" +
                "            padding: 0;\n" +
                "            padding-left: 20px;\n" +
                "            padding-right: 20px;\n" +
                "            box-sizing: border-box;\n" +
                "            /* display: flex;\n" +
                "            align-items: center;\n" +
                "            justify-content: center; */\n" +
                "            /* border: 1px solid; */\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "\n" +
                "<body>\n" +
                "    <header id=\"header\" style=\"    margin-top: 150px;\n" +
                "    height: 120px;\n" +
                "    width: 210mm;\n" +
                "    /* border: 1px solid; */\n" +
                "    /* padding: 5px 5px 5px; */\n" +
                "    box-sizing: border-box;\n" +
                "    display: flex;\n" +
                "    flex-direction: column;\n" +
                "    align-items: center;\n" +
                "    /* justify-content: center; */\">\n" +
                "        <p style=\"width: 280px; height:20px; font-weight:600; font-size:16px; font-style:normal; margin-top:0; \n" +
                "        margin-bottom:10px; \n" +
                "        text-align:center; padding-top:2px; border:2px solid; box-shadow: 3px 3px;\"> COWIN BMI200X TEST REPORT\n" +
                "        </p>\n" +
                "        <p style=\"height:20px; font-weight:500; font-size:16px; font-style:normal; margin-top:0; text-align:center;\">Invoice No : "+ bmi_name+formattedDate.split(" ")[0]+"\n" +
                "        </p>\n" +
                "        <div style=\"height:15px; position: absolute;left:120px; margin-top:60px ;font-weight:400; font-size:16px; font-style:normal;\">\n" +
                "            <p style=\"display: inline;\">Name</p>\n" +
                "            <p style=\"display: inline; margin-left:20px\">:</p>\n" +
                "            <p style=\"display: inline;margin-left:5px\">"+ name_patient_r +"</p>\n" +
                "        </div>\n" +
                "        <div style=\"height:15px; position: absolute;left:120px; margin-top:84px;font-weight:400; font-size:16px; font-style:normal;\">\n" +
                "            <p style=\"display: inline;\">Age</p>\n" +
                "            <p style=\"display: inline;margin-left:31px\">:</p>\n" +
                "            <p style=\"display: inline;margin-left:5px\">"+ age_value_r + "</p>\n" +
                "        </div>\n" +
                "        <div style=\"height:15px; position: absolute;left:460px; margin-top:60px;font-weight:400; font-size:16px; font-style:normal; \">\n" +
                "            <p style=\"display: inline;\">Date</p>\n" +
                "            <p style=\"display: inline; margin-left:20px\">:</p>\n" +
                "            <p style=\"display: inline;margin-left:5px;\">"+formattedDate+"</p>\n" +
                "        </div>\n" +
                "        <div style=\"height:15px; position: absolute;left:460px; margin-top:84px;font-weight:400; font-size:16px; font-style:normal;\">\n" +
                "            <p style=\"display: inline;\">Gender</p>\n" +
                "            <p style=\"display: inline;margin-left:48px\">:</p>\n" +
                "            <p style=\"display: inline;margin-left:5px\">"+gender_value_r +"</p>\n" +
                "        </div>\n" +
                "\n" +
                "    </header>\n" +
                "    <hr style=\"border: 1px solid black;\">\n" +
                "    <main>\n" +
                "        <section id=\"outer-container\" style=\" height: 800px; width: 210mm; /* border: 1px solid; */display: flex;flex-direction: column;box-sizing: border-box;\">\n" +
                "            <p style=\" height:20px; font-weight:600; font-size:16px; font-style:normal;text-align:center; text-decoration:underline\">COMPLETE BMI REPORT\n" +
                "            </p>\n" +
                "            <div style=\"height:15px; position: absolute;left:80px; margin-top:65px;font-weight:600; font-size:16px; font-style:normal;text-decoration:underline\">\n" +
                "                <p style=\"display: inline;\">PARAMETER NAME</p>\n" +
                "                <p style=\"display: inline;margin-left:160px\">RESULT</p>\n" +
                "                <p style=\"display: inline;margin-left:150px;\">REFERENCE RANGE\n" +
                "                </p>\n" +
                "            </div>\n" +
                "            <div style=\"height:15px; margin-left: 80px; margin-top:50px;font-weight:400; font-size:16px; font-style:normal;\">\n" +
                "                <p style=\"width:180px; display: inline-block;\">Height</p>\n" +
                "                <p style=\"width:180px; display: inline-block;margin-left:90px;\">:" +height_value_r +"&nbsp cm</p>\n" +
                "                <p style=\"display: inline-block;margin-left:60px\">(50-200)cm\n" +
                "                </p>\n" +
                "            </div>\n" +
                "            <div style=\"height:15px; margin-left: 80px; margin-top:20px;font-weight:400; font-size:16px; font-style:normal;\">\n" +
                "                <p style=\"width:180px; display: inline-block;\">Weight</p>\n" +
                "                <p style=\"width:180px; display: inline-block;margin-left:90px;\">:" +weight_value_r +"&nbsp kg</p>\n" +
                "                <p style=\"display: inline;margin-left:60px\">(10-150)kg\n" +
                "                </p>\n" +
                "            </div>\n" +
                "            <div style=\"height:15px; margin-left: 80px; margin-top:20px;font-weight:400; font-size:16px; font-style:normal;\">\n" +
                "                <p style=\"width:180px; display: inline-block;\">BMI</p>\n" +
                "                <p style=\"width:180px; display: inline-block;margin-left:90px;\">: " +bmi_value_r +"&nbsp KG/M2</p>\n" +
                "                <p style=\"display: inline;margin-left:60px\">(18-25) KG/M2\n" +
                "\n" +
                "                </p>\n" +
                "            </div>\n" +
                "            <div style=\"height:15px; margin-left: 80px; margin-top:20px;font-weight:400; font-size:16px; font-style:normal;\">\n" +
                "                <p style=\"width:180px; display: inline-block;\">Bodyfat</p>\n" +
                "                <p style=\"width:180px; display: inline-block;margin-left:90px;\">: " +body_FatPercentage_value_r+"%</p>\n" +
                "                <p style=\"display: inline;margin-left:60px\">(21-33)%\n" +
                "                </p>\n" +
                "            </div>\n" +
                "            <div style=\"height:15px; margin-left: 80px; margin-top:20px;font-weight:400; font-size:16px; font-style:normal;\">\n" +
                "                <p style=\"width:180px; display: inline-block;\">Total Body Water</p>\n" +
                "                <p style=\"width:180px; display: inline-block;margin-left:90px;\">: " +total_body_water_value_r+"%</p>\n" +
                "                <p style=\"display: inline;margin-left:60px\">(50-66)%\n" +
                "                </p>\n" +
                "            </div>\n" +
                "            <div style=\"height:15px; margin-left: 80px; margin-top:20px;font-weight:400; font-size:16px; font-style:normal;\">\n" +
                "                <p style=\"width:180px; display: inline-block;\">BMR </p>\n" +
                "                <p style=\"width:180px; display: inline-block;margin-left:90px;\">: " + bmr_value_r +"&nbsp Cal/per day</p>\n" +
                "                <p style=\"display: inline;margin-left:60px\">(1170-2440) Cal/per day\n" +
                "                </p>\n" +
                "            </div>\n" +
                "            <div style=\"height:15px; margin-left: 80px; margin-top:20px;font-weight:600; font-size:16px; font-style:normal;\">\n" +
                "                <p style=\"width:180px; display: inline-block;text-decoration:underline;\">Nutrition Status</p>\n" +
                "                <p style=\"width:180px; display: inline-block;margin-left:90px;\">:"+nutr_status+"</p>\n" +
                "                <p style=\"display: inline;margin-left:60px\">\n" +
                "                </p>\n" +
                "            </div>\n" +
                "            <div style=\"height:15px; margin-left: 80px; margin-top:20px;font-weight:400; font-size:16px; font-style:normal;\">\n" +
                "                <p style=\"width:180px; display: inline-block;\">Over weight</p>\n" +
                "                <p style=\"width:180px; display: inline-block;margin-left:90px;\">: " +over_weight+"&nbsp kg"+"</p>\n" +
                "                <p style=\"display: inline;margin-left:60px\">\n" +
                "                </p>\n" +
                "            </div>\n" +
                "            <div style=\"height:15px; margin-left: 80px; margin-top:20px;font-weight:400; font-size:16px; font-style:normal;\">\n" +
                "                <p style=\"width:180px; display: inline-block;\">Under weight</p>\n" +
                "                <p style=\"width:180px; display: inline-block;margin-left:90px;\">: " +under_weight +"&nbsp kg"+"</p>\n" +
                "                <p style=\"display: inline;margin-left:60px\">\n" +
                "                </p>\n" +
                "            </div>\n" +
                "            <div style=\"height:15px; margin-left: 80px; margin-top:20px;font-weight:400; font-size:16px; font-style:normal;\">\n" +
                "                <p style=\"width:180px; display: inline-block;\">SPO2\n" +
                "                </p>\n" +
                "                <p style=\"width:180px; display: inline-block;margin-left:90px;\">: N/A</p>\n" +
                "                <p style=\"display: inline;margin-left:60px\">(95-100)%\n" +
                "                </p>\n" +
                "            </div>\n" +
                "            <div style=\"height:15px; margin-left: 80px; margin-top:20px;font-weight:400; font-size:16px; font-style:normal;\">\n" +
                "                <p style=\"width:180px; display: inline-block;\">Heartrate</p>\n" +
                "                <p style=\"width:180px; display: inline-block;margin-left:90px;\">: N/A</p>\n" +
                "                <p style=\"display: inline;margin-left:60px\">(55-70)bpm\n" +
                "                </p>\n" +
                "            </div>\n" +
                "            <div style=\"height:15px; margin-left: 80px; margin-top:20px;font-weight:400; font-size:16px; font-style:normal;\">\n" +
                "                <p style=\"width:180px; display: inline-block;\">Blood presure</p>\n" +
                "                <p style=\"width:180px; display: inline-block;margin-left:90px;\">: N/A</p>\n" +
                "                <p style=\"display: inline;margin-left:60px\">(120/80)mmHg\n" +
                "                </p>\n" +
                "            </div>\n" +
                "            <div style=\"height:auto; margin-left: 80px; margin-top:20px;font-weight:400; font-size:16px; font-style:normal;\">\n" +
                "                <p style=\"width:180px; display: inline-block;position:absolute;margin-top:20px;\">Recommendation</p>\n" +
                "                <p style=\"width: 400px; display: inline-block;margin-left:278px;  line-height:20px\">: </p>\n" +
                "            </div>\n" +
                "            <div style=\"width: 680px;height:auto; margin-left: 80px; margin-top:20px;font-weight:400; font-size:16px; position:relative;font-style:normal;\">\n" +
                "                <p style=\"display: inline-block;height:auto; position:relative;\"><span style=\"font-weight:600;line-height: 24px;\">Disclaimer</span><br>Please contact a dietician for health weight loss or gain weight.<br>**** This is a computer generated report with over 96%* accuracy and \n" +
                "                    Please consult physician or other health care profession for further guidance.</p>\n" +
                "                <p style=\"\">*T&C</p>\n" +
                "            </div>\n" +
                "\n" +
                "\n" +
                "        </section>\n" +
                "    </main>\n" +
                "</body>\n" +
                "\n" +
                "</html>", "text/html", "utf-8", null);
        webView.getSettings();
        webView.setInitialScale(150);
        generateAndSavePdf();
    }

    private void generateAndSavePdf() {
        // Check if the WebView has finished loading its content
        if (!webView.getSettings().getLoadsImagesAutomatically()) {
            webView.getSettings().setLoadsImagesAutomatically(true);
        }

        // Wait for WebView to finish rendering the content
        webView.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Get a WebView bitmap
                Bitmap bitmap = Bitmap.createBitmap(
                        webView.getWidth(),
                        webView.getHeight(),
                        Bitmap.Config.ARGB_8888
                );

                Canvas canvas = new Canvas(bitmap);
                webView.draw(canvas);

                // Save the bitmap as a PDF
//                String file_name = name_patient_r+"cowin.pdf";
                File pdfFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "cowin.pdf");
                try {
                    FileOutputStream fos = new FileOutputStream(pdfFile);
                    PdfDocument document = new PdfDocument();
                    PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(bitmap.getWidth(), bitmap.getHeight(), 1).create();
                    PdfDocument.Page page = document.startPage(pageInfo);
                    page.getCanvas().drawBitmap(bitmap, 0, 0, null);
                    document.finishPage(page);
                    document.writeTo(fos);
                    document.close();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, 1000); // Delay to allow WebView to render content
    }

}