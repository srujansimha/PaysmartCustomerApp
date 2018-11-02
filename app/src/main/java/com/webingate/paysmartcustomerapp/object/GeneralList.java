package com.webingate.paysmartcustomerapp.object;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Panacea-Soft on 7/17/18.
 * Contact Email : teamps.is.cool@gmail.com
 */


public class GeneralList {

    @SerializedName("name")
    public String name;

    @SerializedName("caption")
    public String caption;

    @SerializedName("image")
    public String image;

    @SerializedName("detail")
    public String detail;

    @SerializedName("description")
    public String description;

    @SerializedName("attrib1")
    public String attrib1;

    @SerializedName("attrib2")
    public String attrib2;

    @SerializedName("attrib3")
    public String attrib3;

    @SerializedName("attrib4")
    public String attrib4;

    public GeneralList(String name, String caption, String image, String detail, String description) {
        this.name = name;
        this.caption = caption;
        this.image = image;
        this.detail = detail;
        this.description = description;
    }

    public GeneralList(String name, String caption, String image, String detail
            , String attrib1, String attrib2, String attrib3, String attrib4, String desc) {
        this.name = name;
        this.caption = caption;
        this.image = image;
        this.detail = detail;
        this.description = desc;
        this.attrib1 = attrib1;
        this.attrib2 = attrib2;
        this.attrib3 = attrib3;
        this.attrib4 = attrib4;
    }
}
