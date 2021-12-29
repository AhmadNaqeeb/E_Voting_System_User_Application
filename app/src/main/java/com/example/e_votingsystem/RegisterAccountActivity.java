package com.example.e_votingsystem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.e_votingsystem.Database.EVotingDBContract;
import com.example.e_votingsystem.Database.FirebaseDBHandler;
import com.example.e_votingsystem.Model.DataRetrievalCallback;
import com.example.e_votingsystem.Model.VoterDataCallback;
import com.example.e_votingsystem.Model.VoterModel;
import com.google.firebase.auth.FirebaseAuth;
import com.hbb20.CountryCodePicker;
import com.jaredrummler.materialspinner.MaterialSpinner;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

public class RegisterAccountActivity extends AppCompatActivity implements VoterDataCallback {

    EditText etR_Name, etR_Age, etR_CNIC, etR_Mobile, etR_Password, etR_Conf_Password;
    Button btn_Register;

    MaterialSpinner VoterCitySpinner, VoterAreaSpinner, VoterProvinceSpinner;
    String SelectedProvince = "", SelectedCity;
    String SelectedArea;
    String r_mobile,a;

    public static String [] cities = {"Select Your City", "Lahore", "Islamabad", "Karachi"};

    public static String [] areas = {"Select Area", "DHA Phase I", "DHA Phase II", "DHA Phase III", "Gulberg I","Gulberg II", "Sector E-11", "Sector G-13"};

    public static String [] province = {"Select Your Province", "Punjab", "Sindh", "KPK", "Balochistan", "Gilgit Baltistan"};

    CountryCodePicker ccp;

