package com.example.user.attendance_tracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ShowReset extends AppCompatActivity {
    Button okayBtn;
    //Update
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_reset);
        okayBtn = findViewById(R.id.okayButton);
        okayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ShowReset.this,MainActivity.class));
                finish();
            }
        });
    }
}
