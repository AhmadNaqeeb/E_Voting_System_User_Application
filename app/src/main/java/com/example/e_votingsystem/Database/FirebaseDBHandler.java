package com.example.e_votingsystem.Database;

import android.app.Application;

import android.content.Context;
import android.util.Log;

import com.example.e_votingsystem.Candidates_Manage.CandidateDetails;
import com.example.e_votingsystem.Candidates_Manage.Candidates;
import com.example.e_votingsystem.Model.DataRetrievalCallback;
import com.example.e_votingsystem.Model.CandidateDataCallback;
import com.example.e_votingsystem.Model.VoterDataCallback;
import com.example.e_votingsystem.Model.VoterModel;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FirebaseDBHandler extends Application {

    public static Firebase mRootReference;
    public static DataRetrievalCallback mCallback;
    public static CandidateDataCallback mCandidateDataCallback;
    public static VoterDataCallback mVoterDataCallback;

    public static Context mContext ;
    @Override
    public void onCreate() {

        super.onCreate();
        Firebase.setAndroidContext(this);
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        mRootReference = new Firebase("https://e-voting-admin-76dc6-default-rtdb.firebaseio.com/EVotingDB");
        Log.d("Firebase DB Handler " , "Database Initialized");

    }

    public static boolean insert(String parentNodeName, String nodeName , Object node , String index){

        mRootReference.child(parentNodeName).child(nodeName).child(index).setValue(node);

        return true ;
    }

    public static boolean update(String parentNodeName, String nodeName , Object updatesNode , String childNodePosition){

        mRootReference.child(parentNodeName).child(nodeName).child(childNodePosition).setValue(updatesNode);

        return true ;
    }

    // Retrieve all children nodes.......

    public static void getNodeAllChildren(final String parentNodeName , final String nodeName){
        final Firebase db_ref = mRootReference.child(parentNodeName).child(nodeName);
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Map<String , String>> nodeChildrenList = new ArrayList<>();
                for (DataSnapshot data : dataSnapshot.getChildren()){
                    Map<String,String> childMap = new HashMap<>();
                    childMap.put(data.getKey(),data.getValue().toString());
                    nodeChildrenList.add(childMap);
                }
                Log.d("RetrieveDataSize: " , String.valueOf(nodeChildrenList.size()));
                mCallback.onDataRetrieved(nodeChildrenList , nodeName);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.d("cancelled: " , (parentNodeName + "," + nodeName));
            }
        };
        db_ref.addListenerForSingleValueEvent(valueEventListener);
        db_ref.removeEventListener(valueEventListener);
    }

    public static void getCandidateNodeAllChildren(final String parentNodeName , final String nodeName){
        final Firebase db_ref = mRootReference.child(parentNodeName).child(nodeName);
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Candidates> nodeChildrenList = new ArrayList<>();
                for (DataSnapshot data : dataSnapshot.getChildren()){
                    Candidates candidates = data.getValue(Candidates.class);
                    Log.d("FirebaseDBHandler: ", candidates.toString());
                    nodeChildrenList.add(candidates);
                }
                Log.d("RetrieveDataSize: " , String.valueOf(nodeChildrenList.size()));
                mCandidateDataCallback.onCandidateDataRetrieved(nodeChildrenList,nodeName);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.d("cancelled: " , (parentNodeName + "," + nodeName));
            }
        };
        db_ref.addListenerForSingleValueEvent(valueEventListener);
        db_ref.removeEventListener(valueEventListener);
    }

    public static void getVoterNodeAllChildren(final String parentNodeName , final String nodeName){
        final Firebase db_ref = mRootReference.child(parentNodeName).child(nodeName);
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<VoterModel> nodeChildrenList = new ArrayList<>();
                for (DataSnapshot data : dataSnapshot.getChildren()){
                    VoterModel voterModel = data.getValue(VoterModel.class);
                    Log.d("FirebaseDBHandler: ", voterModel.toString());
                    nodeChildrenList.add(voterModel);
                }
                Log.d("RetrieveDataSize: " , String.valueOf(nodeChildrenList.size()));
                mVoterDataCallback.onVoterDataRetrieved(nodeChildrenList,nodeName);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.d("cancelled: " , (parentNodeName + "," + nodeName));
            }
        };
        db_ref.addListenerForSingleValueEvent(valueEventListener);
        db_ref.removeEventListener(valueEventListener);
    }

}
