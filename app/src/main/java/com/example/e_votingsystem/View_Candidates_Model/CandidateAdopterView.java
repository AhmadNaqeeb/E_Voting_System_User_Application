package com.example.e_votingsystem.View_Candidates_Model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_votingsystem.Candidates_Manage.CandidateDetails;
import com.example.e_votingsystem.R;
import com.example.e_votingsystem.VoterHomeActivity;

import java.util.ArrayList;
import java.util.List;

public class CandidateAdopterView extends RecyclerView.Adapter<CandidateAdopterView.CandidatesViewHolder> {

    private Context context;
    String province = "";
    private List<CandidateDetails> allRegisteredCandidatesList = new ArrayList<>();

    public CandidateAdopterView(Context context) {
        this.allRegisteredCandidatesList = VoterHomeActivity.candidateList;
        this.context = context;

    }

    @Override
    public void onBindViewHolder(CandidateAdopterView.CandidatesViewHolder holder, int position) {
        CandidateDetails model = allRegisteredCandidatesList.get(position);
        System.out.println("Ahmad");
        holder.bind(model);

    }

    @Override
    public int getItemCount() {
        return allRegisteredCandidatesList.size();
    }

    @NonNull
    @Override
    public CandidateAdopterView.CandidatesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.candidates_list_design, parent, false);
        return new CandidateAdopterView.CandidatesViewHolder(view);
    }

    public class CandidatesViewHolder extends RecyclerView.ViewHolder {
        TextView tv_Name, tv_Constituency, tv_Party, tv_Province;
        ImageView iv_Sign;

        public CandidatesViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_Name = itemView.findViewById(R.id.tv_Name);
            tv_Constituency = itemView.findViewById(R.id.tv_Constituency);
            tv_Party = itemView.findViewById(R.id.tv_Party);
            tv_Province = itemView.findViewById(R.id.tv_Province);
            iv_Sign = itemView.findViewById(R.id.iv_Sign);
        }

       public void bind(CandidateDetails candidateDetails) {
            int resID = 0;
            if (candidateDetails.getParty().getSymbol() != null) {
                resID = context.getResources().getIdentifier(candidateDetails.getParty().getSymbol(), "drawable", context.getPackageName());
            }
            tv_Name.setText(candidateDetails.getName());
            tv_Province.setText(candidateDetails.getProvince());
            tv_Party.setText(candidateDetails.getParty().getName());
            tv_Constituency.setText(candidateDetails.getConstituency().getName()  + " - " + candidateDetails.getConstituency().getParent());
            if (resID != 0) {
                iv_Sign.setImageResource(resID);
            }
        }
    }
}

