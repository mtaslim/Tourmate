package com.ityadi.app.tourmate.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by taslim on 8/19/2016.
 */
public class MomentListResponse {

    @SerializedName("momentList")
    @Expose
    private List<MomentList> momentList = new ArrayList<MomentList>();

    /**
     *
     * @return
     * The momentList
     */
    public List<MomentList> getMomentList() {
        return momentList;
    }

    /**
     *
     * @param momentList
     * The momentList
     */
    public void setMomentList(List<MomentList> momentList) {
        this.momentList = momentList;
    }

}
