package com.burra.cowinemployees;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

public class PDFLoader extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfloader);

        webView = findViewById(R.id.webview);

        // Enable JavaScript and other settings as needed
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // Create a WebViewClient to handle page navigation
        webView.setWebViewClient(new WebViewClient());

        // Define the URL you want to load with the POST request

        String url = "https://cowinserver.cowinbmi.io/api/report_bmi";
        String postData = "key1=value1&key2=value2"; // Your POST data in the format "key=value&key=value"

        // Load the URL with a POST request
        webView.loadUrl(url);

        // Optionally, you can add WebViewClient methods to handle specific events
    }
}