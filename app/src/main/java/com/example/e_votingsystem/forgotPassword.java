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
import com.example.e_votingsystem.Model.VoterDataCallback;
import com.example.e_votingsystem.Model.VoterModel;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class forgotPassword extends AppCompatActivity implements VoterDataCallback {

    public static VoterModel forgotPasswordUser;
    EditText enter_CNIC;
    Button btn_GETOTP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        enter_CNIC = findViewById(R.id.enter_MobileNo);
        btn_GETOTP = findViewById(R.id.btn_VerifyOTP);

        btn_GETOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!enter_CNIC.getText().toString().trim().isEmpty()) {

                    PhoneProvider();
                }
                else
                {
                    Toast.makeText(forgotPassword.this, "Enter CNIC Number", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onVoterDataRetrieved(List<VoterModel> voterDataList, String nodeName) {

        if (voterDataList !=null && voterDataList.size() > 0)
        {
            for (VoterModel vm: voterDataList)
            {
                if (vm.getCNIC().equalsIgnoreCase(enter_CNIC.getText().toString()))
                {
                    forgotPasswordUser = vm;
                    String mobileNo = vm.getMobile();

                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                            "+"+mobileNo, 30, TimeUnit.SECONDS, forgotPassword.this,
                            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                @Override
                                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                                }

                                @Override
                                public void onVerificationFailed(@NonNull FirebaseException e) {
                                    Toast.makeText(forgotPassword.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                                }

                                @Override
                                public void onCodeSent(@NonNull String backendOTP, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {

                                    Intent intent = new Intent(forgotPassword.this, ForgotPasswordOTP.class);
                                    intent.putExtra("mobile", mobileNo);
                                    intent.putExtra("backendOTP", backendOTP);
                                    startActivity(intent);
                                }
                            });
                    break;
                }
            }
        }else
        {
            Toast.makeText(this, "This CNIC Number not found", Toast.LENGTH_SHORT).show();
        }

    }
    public void PhoneProvider()
    {
        FirebaseDBHandler.mVoterDataCallback = this;
        FirebaseDBHandler.getVoterNodeAllChildren("1", EVotingDBContract.VOTER_NODE);
    }
}