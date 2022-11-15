package com.example.fooddash;

public class ProfileHelperClass {

    String pref,familyname,imageurl;

    public ProfileHelperClass(){

    }
    public ProfileHelperClass(String pref, String familyname, String imageurl){
        this.pref = pref;
        this.familyname = familyname;
        this.imageurl = imageurl;
    }

    public String getPref() { return pref; }

    public void setPref(String pref) { this.pref = pref; }

    public String getFamilyname() { return familyname; }

    public void setFamilyname(String familyname) { this.familyname = familyname; }

    public String getImageurl() { return imageurl; }

    public void setImageurl(String imageurl) { this.imageurl = imageurl; }
}
