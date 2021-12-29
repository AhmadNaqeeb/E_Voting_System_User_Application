package com.example.e_votingsystem.Candidates_Manage;


public class Candidates {
    private int id;
    private String name;
    private String CNIC;
    private String province;
    private String city;
    private String area;
    private String constituency;
    private String party;

    public Candidates() {
        id = 0;
        name = "";
        province = "";
    }

    public Candidates(String name, String province, String constituency, String party) {
        this.name = name;
        this.province = province;
        this.constituency = constituency;
        this.party = party;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getConstituency() {
        return constituency;
    }

    public void setConstituency(String constituency) {
        this.constituency = constituency;
    }

    public String getParty() {
        return party;
    }

    public void setParty(String party) {
        this.party = party;
    }

    public String getCNIC() {
        return CNIC;
    }

    public void setCNIC(String CNIC) {
        this.CNIC = CNIC;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    @Override
    public String toString() {
        return "Candidates{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", province='" + province + '\'' +
                ", constituency='" + constituency.toString() + '\'' +
                ", party='" + party.toString() + '\'' +
                '}';
    }
}
