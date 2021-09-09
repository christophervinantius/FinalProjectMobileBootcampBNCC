package com.cv.finalprojectmobileapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginFragment extends Fragment {

    TextView toRegisterText;
    EditText emailInput, passwordInput;
    String name, email, password;
    Button login;
    SharedPreferences data;

    private FirebaseAuth auth;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("imageData");

    View.OnClickListener toRegister = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.frame_container, new RegisterFragment())
                    .addToBackStack(null).commit();
        }
    };

    View.OnClickListener loginAcc = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            email = emailInput.getText().toString();
            password = passwordInput.getText().toString();

            auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(requireActivity(), task -> {
                        if (task.isSuccessful()) {
                            int at = email.indexOf("@");
                            if(at != 1){
                                name = email.substring(0, at);
                            }
                            data = requireActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
                            SharedPreferences.Editor edit = data.edit();
                            String nameValue = data.getString("name", "");
                            if(nameValue == null){
                                edit.putString("name", name);
                            }
                            edit.putString("email", email);
                            edit.apply();
                            Intent toMain = new Intent(requireActivity(), MainActivity.class);
                            startActivity(toMain);
                        } else {
                            Toast.makeText(requireActivity(), "Login failed, please recheck the entered email and password!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        auth = FirebaseAuth.getInstance();
        toRegisterText = view.findViewById(R.id.toRegister_text);
        toRegisterText.setOnClickListener(toRegister);

        emailInput = view.findViewById(R.id.email_et);
        passwordInput = view.findViewById(R.id.password_et);
        login = view.findViewById(R.id.login_btn);
        login.setOnClickListener(loginAcc);
    }
}