package com.example.e_votingsystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.e_votingsystem.Candidates_Manage.View_Candidates;
import com.example.e_votingsystem.Database.FirebaseDBHandler;
import com.example.e_votingsystem.Model.VoterModel;
import com.jaredrummler.materialspinner.MaterialSpinner;

public class CustomSpinner extends AppCompatActivity {

    MaterialSpinner spinner;
    Button btn_Back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_spinner);

        btn_Back = findViewById(R.id.btn_Back);
        spinner = findViewById(R.id.spinner);
        String currentVoterVoteCast= "";
        if (SignInActivity.loggedInUser.getMnaCandidate().equalsIgnoreCase("NA")){
            currentVoterVoteCast = "MNA";
        }else if (SignInActivity.loggedInUser.getMpaCandidate().equalsIgnoreCase("NA")){
            currentVoterVoteCast = "MPA";
        }

        String[] parents;
        if (!SignInActivity.loggedInUser.getVote().equalsIgnoreCase("NA")){
            parents = new String[]{"Select Your Vote", currentVoterVoteCast};
        }else{
            parents = new String[]{"Select Your Vote", "MNA", "MPA"};
        }
        spinner.setItems(parents);

        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                Toast.makeText(CustomSpinner.this, item.toString(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CustomSpinner.this, View_Candidates.class);
                intent.putExtra("constituencyParent",item.toString());
                startActivity(intent);
            }
        });
        btn_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomSpinner.this, VoterHomeActivity.class);
                startActivity(intent);
            }
        });
    }
}