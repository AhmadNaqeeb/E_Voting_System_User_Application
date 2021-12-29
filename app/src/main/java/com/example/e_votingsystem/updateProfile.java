package com.example.e_votingsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.e_votingsystem.Database.EVotingDBContract;
import com.example.e_votingsystem.Database.FirebaseDBHandler;
import com.example.e_votingsystem.Model.VoterModel;
import com.jaredrummler.materialspinner.MaterialSpinner;

public class updateProfile extends AppCompatActivity {

    EditText et_Name, et_Mobile, et_Age, et_Password;
    Button btn_Update;

    MaterialSpinner UpdateCitySpinner, UpdateAreaSpinner, UpdateProvinceSpinner;

    String USERNAME, AGE, MOBILE, PASSWORD;

    String CITY, AREA, PROVINCE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        et_Name = findViewById(R.id.etU_Name);
        et_Mobile = findViewById(R.id.etU_Mobile);
        et_Age = findViewById(R.id.etU_Age);
        UpdateCitySpinner = findViewById(R.id.UpdateCitySpinner);
        UpdateAreaSpinner = findViewById(R.id.UpdateAreaSpinner);
        UpdateProvinceSpinner = findViewById(R.id.UpdateProvinceSpinner);
        et_Password = findViewById(R.id.etU_Password);
        btn_Update = findViewById(R.id.btn_Update);
        UpdateCitySpinner.setItems(RegisterAccountActivity.cities);

        //Show All Data from DB
        showAllUserData();

    }

    private void showAllUserData() {

        USERNAME = SignInActivity.loggedInUser.getName();
        AGE = SignInActivity.loggedInUser.getAge();
        MOBILE = SignInActivity.loggedInUser.getMobile();
        CITY = SignInActivity.loggedInUser.getCity();
        AREA = SignInActivity.loggedInUser.getArea();
        PROVINCE = SignInActivity.loggedInUser.getProvince();
        PASSWORD = SignInActivity.loggedInUser.getPassword();


        et_Name.setText(USERNAME);
        et_Age.setText(AGE);
        et_Mobile.setText(MOBILE);
        UpdateCitySpinner.setItems(RegisterAccountActivity.cities);
        UpdateAreaSpinner.setItems(RegisterAccountActivity.areas);
        UpdateProvinceSpinner.setItems(RegisterAccountActivity.province);
        et_Password.setText(PASSWORD);
    }

    public void setBtn_Update(View view){
        String Name = et_Name.getText().toString().trim();
        String Age = et_Age.getText().toString().trim();
        String Mobile = et_Mobile.getText().toString().trim();
        String Password = et_Password.getText().toString().trim();
        String City = UpdateCitySpinner.getText().toString(), Area = UpdateAreaSpinner.getText().toString(), Province = UpdateProvinceSpinner.getText().toString();
        String CNIC = SignInActivity.loggedInUser.getCNIC();
        String Vote = SignInActivity.loggedInUser.getVote();
        String candidate = SignInActivity.loggedInUser.getMnaCandidate();
        String childNodePosition = String.valueOf(SignInActivity.loggedInUser.getId());

        VoterModel vm = new VoterModel(Name,Age,CNIC,Mobile,City,Area,Province,Password,Vote,candidate, SignInActivity.loggedInUser.getMpaCandidate());
        vm.setId(SignInActivity.loggedInUser.getId());

        // Update Voter status into Firebase database
        FirebaseDBHandler.update("1", EVotingDBContract.VOTER_NODE,vm,childNodePosition);
    }
}