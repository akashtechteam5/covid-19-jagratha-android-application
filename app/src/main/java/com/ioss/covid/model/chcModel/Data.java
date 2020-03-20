
package com.ioss.covid.model.chcModel;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("chc")
    @Expose
    private List<Chc> chc = null;

    public List<Chc> getChc() {
        return chc;
    }

    public void setChc(List<Chc> chc) {
        this.chc = chc;
    }

}
