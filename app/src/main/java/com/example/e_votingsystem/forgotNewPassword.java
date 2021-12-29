package com.example.e_votingsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.e_votingsystem.Database.EVotingDBContract;
import com.example.e_votingsystem.Database.FirebaseDBHandler;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class forgotNewPassword extends AppCompatActivity {

    EditText etR_Password, etR_Conf_Password;
    Button btn_ResetPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_new_password);

       // String mobile = getIntent().getStringExtra("mobile");

        etR_Password = findViewById(R.id.etR_Password);
        etR_Conf_Password = findViewById(R.id.etR_Conf_Password);
        btn_ResetPassword = findViewById(R.id.btn_ResetPassword);

        btn_ResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Update Voter status into Firebase database
                forgotPassword.forgotPasswordUser.setPassword(etR_Password.getText().toString());
                String childNodePosition = String.valueOf(forgotPassword.forgotPasswordUser.getId());

                FirebaseDBHandler.update("1", EVotingDBContract.VOTER_NODE,forgotPassword.forgotPasswordUser,childNodePosition);

                Toast.makeText(getApplicationContext(),"Password changed", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(forgotNewPassword.this, SignInActivity.class));
                finish();

            }
        });
    }
}