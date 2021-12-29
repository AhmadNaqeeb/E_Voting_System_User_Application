package com.example.e_votingsystem.View_Candidates_Model;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.e_votingsystem.BioMetricAuthenticateActivity;
import com.example.e_votingsystem.Candidates_Manage.CandidateDetails;
import com.example.e_votingsystem.Candidates_Manage.Candidates;
import com.example.e_votingsystem.Database.EVotingDBContract;
import com.example.e_votingsystem.Database.FirebaseDBHandler;
import com.example.e_votingsystem.Model.CandidateDataCallback;
import com.example.e_votingsystem.Model.Constituency;
import com.example.e_votingsystem.Model.Parties;
import com.example.e_votingsystem.R;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class View_All_Candidate extends AppCompatActivity{
    private RecyclerView rvCandidates;
    private CandidateAdopterView candidatesAdopterView;
    private List<CandidateDetails> allRegisteredCandidatesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__all__candidate);

        rvCandidates = findViewById(R.id.rvCandidates);
        rvCandidates.setLayoutManager(new LinearLayoutManager(this));
        candidatesAdopterView = new CandidateAdopterView( this);
        rvCandidates.setAdapter(candidatesAdopterView);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
