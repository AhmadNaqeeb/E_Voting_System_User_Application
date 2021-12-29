package com.example.e_votingsystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;


import com.example.e_votingsystem.Candidates_Manage.CandidateDetails;
import com.example.e_votingsystem.Candidates_Manage.Candidates;
import com.example.e_votingsystem.Database.EVotingDBContract;
import com.example.e_votingsystem.Database.FirebaseDBHandler;
import com.example.e_votingsystem.Model.CandidateDataCallback;
import com.example.e_votingsystem.Model.Constituency;
import com.example.e_votingsystem.Model.Parties;
import com.example.e_votingsystem.View_Candidates_Model.View_All_Candidate;
import com.example.e_votingsystem.mapper.EVotingMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
//import com.example.e_votingsystem.View_Candidates_Model.View_All_Candidate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class VoterHomeActivity extends AppCompatActivity implements CandidateDataCallback {
    ViewPager viewPager;
    Timer timer;
    Handler handler;
    ImageView ivVoteNow, viewCandidates, ivAboutUs, ivUpdateProfile, ivResult, ivLogOut;
    public static List candidateList = new ArrayList();
    boolean isCandidateView = false;
    private EVotingMapper eVotingMapper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voterhome);
        viewPager = findViewById(R.id.viewpager);
        ivVoteNow = findViewById(R.id.ivVoteNow);
        viewCandidates = findViewById(R.id.viewCandidates);
        ivAboutUs = findViewById(R.id.ivAboutUs);
        ivResult = findViewById(R.id.ivResult);
        ivUpdateProfile = findViewById(R.id.ivUpdateProfile);
        ivLogOut = findViewById(R.id.ivLogOut);

        List<Integer> imageList = new ArrayList<>();
        eVotingMapper = new EVotingMapper();
        imageList.add(R.drawable.pti_flag);
        imageList.add(R.drawable.pmln_flag);
        imageList.add(R.drawable.ppp_flag);
        imageList.add(R.drawable.mqm_flag);
        imageList.add(R.drawable.ji_flag);
        imageList.add(R.drawable.pmlq_bg);
        imageList.add(R.drawable.tlp_bg);
        ViewPagerAdopter viewPagerAdopter = new ViewPagerAdopter(imageList);
        viewPager.setAdapter(viewPagerAdopter);


        timer = new Timer();
        handler = new Handler();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        int i = viewPager.getCurrentItem();

                        if (i==imageList.size()-1)
                        {
                            i=0;
                            viewPager.setCurrentItem(i, true);
                        }
                        else
                        {
                            i++;
                            viewPager.setCurrentItem(i ,true);
                        }
                    }
                });
            }
        },3000,3000);
    ivVoteNow.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (SignInActivity.loggedInUser.getVote().equalsIgnoreCase("NA") || SignInActivity.loggedInUser.getVote().equalsIgnoreCase("PARTIAL_VOTE_CASTED")){
                //Intent i = new Intent(VoterHomeActivity.this, View_Candidates.class);
                Intent i = new Intent(VoterHomeActivity.this, CustomSpinner.class);
                startActivity(i);
                finish();
            }else{
                isCandidateView = false;
                initCandidateData();
            }
        }
    });
        viewCandidates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isCandidateView = true;
                initCandidateData();
            }
        });

        ivAboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(VoterHomeActivity.this, aboutUS.class);
                startActivity(i);
            }
        });
        ivUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(VoterHomeActivity.this, updateProfile.class);
                startActivity(i);
            }
        });
        ivLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignInActivity.loggedInUser = null;
                Intent loginIntent = new Intent(VoterHomeActivity.this, SignInActivity.class);
                startActivity(loginIntent);
                finish();
            }
        });
        ivResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isCandidateView = false;
                initCandidateData();
            }
        });
    }

    private void initCandidateData(){
        Log.d("Voter_Home_Act","Init Data");
        FirebaseDBHandler.mCandidateDataCallback = this;
        FirebaseDBHandler.getCandidateNodeAllChildren("0", EVotingDBContract.CANDIDATE_NODE);
    }

    @Override
    public void onCandidateDataRetrieved(List<Candidates> candidateDataList, String nodeName) {

        if (candidateDataList != null && candidateDataList.size() > 0){

            List<CandidateDetails> candidateDetails = new ArrayList<>();
            for (Candidates candidates : candidateDataList){
                CandidateDetails newCandidateDetails = new CandidateDetails();
                newCandidateDetails.setId(candidates.getId());
                newCandidateDetails.setName(candidates.getName());
                newCandidateDetails.setProvince(candidates.getProvince());

                ObjectMapper objectMapper = new ObjectMapper();
                try {
                    Parties parties = objectMapper.readValue(candidates.getParty(), Parties.class);
                    Constituency constituency = objectMapper.readValue(candidates.getConstituency(), Constituency.class);
                    newCandidateDetails.setConstituency(constituency);
                    newCandidateDetails.setParty(parties);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                candidateDetails.add(newCandidateDetails);
            } // EOF for loop to convert candidates details...

            Log.d("View_Candidates: ", String.valueOf(candidateDetails.size()));
            candidateList.clear();
            candidateList.addAll(candidateDetails);
            if (isCandidateView){
                Intent i = new Intent(VoterHomeActivity.this, View_All_Candidate.class);
                startActivity(i);
            } else if (!isCandidateView){
                CandidateDetails newCandidateDetails = eVotingMapper.mapCandidateJSONToModel(SignInActivity.loggedInUser.getMnaCandidate());
                Intent resultActivityIntent = new Intent(VoterHomeActivity.this, Voting_Results_Activity.class);
                resultActivityIntent.putExtra("PartyName",newCandidateDetails.getParty().getName());
                startActivity(resultActivityIntent);
            }
        }
    }
}