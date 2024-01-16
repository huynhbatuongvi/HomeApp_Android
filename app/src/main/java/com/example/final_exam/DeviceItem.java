package com.example.final_exam;

public class DeviceItem {

    // khai báo biến
    private String namedevice;
    private int alpha;
    private  long did;

    // tạo constructor
    public DeviceItem(long did, String namedevice) {
        this.did = did;
        this.namedevice = namedevice;
    }
    public void setAlpha(int alpha) {
        this.alpha = alpha;
    }

    // tạo getter và setter cho các biến

    public int getAlpha() {
        return alpha;
    }

    public String getNamedevice() {
        return namedevice;
    }

    public void setNamedevice(String namedevice) {
        this.namedevice = namedevice;
    }

    public long getDid() {
        return did;
    }

    public void setDid(long did) {
        this.did = did;
    }

    }

