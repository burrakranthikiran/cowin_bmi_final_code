package com.burra.cowinemployees;



import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.burra.adaptors.UserRecordsAdapter;
import com.burra.modelclass.UserRecord;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UserRecords extends AppCompatActivity {
    private static final String PREFS_NAME = "MyPrefs";
    private static final String PREF_MAC_ID = "mac_id";
    String macid;
    private List<UserRecord> userRecords;
    private RecyclerView recyclerView;
    private UserRecordsAdapter adapter;

    EditText searchEditText;

    ImageView back_icon_kt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_records);

        searchEditText = findViewById(R.id.searchEditText);
        back_icon_kt = findViewById(R.id.back_icon);
        back_icon_kt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        userRecords = new ArrayList<>();
        adapter = new UserRecordsAdapter(userRecords);
        recyclerView.setAdapter(adapter);

        fetchDataFromAPI();

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                filter(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    private void fetchDataFromAPI() {
//        String apiUrl = "https://cowinserver.cowinbmi.io/api/device_users";
        String apiUrl = "https://verified-ready-starfish.ngrok-free.app/api/device_users";
        JSONObject requestData = new JSONObject();
        try {
            SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
            macid = preferences.getString(PREF_MAC_ID, null);
            requestData.put("macid", macid);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String requestBody = requestData.toString();

        StringRequest request = new StringRequest(Request.Method.POST, apiUrl,
                response -> {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        userRecords.clear();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            UserRecord userRecord = new UserRecord();
                            userRecord.setId(jsonObject.optString("id"));
                            userRecord.setName(jsonObject.optString("name"));
                            userRecord.setAge(jsonObject.optString("age"));
                            userRecord.setGender(jsonObject.optString("gender"));
                            userRecord.setDate(jsonObject.optString("date"));
                            userRecord.setPhone(jsonObject.optString("phone"));
                            // ... Set other data

                            userRecords.add(userRecord);
                        }
                        adapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    Log.e("RequestError", "Error fetching data: " + error.getMessage());
                }) {
            @Override
            public byte[] getBody() {
                return requestBody.getBytes();
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    private void filter(String query) {
        List<UserRecord> filteredRecords = new ArrayList<>();
        for (UserRecord record : userRecords) {
            if (record.getName().toLowerCase().contains(query.toLowerCase())) {
                filteredRecords.add(record);
            }
        }
        adapter.setFilteredList(filteredRecords);
    }
}
