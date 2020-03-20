
package com.ioss.covid.model.userDetailsModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Symptom {

    @SerializedName("symptom_id")
    @Expose
    private String symptomId;
    @SerializedName("symptom")
    @Expose
    private String symptom;
    @SerializedName("symptom_mal")
    @Expose
    private String symptomMal;
    @SerializedName("value")
    @Expose
    private String value;

    public String getSymptomId() {
        return symptomId;
    }

    public void setSymptomId(String symptomId) {
        this.symptomId = symptomId;
    }

    public String getSymptom() {
        return symptom;
    }

    public void setSymptom(String symptom) {
        this.symptom = symptom;
    }

    public String getSymptomMal() {
        return symptomMal;
    }

    public void setSymptomMal(String symptomMal) {
        this.symptomMal = symptomMal;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