    private FirebaseAuth mAuth;
    private static int registeredVoterCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeraccount);

        init();

        VoterCitySpinner.setItems(cities);

        VoterCitySpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                SelectedCity = item.toString();
                Toast.makeText(RegisterAccountActivity.this, item.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        VoterAreaSpinner.setItems(areas);

        VoterAreaSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                SelectedArea = item.toString();
                Toast.makeText(RegisterAccountActivity.this, item.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        VoterProvinceSpinner.setItems(province);

        VoterProvinceSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                SelectedProvince = item.toString();
                Toast.makeText(RegisterAccountActivity.this, item.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        btn_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String r_name = etR_Name.getText().toString().trim();
                String r_age = etR_Age.getText().toString().trim();
                String r_cnic = etR_CNIC.getText().toString().trim();
                r_mobile = etR_Mobile.getText().toString().trim();
                String r_password = etR_Password.getText().toString().trim();
                String r_conf_password = etR_Conf_Password.getText().toString().trim();
                boolean isError = false;
                a = ccp.getFullNumberWithPlus().toString().trim()+ r_mobile;

                if (r_name.isEmpty()) {
                    etR_Name.setError("Please Enter Name");
                    isError = true;
                }
                if (r_age.isEmpty()) {
                    etR_Age.setError("Please Enter Age");
                    isError = true;
                }
                if (r_cnic.length() < 13) {
                    etR_CNIC.setError("Please Enter 13 digits valid CNIC");
                    isError = true;
                }
                if (r_cnic.length() > 13) {
                    etR_CNIC.setError("Please Enter 13 digits valid CNIC");
                    isError = true;
                }
                if (r_cnic.isEmpty()) {
                    etR_CNIC.setError("Please Enter CNIC");
                    isError = true;
                }
                if (r_mobile.isEmpty()) {
                    etR_Mobile.setError("Please Enter Mobile Number");
                    isError = true;
                }
                if (r_password.length() < 8) {
                    etR_Password.setError("Password must be more than 8 characters");
                    isError = true;
                }
                if (r_password.isEmpty()) {
                    etR_Password.setError("Please Enter Password");
                    isError = true;
                }
                if (!r_conf_password.equalsIgnoreCase(r_password)) {
                    etR_Conf_Password.setError("Password did not match");

                    isError = true;
                }
                if (r_conf_password.equalsIgnoreCase(r_password)) {
                    etR_Conf_Password.setError(null);
                    if (!isError) {
                        validateCNIC();
                    } else {
                        Toast.makeText(getApplicationContext(), "Invalid Input", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    private void init() {
        etR_Name = findViewById(R.id.etR_Name);
        etR_Age = findViewById(R.id.etU_Mobile);
        etR_CNIC = findViewById(R.id.etR_CNIC);
        etR_Mobile = findViewById(R.id.etR_Mobile);
        VoterCitySpinner = findViewById(R.id.VoterCitySpinner);
        VoterAreaSpinner = findViewById(R.id.VoterAreaSpinner);
        VoterProvinceSpinner = findViewById(R.id.VoterProvinceSpinner);
        etR_Password = findViewById(R.id.etR_Password);
        etR_Conf_Password = findViewById(R.id.etR_Conf_Password);
        btn_Register = findViewById(R.id.btn_Register);
        ccp = findViewById(R.id.ccp);
        mAuth = FirebaseAuth.getInstance();
        ccp.registerCarrierNumberEditText(etR_Mobile);
    }

    private void validateCNIC() {
        FirebaseDBHandler.mVoterDataCallback = this;
        FirebaseDBHandler.getVoterNodeAllChildren("1", EVotingDBContract.VOTER_NODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data1) {
        super.onActivityResult(requestCode, resultCode, data1);

        if (requestCode == 1234) {
            if (resultCode == RESULT_OK) {
                SaveVoterRecord();
            }
        }
    }

    private void SaveVoterRecord() {

        String Name = etR_Name.getText().toString().trim();
        String Age = etR_Age.getText().toString().trim();
        String CNIC = etR_CNIC.getText().toString().trim();
        String Mobile =  ccp.getFullNumberWithPlus().toString().trim();
        String Password = etR_Password.getText().toString().trim();
        String Vote = "NA";
        String mnaCandidate = "NA";
        String mpaCandidate = "NA";
        VoterModel vm = new VoterModel(Name, Age, CNIC, Mobile, SelectedCity, SelectedArea, SelectedProvince, Password, Vote, mnaCandidate, mpaCandidate);

        vm.setId(registeredVoterCount);
        vm.setMpaCandidate(mpaCandidate);
        FirebaseDBHandler.insert("1", EVotingDBContract.VOTER_NODE, vm, String.valueOf(registeredVoterCount));
        registeredVoterCount++;
        Toast.makeText(getApplicationContext(), "You are Registered With Online E Voting System Successfully", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(RegisterAccountActivity.this, SignInActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onVoterDataRetrieved(List<VoterModel> voterDataList, String nodeName) {
        if (voterDataList != null && voterDataList.size() > 0) {
            String CNIC = etR_CNIC.getText().toString().trim();
            boolean isError = false;
            for (VoterModel voterModel : voterDataList) {
                if (voterModel.getCNIC().equalsIgnoreCase(CNIC)) {
                    Toast.makeText(this, "Duplicate CNIC Number. Please Enter Unique CNIC number.", Toast.LENGTH_LONG).show();
                    isError = true;
                    break;
                }
            }

            if (!isError) {
                if (registeredVoterCount == 0) {  // check in case of application gets restart or reinstall....
                    registeredVoterCount = voterDataList.get(voterDataList.size() - 1).getId();
                    registeredVoterCount++;
                }
                Intent intent = new Intent(RegisterAccountActivity.this, Verification_Code.class);
                intent.putExtra("mobile", ccp.getFullNumberWithPlus().toString().trim());
                startActivityForResult(intent, 1234);
            }

        } else {
            registeredVoterCount = 0;
            Intent intent = new Intent(RegisterAccountActivity.this, Verification_Code.class);
            intent.putExtra("mobile", ccp.getFullNumberWithPlus().toString().trim());
            startActivityForResult(intent, 1234);
        }
    }
}