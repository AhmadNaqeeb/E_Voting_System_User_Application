package com.example.e_votingsystem.Candidates_Manage;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_votingsystem.BioMetricAuthenticateActivity;
import com.example.e_votingsystem.Model.Constituency;
import com.example.e_votingsystem.Database.EVotingDBContract;
import com.example.e_votingsystem.Database.FirebaseDBHandler;
import com.example.e_votingsystem.Model.CandidateDataCallback;
import com.example.e_votingsystem.Model.Parties;
import com.example.e_votingsystem.R;
import com.example.e_votingsystem.SignInActivity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class View_Candidates extends AppCompatActivity implements CandidateDataCallback, CandidatesAdopter.OnItemClick {
    private TextView tvVoterAddress ;
    private RecyclerView rvCandidates;
    private CandidatesAdopter candidatesAdopter;
    private List<CandidateDetails> allRegisteredCandidatesList = new ArrayList<>();
    private String constituencyParent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__candidates);

        Intent constituencyParentIntent = getIntent();
        constituencyParent = constituencyParentIntent.getStringExtra("constituencyParent");
        tvVoterAddress = findViewById(R.id.tvVoterAddress);
        rvCandidates = findViewById(R.id.rvCandidates);
        rvCandidates.setLayoutManager(new LinearLayoutManager(this));
        initCandidateData();
    }

    private void initCandidateData(){
        Log.d("View_Candidates_Act","Init Data");
        FirebaseDBHandler.mCandidateDataCallback = this;
        FirebaseDBHandler.getCandidateNodeAllChildren("0",EVotingDBContract.CANDIDATE_NODE);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onCandidateDataRetrieved(List<Candidates> candidateDataList, String nodeName) {

        if (candidateDataList != null && candidateDataList.size() > 0){

            List<CandidateDetails> candidateDetails = new ArrayList<>();
            String voterProvince = SignInActivity.loggedInUser.getProvince();
            String voterCity = SignInActivity.loggedInUser.getCity();
            String voterArea = SignInActivity.loggedInUser.getArea();

            for (Candidates candidates : candidateDataList){
                CandidateDetails newCandidateDetails = new CandidateDetails();
                ObjectMapper objectMapper = new ObjectMapper();
                try {
                    Parties parties = objectMapper.readValue(candidates.getParty(), Parties.class);
                    Constituency constituency = objectMapper.readValue(candidates.getConstituency(), Constituency.class);
                    newCandidateDetails.setConstituency(constituency);
                    newCandidateDetails.setParty(parties);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (newCandidateDetails.getConstituency().getParent().equalsIgnoreCase(constituencyParent) && candidates.getProvince().equalsIgnoreCase(voterProvince)
                     && candidates.getCity().equalsIgnoreCase(voterCity) && candidates.getArea().equalsIgnoreCase(voterArea)
                ){
                    newCandidateDetails.setId(candidates.getId());
                    newCandidateDetails.setName(candidates.getName());
                    newCandidateDetails.setCNIC(candidates.getCNIC());
                    newCandidateDetails.setProvince(candidates.getProvince());
                    newCandidateDetails.setCity(candidates.getCity());
                    newCandidateDetails.setArea(candidates.getArea());
                    candidateDetails.add(newCandidateDetails);
                }
            }
            Log.d("View_Candidates: ", String.valueOf(candidateDetails.size()));
            if (candidateDetails.size() > 0){
                tvVoterAddress.setText(voterArea + "," + voterCity + "," + voterProvince);
                candidatesAdopter = new CandidatesAdopter(candidateDetails, this, this);
                rvCandidates.setAdapter(candidatesAdopter);
            }else{
                String msg = "There is no candidate registered with the EVoting System for following location.\n" + (voterArea + "," + voterCity + "," + voterProvince) + " \nPlease Contact with EVoting System Administrator";
                tvVoterAddress.setText(msg);
            }
        }

    }

    @Override
    public void onClick(CandidateDetails candidateDetails) {
        verifyVoterFingerPrints(candidateDetails);
    }

    private void verifyVoterFingerPrints(CandidateDetails candidateDetails){
         String partyName = candidateDetails.getParty().getName();
         if (partyName != null && !partyName.isEmpty()){
             final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
             alertDialogBuilder.setTitle("Are you sure?");
             alertDialogBuilder.setMessage("You want to vote for party " + partyName  + "?");
             alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                 @Override
                 public void onClick(DialogInterface dialog, int which) {
                     try {
                         String candidateJson = new ObjectMapper().writeValueAsString(candidateDetails);
                         Intent biometricIntent = new Intent(View_Candidates.this, BioMetricAuthenticateActivity.class);
                         biometricIntent.putExtra("PartyName",partyName);
                         biometricIntent.putExtra("candidate",candidateJson);
                         biometricIntent.putExtra("constituencyParent",candidateDetails.getConstituency().getParent());
                         startActivity(biometricIntent);
                         finish();
                     } catch (JsonProcessingException e) {
                         e.printStackTrace();
                     }

                 }
             });
             alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                 @Override
                 public void onClick(DialogInterface dialog, int which) {
                     dialog.cancel();
                 }
             });
             alertDialogBuilder.show();
         }else{
             Toast.makeText(this, "Please select party first", Toast.LENGTH_SHORT).show();
         }
    }
}