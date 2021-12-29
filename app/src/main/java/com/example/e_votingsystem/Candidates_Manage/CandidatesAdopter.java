package com.example.e_votingsystem.Candidates_Manage;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_votingsystem.R;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CandidatesAdopter extends RecyclerView.Adapter<CandidatesAdopter.CandidatesViewHolder>{

    private Context context;
    private OnItemClick mCallback;
    String province = "";
    private List<CandidateDetails> allRegisteredCandidatesList = new ArrayList<>();
    public CandidatesAdopter(@NonNull List<CandidateDetails> allRegisteredCandidatesList , Context context, OnItemClick itemClick) {
        this.allRegisteredCandidatesList = allRegisteredCandidatesList;
        this.context = context;
        this.mCallback = itemClick;

    }

    @Override
    public void onBindViewHolder(CandidatesViewHolder holder, int position) {
        CandidateDetails model = allRegisteredCandidatesList.get(position);
        holder.bind(model,mCallback);

    }

    @Override
    public int getItemCount() {
        return allRegisteredCandidatesList.size();
    }

    @NonNull
    @Override
    public CandidatesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.candidates_list_design, parent, false);
        return new CandidatesViewHolder(view);
    }

    public class CandidatesViewHolder extends RecyclerView.ViewHolder
    {
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

        public void bind(CandidateDetails candidateDetails, OnItemClick listener){
            int resID = 0;
            if (candidateDetails.getParty().getSymbol() != null){
                resID = context.getResources().getIdentifier(candidateDetails.getParty().getSymbol(), "drawable",context.getPackageName());
            }
            tv_Name.setText(candidateDetails.getName());
            //tv_Province.setText(candidateDetails.getProvince());
            tv_Party.setText(candidateDetails.getParty().getName());
            tv_Constituency.setText(candidateDetails.getConstituency().getName() + " - " + candidateDetails.getConstituency().getParent());
            if (resID != 0)
            {
                iv_Sign.setImageResource(resID);
            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(candidateDetails);
                }
            });
        }
    }
    public interface OnItemClick{
        void onClick(CandidateDetails candidateDetails);
    }
}
