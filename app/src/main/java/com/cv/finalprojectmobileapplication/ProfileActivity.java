package com.cv.finalprojectmobileapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {

    ImageView toMainBtn;
    TextView titleName, name, titleEmail, email;
    String nameAcc, emailAcc;
    SharedPreferences data;

    View.OnClickListener toMain = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent toMain = new Intent(ProfileActivity.this, MainActivity.class);
            startActivity(toMain);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        toMainBtn = findViewById(R.id.toMain_btn);
        toMainBtn.setOnClickListener(toMain);

        titleName = findViewById(R.id.profileNameTitle_text);
        titleEmail = findViewById(R.id.profileEmailTitle_text);
        name = findViewById(R.id.profileName_text);
        email = findViewById(R.id.profileEmail_text);

        data = getSharedPreferences("data", MODE_PRIVATE);
        nameAcc = data.getString("name", "");
        emailAcc = data.getString("email", "");
        name.setText(nameAcc);
        email.setText(emailAcc);
    }
}