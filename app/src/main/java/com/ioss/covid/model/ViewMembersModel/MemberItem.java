
package com.ioss.covid.model.ViewMembersModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MemberItem {

    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("age")
    @Expose
    private String age;
    @SerializedName("health_status")
    @Expose
    private String healthStatus;
    @SerializedName("health_status_colour")
    @Expose
    private String healthStatusColour;
    @SerializedName("vulnerability_status")
    @Expose
    private String vulnerabilityStatus;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
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

}
