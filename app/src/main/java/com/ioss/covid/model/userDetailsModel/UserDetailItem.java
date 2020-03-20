
package com.ioss.covid.model.userDetailsModel;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserDetailItem {
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("district")
    @Expose
    private String district;
    @SerializedName("panchayat")
    @Expose
    private String panchayat;
    @SerializedName("village")
    @Expose
    private String village;
    @SerializedName("contact_1")
    @Expose
    private String contact1;
    @SerializedName("contact_2")
    @Expose
    private String contact2;
    @SerializedName("contact_3")
    @Expose
    private String contact3;
    @SerializedName("health_status")
    @Expose
    private String healthStatus;
    @SerializedName("health_status_colour")
    @Expose
    private String healthStatusColour;
    @SerializedName("vulnerability_status")
    @Expose
    private String vulnerabilityStatus;
    @SerializedName("symptoms")
    @Expose
    private List<Symptom> symptoms = null;
    @SerializedName("questionnaire")
    @Expose
    private List<Object> questionnaire = null;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getPanchayat() {
        return panchayat;
    }

    public void setPanchayat(String panchayat) {
        this.panchayat = panchayat;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getContact1() {
        return contact1;
    }

    public void setContact1(String contact1) {
        this.contact1 = contact1;
    }

    public String getContact2() {
        return contact2;
    }

    public void setContact2(String contact2) {
        this.contact2 = contact2;
    }

    public String getContact3() {
        return contact3;
    }

    public void setContact3(String contact3) {
        this.contact3 = contact3;
    }

    public String getHealthStatus() {
        return healthStatus;
    }

    public void setHealthStatus(String healthStatus) {
        this.healthStatus = healthStatus;
    }

    public String getHealthStatusColour() {
        return healthStatusColour;
    }

    public void setHealthStatusColour(String healthStatusColour) {
        this.healthStatusColour = healthStatusColour;
    }

    public String getVulnerabilityStatus() {
        return vulnerabilityStatus;
    }

    public void setVulnerabilityStatus(String vulnerabilityStatus) {
        this.vulnerabilityStatus = vulnerabilityStatus;
    }

    public List<Symptom> getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(List<Symptom> symptoms) {
        this.symptoms = symptoms;
    }

    public List<Object> getQuestionnaire() {
        return questionnaire;
    }

    public void setQuestionnaire(List<Object> questionnaire) {
        this.questionnaire = questionnaire;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
