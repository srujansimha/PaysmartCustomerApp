package com.webingate.paysmartcustomerapp.object;

import com.google.gson.annotations.SerializedName;

public class GetalyftVehiclelist {

    @SerializedName("id")
    private String id;

    @SerializedName("icon")
    private String icon;

    @SerializedName("name")
    private String name;

    @SerializedName("selectedicon")
    private String selectedicon;

    @SerializedName("isselected")
    private String isselected;


    public GetalyftVehiclelist(String id, String icon, String name, String selectedicon, String isselected) {
        this.id = id;
        this.icon = icon;
        this.name = name;
        this.selectedicon = selectedicon;
        this.isselected = isselected;
    }

    public String getId() {
        return id;
    }

    public String getIcon() {
        return icon;
    }

    public String getSelectedIcon() {
        return selectedicon;
    }


    public String getName() {
        return name;
    }

    public String getselected() {
        return isselected;
    }

    public void setselected(String isselected) {
         this.isselected = isselected;
    }
}
