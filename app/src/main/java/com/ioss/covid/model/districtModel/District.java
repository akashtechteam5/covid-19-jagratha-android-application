
package com.ioss.covid.model.districtModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class District {

    @SerializedName("district_id")
    @Expose
    private String districtId;
    @SerializedName("state_id")
    @Expose
    private String stateId;
    @SerializedName("district_name")
    @Expose
    private String districtName;
    @SerializedName("district_name_mal")
    @Expose
    private String districtNameMal;

    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
    }

    public String getStateId() {
        return stateId;
    }

    public void setStateId(String stateId) {
        this.stateId = stateId;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getDistrictNameMal() {
        return districtNameMal;
    }

    public void setDistrictNameMal(String districtNameMal) {
        this.districtNameMal = districtNameMal;
    }

}
