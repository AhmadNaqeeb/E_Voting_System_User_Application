package com.example.e_votingsystem.mapper;

import android.content.Intent;
import android.util.Log;

import com.example.e_votingsystem.Candidates_Manage.CandidateDetails;
import com.example.e_votingsystem.Candidates_Manage.Candidates;
import com.example.e_votingsystem.Model.Constituency;
import com.example.e_votingsystem.Model.Parties;
import com.example.e_votingsystem.SignInActivity;
import com.example.e_votingsystem.VoterHomeActivity;
import com.example.e_votingsystem.Voting_Results_Activity;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

public class EVotingMapper {

    public CandidateDetails mapCandidateJSONToModel(String  candidateJSON){
        ObjectMapper objectMapper = new ObjectMapper();
        CandidateDetails newCandidateDetails = new CandidateDetails();
        try {
            newCandidateDetails = objectMapper.readValue(candidateJSON,CandidateDetails.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newCandidateDetails;
    }
}
