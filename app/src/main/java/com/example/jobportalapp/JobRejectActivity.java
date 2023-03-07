package com.example.jobportalapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class JobRejectActivity extends AppCompatActivity {
    String username;
    ListView listView;
    private List<Rejected> joblist;
    DatabaseReference databaseReference;
    private JobRejectedAdapter customAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_reject);

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            username = bundle.getString("username");
        }

        listView = findViewById(R.id.ListViewId);
        databaseReference = FirebaseDatabase.getInstance().getReference("Rejected").child(username);
        joblist = new ArrayList<>();
        customAdapter = new JobRejectedAdapter(JobRejectActivity.this,joblist);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                joblist.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    Rejected rejected = dataSnapshot.getValue(Rejected.class);
                    joblist.add(rejected);
                }
                listView.setEmptyView(findViewById(android.R.id.empty));
                listView.setAdapter(customAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error){

            }
        });
    }
}