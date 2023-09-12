package com.burra.adaptors;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.burra.cowinemployees.R;
import com.burra.modelclass.UserRecord;

import java.util.ArrayList;
import java.util.List;

public class UserRecordsAdapter extends RecyclerView.Adapter<UserRecordsAdapter.ViewHolder> {
    private List<UserRecord> userRecords;
    private List<UserRecord> filteredRecords; // Add this list for filtered data

    public UserRecordsAdapter(List<UserRecord> userRecords) {
        this.userRecords = userRecords;
        this.filteredRecords = userRecords; // Initialize with all data
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_record, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserRecord userRecord = filteredRecords.get(position);
        // Bind data to views in the ViewHolder
        holder.idTextView.setText("ID :" + userRecord.getId());
        holder.nameTextView.setText("Name :" + userRecord.getName());
        holder.ageTextView.setText("Age :"+userRecord.getAge());
        holder.genderTextView.setText("Gender :"+userRecord.getGender());
        holder.dateTextView.setText("Date  :"+userRecord.getDate());
        holder.phoneTextView.setText("Phone Number :"+userRecord.getPhone());
        // ... Bind other data to views
    }

    @Override
    public int getItemCount() {
        return filteredRecords.size();
    }

    public void setFilteredList(List<UserRecord> filteredRecords) {
        this.filteredRecords = filteredRecords;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Define your views here
        public TextView idTextView, nameTextView, ageTextView, genderTextView, dateTextView, phoneTextView;
        public Button more_details;
        // ...

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Initialize views
            idTextView = itemView.findViewById(R.id.idTextView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            ageTextView = itemView.findViewById(R.id.ageTextView);
            genderTextView = itemView.findViewById(R.id.genderTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            phoneTextView = itemView.findViewById(R.id.phoneTextView);
            more_details = itemView.findViewById(R.id.more_details);
            // ...
        }
    }
}
