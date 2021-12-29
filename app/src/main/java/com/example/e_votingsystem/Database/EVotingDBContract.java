package com.example.e_votingsystem.Database;

import android.net.Uri;

public final class EVotingDBContract {

    public static final String DATABASE_NAME = "EVotingDB";
    public static final String AUTHORITY = "com.example.e_votingsystem";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY) ;
    public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(DATABASE_NAME).build();

    public static final String PATH_ADMIN = "Admin" ;
    public static final String PATH_PARTIES = "Parties" ;
    public static final String CANDIDATE_NODE = "Candidates" ;
    public static final String CONSTITUENCY_NODE = "Constituency" ;
    public static final String VOTER_NODE = "Voter" ;

    public static int childNodePosition = -1;

    // Default Contructor.....

    public EVotingDBContract(){

    }

    // Voter table in EVotingDB Database........
    public static class VoterTable{

        public static String TABLE_NAME = VOTER_NODE;
        public static String VOTER_ID_COLUMN = "id";
        public static String VOTER_CNIC_COLUMN = "cnic";
        public static String VOTER_NAME_COLUMN = "name";
        public static String VOTER_PASSWORD_COLUMN = "password";
        public static String VOTER_CITY_COLUMN = "city";
        public static String VOTER_PROVINCE_COLUMN = "province";
        public static String VOTER_AREA_COLUMN = "area";
        public static String VOTER_VOTE_COLUMN = "vote";
        public static String VOTER_AGE_COLUMN = "age";
        public static String VOTER_MOBILE_COLUMN = "mobile";
        public static String VOTER_MNA_CANDIDATE_COLUMN = "mnaCandidate";
        public static String VOTER_MPA_CANDIDATE_COLUMN = "mpaCandidate";
    }

    // Inner classes act as provider of Database table information.....

    // Party table in EVotingDB Database........

    public static class PartiesTable{

        public static String TABLE_NAME = "Parties";
        public static String PARTY_ID_COLUMN = "id";
        public static String PARTY_NAME_COLUMN = "name";
        public static String PARTY_SYMBOL_COLUMN = "symbol";
    }

    public static class CandidatesTable {

        public static String TABLE_NAME = "Candidates";
        public static String CANDIDATE_ID_COLUMN = "id";
        public static String CANDIDATE_CNIC_COLUMN = "cnic";
        public static String CANDIDATE_PROVINCE_COLUMN = "province";
        public static String CANDIDATE_AREA_COLUMN = "area";
        public static String CANDIDATE_NAME_COLUMN = "name";
        public static String CANDIDATE_CITY_COLUMN = "city";
        public static String CANDIDATE_CONSTITUENCY_COLUMN = "constituency";
        public static String CANDIDATE_PARTY_COLUMN = "party";
    }

    public static class ConstituencyTable {

        public static String TABLE_NAME = "Constituency";
        public static String CONSTITUENCY_ID_COLUMN = "id";
        public static String CONSTITUENCY_NAME_COLUMN = "name";
        public static String CONSTITUENCY_PARENT_COLUMN = "parent";
        public static String CONSTITUENCY_CITY_COLUMN = "city";
        public static String CONSTITUENCY_PROVINCE_COLUMN = "province";
        public static String CONSTITUENCY_AREA_COLUMN = "area";
    }
}
