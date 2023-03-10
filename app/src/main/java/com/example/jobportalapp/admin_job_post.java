package com.example.jobportalapp;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class admin_job_post extends AppCompatActivity {
    String username;
    Button postButton;
    EditText jobIdTxt,jobTitleTxt, companyTxt, salaryTxt, descTxt, reqTxt, dateTxt;
    DatabaseReference databaseReference,ref;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_job_post);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        ref = FirebaseDatabase.getInstance().getReference();
        jobIdTxt = findViewById(R.id.jobIdEditText);
        jobTitleTxt = findViewById(R.id.jobTitleEditText);
        companyTxt = findViewById(R.id.companyEditText);
        salaryTxt = findViewById(R.id.salaryEditText);
        descTxt = findViewById(R.id.descriptionEditText);
        reqTxt = findViewById(R.id.requirementEditText);
        dateTxt = findViewById(R.id.dateEditText);
        postButton = findViewById(R.id.postJob);

        Bundle bundle = getIntent().getExtras();

        if(bundle!=null){
            username = bundle.getString("username");
        }

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                savedata();
            }

        });

    }

    private void savedata() {
        String job_id = jobIdTxt.getText().toString();
        String job_name = jobTitleTxt.getText().toString();
        String company_name = companyTxt.getText().toString();
        String salary_amount = salaryTxt.getText().toString();
        String job_desc = descTxt.getText().toString();
        String job_req = reqTxt.getText().toString();
        String job_post_date = dateTxt.getText().toString();
        if(job_id.isEmpty()){
            jobIdTxt.setError("Empty Field");
            jobIdTxt.requestFocus();
        }
        else if(job_name.isEmpty()){
            jobTitleTxt.setError("Empty Field");
            jobTitleTxt.requestFocus();
        }
        else if(company_name.isEmpty()){
            companyTxt.setError("Empty Field");
            companyTxt.requestFocus();
        }
        else if(salary_amount.isEmpty()){
            salaryTxt.setError("Empty Field");
            salaryTxt.requestFocus();
        }
        else if(job_desc.isEmpty()){
            descTxt.setError("Empty Field");
            descTxt.requestFocus();
        }
        else if(job_req.isEmpty()){
            reqTxt.setError("Empty Field");
            reqTxt.requestFocus();
        }
        else if(job_post_date.isEmpty()){
            dateTxt.setError("Empty Field");
            dateTxt.requestFocus();
        }
        else if(!company_name.equals(username)){
            companyTxt.setError("Company doesn't match");
            companyTxt.requestFocus();
        }
        else{
            ref.child("All Jobs").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.hasChild(job_id)){
                        jobIdTxt.setError("Job id exists");
                        jobIdTxt.requestFocus();
                    }
                    else{
                        Job job = new Job(job_id,job_name,company_name,salary_amount,job_desc,job_req,job_post_date);
                        databaseReference.child("Company Jobs").child(username).child(job_id).setValue(job);
                        ref.child("All Jobs").child(job_id).setValue(job);
                        Toast.makeText(admin_job_post.this, "New Job Added", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(admin_job_post.this,AdminJobPostList.class);
                        intent.putExtra("username",username);
                        startActivity(intent);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
    }
}