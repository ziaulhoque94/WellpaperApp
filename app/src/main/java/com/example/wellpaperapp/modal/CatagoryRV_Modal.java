package com.example.wellpaperapp.modal;

public class CatagoryRV_Modal {
    private String Catagory;
    private String CatagoruIVUrl;

    public CatagoryRV_Modal(String catagory, String catagoruIVUrl) {
        Catagory = catagory;
        CatagoruIVUrl = catagoruIVUrl;
    }

    public CatagoryRV_Modal() {
    }

    public String getCatagory() {
        return Catagory;
    }

    public void setCatagory(String catagory) {
        Catagory = catagory;
    }

    public String getCatagoruIVUrl() {
        return CatagoruIVUrl;
    }

    public void setCatagoruIVUrl(String catagoruIVUrl) {
        CatagoruIVUrl = catagoruIVUrl;
    }
}
