package com.example.myapplication.restApi;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.Entity.Student;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class StudentHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_student_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView textView = findViewById(R.id.studentHomeTextView);
        textView.setText("Student Home Activity");
        Button button = findViewById(R.id.studentHomeButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                ApiRequestDao apiRequest = new ApiRequestDao();


                apiRequest.getList(StudentHomeActivity.this, new ApiRequestDao.ApiCallback() {
                    @Override
                    public void onSuccess(List<Student> students) {
                        // Handle the successful response and update the UI
                        StringBuilder studentDetails = new StringBuilder();
                        for (Student student : students) {
                            studentDetails.append("Name: " + student.getName() + "\n");
                            studentDetails.append("Username: " + student.getUsername() + "\n");
                            studentDetails.append("ID: " + student.getId() + "\n\n");
                        }

                        // Set the details to the TextView
                        textView.setText(studentDetails.toString());

                        // Optionally log the student names for debugging
                        for (Student student : students) {
                            Log.d("Student Info", student.getName());
                        }
                    }

                    @Override
                    public void onError(String errorMessage) {
                        // Handle the error (e.g., show a Toast)
                        Toast.makeText(StudentHomeActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                        textView.setText(errorMessage);
                    }
                });


                //**Post Object

//                Student student = new Student();
//                student.setName("Bangladesh");
//                student.setUsername("Bangladesh");
//                student.setPassword("5644 Bangladesh");

//                apiRequest.addStudent(getApplicationContext(),student);

                //**Update
//                student.setId(45574);
//                apiRequest.updateStudent(getApplicationContext(),student);


                //**Delete
//                apiRequest.deleteStudent(getApplicationContext(), 45574);


            }
        });
        Button goToAddStudentButton = findViewById(R.id.goToAddStudentButton);
        goToAddStudentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle the button click event here
                Toast.makeText(StudentHomeActivity.this, " Add Button clicked!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(StudentHomeActivity.this, AddStudentActivity.class);
                startActivity(intent);
            }
        });

    }
}