package com.example.myapplication.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.DBUtil.Database;
import com.example.myapplication.R;


import com.example.myapplication.Entity.Users;
import com.example.myapplication.signup;

import java.util.ArrayList;

public class UserAdapters extends ArrayAdapter<Users>{
    public UserAdapters(@NonNull Context context, ArrayList<Users> dataArrayList) {
        super(context, R.layout.list_item, dataArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {

        Users listData = getItem(position);
        if (view == null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }
        TextView listName = view.findViewById(R.id.listName);
        TextView listEmail = view.findViewById(R.id.listEmail);
        TextView listDob = view.findViewById(R.id.listDob);
        TextView delete = view.findViewById(R.id.listDelete);
        TextView update = view.findViewById(R.id.listUpdate);

        listName.setText(listData != null ? listData.username : "");
        listEmail.setText(listData != null ? listData.email : "");
        listDob.setText(listData != null ? listData.dob : "");
        Database db = new Database(view.getContext());
        View finalView = view;
        delete.setOnClickListener(view1 -> {
            Toast.makeText(view1.getContext(), "Delete call!!", Toast.LENGTH_LONG).show();
            db.deleteUser(listData.id);
            remove(listData);
            notifyDataSetChanged();
        });

        update.setOnClickListener(v -> {
            Toast.makeText(v.getContext(), "Edit Call", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(v.getContext(), signup.class);

            intent.putExtra("USER_ID", listData.id);  // Pass the user ID
            intent.putExtra("USER_NAME", listData.username);  // Pass the username
            intent.putExtra("USER_EMAIL", listData.email);  // Pass the email
            intent.putExtra("USER_DOB", listData.dob);  // Pass the date of birth
            v.getContext().startActivity(intent);
        });

        return view;
    }

}
