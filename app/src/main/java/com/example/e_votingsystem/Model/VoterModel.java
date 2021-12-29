package com.example.e_votingsystem.Model;

public class VoterModel {
    int id ;
    String Name;
    String Age;
    String CNIC;
    String Mobile;
    String City;
    String Area;
    String Province;
    String Password;
    String Vote;
    String mnaCandidate;
    String mpaCandidate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAge() {
        return Age;
    }

    public void setAge(String age) {
        Age = age;
    }

    public String getCNIC() {
        return CNIC;
    }

    public void setCNIC(String CNIC) {
        this.CNIC = CNIC;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getArea() {
        return Area;
    }

    public void setArea(String area) {
        Area = area;
    }

    public String getProvince() {
        return Province;
    }

    public void setProvince(String province) {
        Province = province;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getVote() {
        return Vote;
    }

    public void setVote(String vote) {
        Vote = vote;
    }

    public String getMnaCandidate() {
        return mnaCandidate;
    }

    public void setMnaCandidate(String mnaCandidate) {
        this.mnaCandidate = mnaCandidate;
    }

    public String getMpaCandidate() {
        return mpaCandidate;
    }

    public void setMpaCandidate(String mpaCandidate) {
        this.mpaCandidate = mpaCandidate;
    }

    public VoterModel() {
    }

    public VoterModel(String name, String age, String CNIC, String mobile, String city, String area, String province, String password, String vote, String mnaCandidate, String mpaCandidate) {
        Name = name;
        Age = age;
        this.CNIC = CNIC;
        Mobile = mobile;
        City = city;
        Area = area;
        Province = province;
        Password = password;
        Vote = vote;
        this.mnaCandidate = mnaCandidate;
        this.mpaCandidate = mpaCandidate;
    }

    @Override
    public String toString() {
        return "VoterModel{" +
                "Name='" + Name + '\'' +
                ", Age='" + Age + '\'' +
                ", CNIC='" + CNIC + '\'' +
                ", Mobile='" + Mobile + '\'' +
                ", City='" + City + '\'' +
                ", Area='" + Area + '\'' +
                ", Province='" + Province + '\'' +
                ", Password='" + Password + '\'' +
                ", Vote='" + Vote + '\'' +
                ", candidate='" + mnaCandidate + '\'' +
                '}';
    }
}
