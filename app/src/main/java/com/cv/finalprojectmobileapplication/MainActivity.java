package com.cv.finalprojectmobileapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    TextView profile_text;
    ImageView profile_image;
    Button toProfile_btn, toAuth_btn;
    String image, desc;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("imageData");

    View.OnClickListener toImage = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent toImage = new Intent(MainActivity.this, ImageActivity.class);
            startActivity(toImage);
        }
    };

    View.OnClickListener toProfile = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent toProfile = new Intent(MainActivity.this, ProfileActivity.class);
            startActivity(toProfile);
        }
    };

    View.OnClickListener toAuth = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent toAuth = new Intent(MainActivity.this, AuthActivity.class);
            startActivity(toAuth);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        profile_text = findViewById(R.id.profile_text);

        image = "data:image/png;base64,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";
        desc = "This is the logo of Android.";
        ImageData imageData = new ImageData(image, desc);
        ref.setValue(imageData);

        profile_image = findViewById(R.id.profile_image);
        profile_image.setOnClickListener(toImage);
        toProfile_btn = findViewById(R.id.profile_btn);
        toProfile_btn.setOnClickListener(toProfile);
        toAuth_btn = findViewById(R.id.logout_btn);
        toAuth_btn.setOnClickListener(toAuth);

        Glide.with(MainActivity.this).load(imageData.getImage()).into(profile_image);

    }
}