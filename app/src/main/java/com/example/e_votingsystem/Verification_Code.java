package com.example.e_votingsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class Verification_Code extends AppCompatActivity {

    EditText et_OTP;
    Button btn_VerifyOTP;

    String mobile_no;
    FirebaseAuth mAuth;
    String otpid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification__code);

        mobile_no = getIntent().getStringExtra("mobile").toString().trim();
        Toast.makeText(getApplicationContext(),mobile_no,Toast.LENGTH_LONG).show();
        et_OTP = findViewById(R.id.et_OTP);
        btn_VerifyOTP = findViewById(R.id.btn_Update);

        mAuth = FirebaseAuth.getInstance();
        mAuth.getFirebaseAuthSettings().setAppVerificationDisabledForTesting(false);
        otp_creation();

        btn_VerifyOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_OTP.getText().toString().isEmpty())
                {
                    Toast.makeText(Verification_Code.this, "Please Enter Your Code", Toast.LENGTH_SHORT).show();
                }
                else if (et_OTP.getText().toString().length() != 6)
                {
                    Toast.makeText(Verification_Code.this, "Enter 6 digits OTP Code", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(otpid, et_OTP.getText().toString());
                    signInWithPhoneAuthCredential(credential);
                }
            }
        });
    }

    private void otp_creation()
    {
        //Toast.makeText(this, mobile_no, Toast.LENGTH_LONG).show();
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(mobile_no)       // Phone number to verify
                        .setTimeout(10L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken)
                            {
                                otpid = s;
                            }

                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                signInWithPhoneAuthCredential(phoneAuthCredential);
                            }
                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                Log.i("Verification Failure: ",e.getMessage());
                                Toast.makeText(Verification_Code.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void onCodeAutoRetrievalTimeOut(@NonNull String s) {

                            }
                        })          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(Verification_Code.this, "Account Registered Sucessfully. Now, Login Your Account.", Toast.LENGTH_LONG).show();
                            setResult(RESULT_OK);
                            finish();
                        }
                        else {
                             Toast.makeText(Verification_Code.this, "Sign in Code Error : "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}