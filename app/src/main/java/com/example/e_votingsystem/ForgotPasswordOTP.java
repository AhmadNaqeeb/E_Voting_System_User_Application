package com.example.e_votingsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public class ForgotPasswordOTP extends AppCompatActivity {

    EditText et_SubmitOTPCode;
    Button btn_VerifyOTP;
    TextView tv_MobileNumber;

    String getOtpBackend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password_o_t_p);

        et_SubmitOTPCode = findViewById(R.id.et_SubmitOTPCode);
        btn_VerifyOTP = findViewById(R.id.btn_VerifyOTP);
        tv_MobileNumber = findViewById(R.id.tv_MobileNumber);
        String mobile_no = "+"+getIntent().getStringExtra("mobile");
        tv_MobileNumber.setText(mobile_no);

        getOtpBackend = getIntent().getStringExtra("backendOTP");

        btn_VerifyOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!et_SubmitOTPCode.getText().toString().trim().isEmpty())
                {
                    String enterCodeOtp = et_SubmitOTPCode.getText().toString();

                    if (getOtpBackend != null)
                    {
                        PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(getOtpBackend, enterCodeOtp);

                        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful())
                                        {
                                            Intent intent = new Intent(getApplicationContext(), forgotNewPassword.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                           // intent.putExtra("mobile", mobile_no);
                                            startActivity(intent);
                                        }else
                                        {
                                            Toast.makeText(ForgotPasswordOTP.this, "Please Enter Correct OTP", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }else
                    {
                         Toast.makeText(ForgotPasswordOTP.this, "Please check Internet Connection", Toast.LENGTH_SHORT).show();
                    }

                }
                else
                {
                    Toast.makeText(ForgotPasswordOTP.this, "Please Enter Valid Code", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}