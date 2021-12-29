package com.example.e_votingsystem.Model;

import java.util.List;

public interface VoterDataCallback {
    public void onVoterDataRetrieved(List<VoterModel> voterDataList , String nodeName);
}
