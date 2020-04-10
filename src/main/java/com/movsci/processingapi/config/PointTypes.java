package com.movsci.processingapi.config;

public enum PointTypes {
    PT("PT"),
    LN("LN"),
    ANG2("ANG2"),
    ANG3("ANG3"),
    ANG4("ANG4"),
    TR("TR");

    public String type;

    PointTypes(String pointType){
        this.type = pointType;
    }

    public String getType(){
        return type;
    }

}
