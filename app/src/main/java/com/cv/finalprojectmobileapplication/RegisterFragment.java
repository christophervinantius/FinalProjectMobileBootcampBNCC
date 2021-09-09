package com.cv.finalprojectmobileapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.service.autofill.FieldClassification;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterFragment extends Fragment {

    ImageView toLoginBtn;
    EditText nameInput, emailInput, passwordInput, confirmPasswordInput;
    String name, email, password, confirmPassword, image, desc;
    Button register;
    SharedPreferences data;

    private FirebaseAuth auth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("imageData");

    View.OnClickListener toLogin = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            getParentFragmentManager().popBackStack();
        }
    };

    View.OnClickListener registerAcc = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            name = nameInput.getText().toString();
            email = emailInput.getText().toString();
            password = passwordInput.getText().toString();
            confirmPassword = confirmPasswordInput.getText().toString();
            boolean validAcc;
            String validEmail = "^(.+)@(.+).com$";
            Pattern pattern = Pattern.compile(validEmail);
            Matcher matcher = pattern.matcher(email);
            if(name.length() < 5){
                validAcc = false;
                Toast.makeText(getActivity(), "Name must have 5 or more characters!"
                ,Toast.LENGTH_SHORT).show();
            }else{
                if(!matcher.matches()){
                    validAcc = false;
                    Toast.makeText(getActivity(), "Email must have @ and end with .com!"
                            ,Toast.LENGTH_SHORT).show();
                }else{
                     if(password.length() < 8){
                         validAcc = false;
                         Toast.makeText(getActivity(), "Password must have 8 or more characters!",
                                 Toast.LENGTH_SHORT).show();
                        }else{
                            if(!confirmPassword.equals(password)){
                                validAcc = false;
                                Toast.makeText(getActivity(), "Confirmation password must match the password!"
                                        ,Toast.LENGTH_SHORT).show();
                            }else{
                                validAcc = true;
                            }
                        }
                    }
                }

            if(validAcc){
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(requireActivity(), new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()) {
                                    data = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor edit = data.edit();
                                    edit.putString("name", name);
                                    edit.putString("email", email);
                                    edit.putString("password", password);
                                    edit.putString("image", image);
                                    edit.putString("desc", desc);
                                    edit.apply();
                                    Toast.makeText(requireActivity(), "Account successfully registered!",
                                            Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(requireActivity(), "Registration failed, please try again with different email!",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }else{
                return;
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        auth = FirebaseAuth.getInstance();
        toLoginBtn = view.findViewById(R.id.toLogin_btn);
        toLoginBtn.setOnClickListener(toLogin);

        nameInput = view.findViewById(R.id.name_et);
        emailInput = view.findViewById(R.id.email_et);
        passwordInput = view.findViewById(R.id.password_et);
        confirmPasswordInput = view.findViewById(R.id.confirmPassword_et);
        register = view.findViewById(R.id.register_btn);
        register.setOnClickListener(registerAcc);
    }
}