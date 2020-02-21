package com.mattluedke.snowshoelib;

import android.support.annotation.Keep;

import com.google.gson.annotations.SerializedName;

import java.util.List;

@Keep
public class StampRequest {

    @SerializedName("data")
    private List<List<Float>> data;

    public List<List<Float>> getData() {
        return data;
    }

    public void setData(List<List<Float>> data) {
        this.data = data;
    }
}
