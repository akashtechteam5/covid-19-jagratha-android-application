
package com.ioss.covid.model.panchayathModel;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("panchayath")
    @Expose
    private List<Panchayath> panchayath = null;

    public List<Panchayath> getPanchayath() {
        return panchayath;
    }

    public void setPanchayath(List<Panchayath> panchayath) {
        this.panchayath = panchayath;
    }

}
