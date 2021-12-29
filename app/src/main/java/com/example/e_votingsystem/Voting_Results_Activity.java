package com.example.e_votingsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.e_votingsystem.Candidates_Manage.CandidateDetails;
import com.example.e_votingsystem.Database.EVotingDBContract;
import com.example.e_votingsystem.Database.FirebaseDBHandler;
import com.example.e_votingsystem.Model.Result;
import com.example.e_votingsystem.Model.VoterDataCallback;
import com.example.e_votingsystem.Model.VoterModel;
import com.example.e_votingsystem.mapper.EVotingMapper;

import java.util.ArrayList;
import java.util.List;

public class Voting_Results_Activity extends AppCompatActivity implements VoterDataCallback {

    TextView tvResult;
    ImageView btnBack;
    EVotingMapper eVotingMapper;
    List<Result> resultList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voting__results_);

        tvResult = findViewById(R.id.tvResult);
        btnBack = findViewById(R.id.btnBack);
        eVotingMapper = new EVotingMapper();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Voting_Results_Activity.this, VoterHomeActivity.class);
                startActivity(intent);
            }
        });
        Intent resultIntent = getIntent();
        String resultTxt = "Your Vote has been casted to " +
                resultIntent.getStringExtra("PartyName");
        tvResult.setText(resultTxt);
        resultList = new ArrayList<>();
        initResultData();
    }

    private void initResultData() {
        FirebaseDBHandler.mVoterDataCallback = this;
        FirebaseDBHandler.getVoterNodeAllChildren("1", EVotingDBContract.VOTER_NODE);
    }

    @Override
    public void onVoterDataRetrieved(List<VoterModel> voterDataList, String nodeName) {

        if (voterDataList != null && voterDataList.size() > 0) {
            for (VoterModel vm : voterDataList) {
                if (!(vm.getVote().equalsIgnoreCase("NA"))) {
                    CandidateDetails candidateDetails = new CandidateDetails();
                    if ((vm.getVote().equalsIgnoreCase("COMPLETE"))){
                        candidateDetails = eVotingMapper.mapCandidateJSONToModel(vm.getMpaCandidate());
                        countVote(candidateDetails);
                        candidateDetails = eVotingMapper.mapCandidateJSONToModel(vm.getMnaCandidate());
                        countVote(candidateDetails);
                    } else if (!(vm.getMpaCandidate().equalsIgnoreCase("NA"))){
                        candidateDetails = eVotingMapper.mapCandidateJSONToModel(vm.getMpaCandidate());
                    }else if (!(vm.getMnaCandidate().equalsIgnoreCase("NA"))){
                        candidateDetails = eVotingMapper.mapCandidateJSONToModel(vm.getMnaCandidate());
                    }

                    if (!(vm.getVote().equalsIgnoreCase("COMPLETE"))){
                        int index = searchInResultList(candidateDetails.getParty().getId(), candidateDetails.getConstituency().getId());
                        if (index != -1) {
                            int voteCount = resultList.get(index).getVoteCount();
                            voteCount++;
                            resultList.get(index).setVoteCount(voteCount);
                        } else {
                            Result newResult = new Result(candidateDetails.getParty().getId(), candidateDetails.getParty().getName(),
                                    candidateDetails.getConstituency().getId(), candidateDetails.getConstituency().getName(), candidateDetails.getName(), 1);
                            resultList.add(newResult);
                        }
                    }
                }
            } // EOF for loop iterate over all voters
            bindResults();
        }
    }

    private void countVote(CandidateDetails candidateDetails){
        int index = searchInResultList(candidateDetails.getParty().getId(), candidateDetails.getConstituency().getId());
        if (index != -1) {
            int voteCount = resultList.get(index).getVoteCount();
            voteCount++;
            resultList.get(index).setVoteCount(voteCount);
        } else {
            Result newResult = new Result(candidateDetails.getParty().getId(), candidateDetails.getParty().getName(),
                    candidateDetails.getConstituency().getId(), candidateDetails.getConstituency().getName(), candidateDetails.getName(), 1);
            resultList.add(newResult);
        }
    }

    public void bindResults() {
        TableLayout stk = (TableLayout) findViewById(R.id.table_main);
        TableRow tbrow0 = new TableRow(this);

        TextView tv0 = new TextView(this);
        tv0.setText(" Constituency ");
        tv0.setTextSize(18);
        tv0.setWidth(350);
        tv0.setTextColor(Color.BLUE);
        tv0.setGravity(Gravity.CENTER);
        tbrow0.addView(tv0);
        TextView tv1 = new TextView(this);
        tv1.setText(" Candidate ");
        tv1.setTextSize(18);
        tv1.setWidth(280);
        tv1.setTextColor(Color.BLUE);
        tv1.setGravity(Gravity.CENTER);
        tbrow0.addView(tv1);
        TextView tv2 = new TextView(this);
        tv2.setText(" Party ");
        tv2.setTextSize(18);
        tv2.setWidth(200);
        tv2.setTextColor(Color.BLUE);
        tv2.setGravity(Gravity.CENTER);
        tbrow0.addView(tv2);
        TextView tv3 = new TextView(this);
        tv3.setText(" Vote ");
        tv3.setTextSize(18);
        tv3.setWidth(200);
        tv3.setTextColor(Color.BLUE);
        tv3.setGravity(Gravity.CENTER);
        tbrow0.addView(tv3);
        stk.addView(tbrow0);
        for (int i = 0; i < resultList.size(); i++) {
            TableRow tbrow = new TableRow(this);
            TextView t1v = new TextView(this);
            t1v.setText(resultList.get(i).getConstituencyName());
            t1v.setTextColor(Color.BLACK);
            t1v.setTextSize(18);
            t1v.setGravity(Gravity.CENTER_HORIZONTAL);
            t1v.setWidth(200);
            tbrow.addView(t1v);
            TextView t2v = new TextView(this);
            t2v.setText(resultList.get(i).getCandidateName());
            t2v.setTextColor(Color.BLACK);
            t2v.setTextSize(18);
            t2v.setGravity(Gravity.CENTER_HORIZONTAL);
            t2v.setWidth(200);
            tbrow.addView(t2v);
            TextView t3v = new TextView(this);
            t3v.setText(resultList.get(i).getPartyName());
            t3v.setTextColor(Color.BLACK);
            t3v.setTextSize(18);
            t3v.setGravity(Gravity.CENTER_HORIZONTAL);
            //t3v.setWidth(150);
            tbrow.addView(t3v);
            TextView t4v = new TextView(this);
            t4v.setText(String.valueOf(resultList.get(i).getVoteCount()));
            t4v.setTextColor(Color.BLACK);
            t4v.setTextSize(18);
            t4v.setGravity(Gravity.CENTER_HORIZONTAL);
            //t4v.setWidth(150);
            tbrow.addView(t4v);
            stk.addView(tbrow);
        }
    }

    private int searchInResultList(int partyId, int constituencyId) {
        int index = -1;
        for (int i = 0; i < resultList.size(); i++) {
            Result result = resultList.get(i);
            if (result.getPartyId() == partyId && result.getConstituencyId() == constituencyId) {
                index = i;
                break;
            }
        }
        return index;
    }

}