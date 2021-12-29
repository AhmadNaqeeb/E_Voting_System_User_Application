package com.example.e_votingsystem.Model;


import com.example.e_votingsystem.Candidates_Manage.Candidates;

import java.util.List;

public interface CandidateDataCallback {
    public void onCandidateDataRetrieved(List<Candidates> candidateDataList , String nodeName);
}
