package com.example.e_votingsystem;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_votingsystem.Database.EVotingDBContract;
import com.example.e_votingsystem.Database.FirebaseDBHandler;
import com.example.e_votingsystem.Model.DataRetrievalCallback;
import com.example.e_votingsystem.Model.VoterDataCallback;
import com.example.e_votingsystem.Model.VoterModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SignInActivity extends AppCompatActivity implements VoterDataCallback {

    TextView tvRegister, tvForgot;
    Button btnLogin;
    EditText et_CNIC, et_Password;
    public static VoterModel loggedInUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin_activity);

        init();

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignInActivity.this, RegisterAccountActivity.class);
                startActivity(i);
            }
        });
        tvForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignInActivity.this, forgotPassword.class);
                startActivity(i);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(validateCNIC() && validatePassword())
               {
                   isAuthenticUser();
               }
            }
        });
    }
    private Boolean validateCNIC()
    {
        String cnic = et_CNIC.getText().toString().trim();
        if (cnic.isEmpty())
        {
            et_CNIC.setError("Field can't be empty");
            return false;
        }
        else
        {
            et_CNIC.setError(null);
            return true;
        }
    }

    private Boolean validatePassword()
    {
        String password = et_Password.getText().toString().trim();
        if (password.isEmpty())
        {
            et_Password.setError("Field can't be empty");
            return false;
        }
        else
        {
            et_Password.setError(null);
            return true;
        }
    }

    private void isAuthenticUser() {
        FirebaseDBHandler.mVoterDataCallback = this;
        FirebaseDBHandler.getVoterNodeAllChildren("1", EVotingDBContract.VOTER_NODE);
    }

    private void init() {
        tvRegister = findViewById(R.id.tvRegister);
        tvForgot = findViewById(R.id.tvForgot);
        btnLogin = findViewById(R.id.btnLogin);
        et_CNIC = findViewById(R.id.et_CNIC);
        et_Password = findViewById(R.id.et_Password);
    }

    @Override
    public void onVoterDataRetrieved(List<VoterModel> voterDataList, String nodeName) {

        if (voterDataList != null && voterDataList.size() > 0){
            String userEnteredCNIC = et_CNIC.getText().toString().trim();
            String userEnteredPassword = et_Password.getText().toString().trim();
            boolean isFound = false ;
            for (int i = 0 ; i < voterDataList.size(); i++)
            {
                VoterModel voterModel = voterDataList.get(i);
                if (
                        voterModel.getCNIC().equalsIgnoreCase(userEnteredCNIC) &&
                                voterModel.getPassword().equalsIgnoreCase(userEnteredPassword)
                ){
                    loggedInUser = voterModel;
                    Intent homeIntent = new Intent(SignInActivity.this, VoterHomeActivity.class);
                    startActivity(homeIntent);
                    finish();
                    isFound = true;
                    break;
                }
            }
            if (!isFound){
                Toast.makeText(getApplicationContext(), "Invalid credentials. Please enter valid cnic and password", Toast.LENGTH_LONG).show();
            }
        }else
        {
            Toast.makeText(getApplicationContext(), "This user is not registered", Toast.LENGTH_LONG).show();
        }

    }
}