
package com.ioss.covid.model.panchayathModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Panchayath {

    @SerializedName("panchayat_id")
    @Expose
    private String panchayatId;
    @SerializedName("district_id")
    @Expose
    private String districtId;
    @SerializedName("panchayat_name")
    @Expose
    private String panchayatName;
    @SerializedName("panchayat_name_mal")
    @Expose
    private String panchayatNameMal;

    public String getPanchayatId() {
        return panchayatId;
    }

    public void setPanchayatId(String panchayatId) {
        this.panchayatId = panchayatId;
    }

    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
    }

    public String getPanchayatName() {
        return panchayatName;
    }

    public void setPanchayatName(String panchayatName) {
        this.panchayatName = panchayatName;
    }

    public String getPanchayatNameMal() {
        return panchayatNameMal;
    }

    public void setPanchayatNameMal(String panchayatNameMal) {
        this.panchayatNameMal = panchayatNameMal;
    }

}
