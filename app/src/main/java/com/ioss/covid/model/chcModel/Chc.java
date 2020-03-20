
package com.ioss.covid.model.chcModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Chc {

    @SerializedName("chc_id")
    @Expose
    private String chcId;
    @SerializedName("phc_id")
    @Expose
    private String phcId;
    @SerializedName("panchayat_id")
    @Expose
    private String panchayatId;
    @SerializedName("district_id")
    @Expose
    private String districtId;
    @SerializedName("chc_name")
    @Expose
    private String chcName;
    @SerializedName("chc_name_mal")
    @Expose
    private String chcNameMal;

    public String getChcId() {
        return chcId;
    }

    public void setChcId(String chcId) {
        this.chcId = chcId;
    }

    public String getPhcId() {
        return phcId;
    }

    public void setPhcId(String phcId) {
        this.phcId = phcId;
    }

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

    public String getChcName() {
        return chcName;
    }

    public void setChcName(String chcName) {
        this.chcName = chcName;
    }

    public String getChcNameMal() {
        return chcNameMal;
    }

    public void setChcNameMal(String chcNameMal) {
        this.chcNameMal = chcNameMal;
    }

}
