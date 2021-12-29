package com.example.e_votingsystem.Model;

public class Result {
    private int partyId;
    private String partyName;
    private int constituencyId;
    private String constituencyName;
    private String candidateName;
    private int voteCount;

    public Result() {

    }

    public Result(int partyId, String partyName, int constituencyId, String constituencyName, String candidateName, int voteCount) {
        this.partyId = partyId;
        this.partyName = partyName;
        this.constituencyId = constituencyId;
        this.constituencyName = constituencyName;
        this.candidateName = candidateName;
        this.voteCount = voteCount;
    }

    public int getPartyId() {
        return partyId;
    }

    public void setPartyId(int partyId) {
        this.partyId = partyId;
    }

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public int getConstituencyId() {
        return constituencyId;
    }

    public void setConstituencyId(int constituencyId) {
        this.constituencyId = constituencyId;
    }

    public String getConstituencyName() {
        return constituencyName;
    }

    public void setConstituencyName(String constituencyName) {
        this.constituencyName = constituencyName;
    }

    public String getCandidateName() {
        return candidateName;
    }

    public void setCandidateName(String candidateName) {
        this.candidateName = candidateName;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    @Override
    public String toString() {
        return "Result{" +
                "partyId=" + partyId +
                ", partyName='" + partyName + '\'' +
                ", constituencyId=" + constituencyId +
                ", constituencyName='" + constituencyName + '\'' +
                ", candidateName='" + candidateName + '\'' +
                ", voteCount=" + voteCount +
                '}';
    }
}
