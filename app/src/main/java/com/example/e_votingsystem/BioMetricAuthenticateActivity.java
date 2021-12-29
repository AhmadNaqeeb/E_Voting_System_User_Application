package com.example.e_votingsystem;

import android.animation.Animator;
import android.content.Intent;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.example.e_votingsystem.Database.EVotingDBContract;
import com.example.e_votingsystem.Database.FirebaseDBHandler;
import com.example.e_votingsystem.Model.VoterDataCallback;
import com.example.e_votingsystem.Model.VoterModel;

import java.util.List;

public class BioMetricAuthenticateActivity extends AppCompatActivity implements VoterDataCallback {

    LottieAnimationView lottieAnimationView;
    private FingerprintManager fingerprintManager;
    private FingerprintManager.AuthenticationCallback authenticationCallback;
    private String partyName;
    private String candidateJson;
    private String constituencyParent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.biometricautenticteactivity);

        Intent biometricIntent = getIntent();
        partyName = biometricIntent.getStringExtra("PartyName");
        candidateJson = biometricIntent.getStringExtra("candidate");
        constituencyParent = biometricIntent.getStringExtra("constituencyParent");

        lottieAnimationView = findViewById(R.id.fpAnimation);

        fingerprintManager = (FingerprintManager)getSystemService(FINGERPRINT_SERVICE);

        authenticationCallback = new FingerprintManager.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                lottieAnimationView.setAnimation(R.raw.error);
                lottieAnimationView.setSpeed(1);
                lottieAnimationView.playAnimation();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        lottieAnimationView.setSpeed(-1);
                        lottieAnimationView.playAnimation();
                    }
                }, 2000);
            }
            @Override
            public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                lottieAnimationView.setAnimation(R.raw.success);
                lottieAnimationView.setSpeed(1);
                lottieAnimationView.playAnimation();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                    }
                }, 2000);

                lottieAnimationView.addAnimatorListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        UpdateVoterVoteStatus();
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                lottieAnimationView.setAnimation(R.raw.error);
                lottieAnimationView.setSpeed(1);
                lottieAnimationView.playAnimation();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        lottieAnimationView.setSpeed(-1);
                        lottieAnimationView.playAnimation();
                    }
                }, 2000);
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        fingerprintManager.authenticate(null, null, 0, authenticationCallback, null);
    }

    private void UpdateVoterVoteStatus(){
        FirebaseDBHandler.mVoterDataCallback = this;
        FirebaseDBHandler.getVoterNodeAllChildren("1", EVotingDBContract.VOTER_NODE);
    }

    private void UpdateVoterStatus(int childNodePosition){
        // call Firebase database to update Voter node for vote casted....
        VoterModel currentVoter = SignInActivity.loggedInUser;
        if (childNodePosition >= 0){
            if (constituencyParent.equalsIgnoreCase("MNA")){
                currentVoter.setMnaCandidate(candidateJson);
                SignInActivity.loggedInUser.setMnaCandidate(candidateJson);
            }else if (constituencyParent.equalsIgnoreCase("MPA")){
                currentVoter.setMpaCandidate(candidateJson);
                SignInActivity.loggedInUser.setMpaCandidate(candidateJson);
            }

            if (!(currentVoter.getMpaCandidate().equalsIgnoreCase("NA")) && !(currentVoter.getMnaCandidate().equalsIgnoreCase("NA"))){
                currentVoter.setVote("COMPLETE");  // MNA_VOTE_CASTED mean user successfully casted his/her vote to the party
            }else{
                currentVoter.setVote("PARTIAL_VOTE_CASTED");  // MNA_VOTE_CASTED mean user successfully casted his/her vote to the party
            }
            SignInActivity.loggedInUser.setVote(currentVoter.getVote());
            // Update Voter status into Firebase database
            FirebaseDBHandler.update("1", EVotingDBContract.VOTER_NODE,currentVoter,String.valueOf(childNodePosition));

            // After successful voting redirect Voter to result activity
            Intent resultActivityIntent = new Intent(BioMetricAuthenticateActivity.this, Voting_Results_Activity.class);
            resultActivityIntent.putExtra("PartyName",partyName);
            startActivity(resultActivityIntent);
            finish();
        }else{
            Toast.makeText(BioMetricAuthenticateActivity.this, "Unable to update your vote status. Please contact E Voting System Admin " + childNodePosition, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onVoterDataRetrieved(List<VoterModel> voterDataList, String nodeName) {
        if (voterDataList != null && voterDataList.size() > 0){
            for (int i = 0 ; i < voterDataList.size(); i++){
                if (nodeName.equalsIgnoreCase(EVotingDBContract.VoterTable.TABLE_NAME)){
                    VoterModel vm = voterDataList.get(i);
                    if (vm.getCNIC().equalsIgnoreCase(SignInActivity.loggedInUser.getCNIC())){
                        UpdateVoterStatus(vm.getId());
                        break;
                    }

                }
            }// EOF for loop....
        }
    }
}