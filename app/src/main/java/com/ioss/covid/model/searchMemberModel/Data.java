
package com.ioss.covid.model.searchMemberModel;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ioss.covid.model.ViewMembersModel.MemberItem;

public class Data {

    @SerializedName("login_id")
    @Expose
    private String loginId;
    @SerializedName("users")
    @Expose
    private List<MemberItem> users = null;

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public List<MemberItem> getUsers() {
        return users;
    }

    public void setUsers(List<MemberItem> users) {
        this.users = users;
    }

}
