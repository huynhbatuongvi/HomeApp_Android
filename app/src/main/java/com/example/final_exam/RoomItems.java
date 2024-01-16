package com.example.final_exam;

public class RoomItems {

    // khai báo biến
    private long cid;
    private String RoomName;
    private int RoomImage;


    // tạo constructor
    public RoomItems(long cid, String roomName, int roomImage) {
        this.cid = cid;
        this.RoomName = roomName;
        this.RoomImage = roomImage;
    }

    // tạo getter và setter cho các biến
    public String getRoomName() {
        return RoomName;
    }

    public int getRoomImage() {
        return RoomImage;
    }


    public long getCid() {
        return cid;
    }

    public void setCid(long cid) {
        this.cid = cid;
    }

    public void setRoomName(String roomName) {
        RoomName = roomName;
    }

    public void setRoomImage(int roomImage) {
        RoomImage = roomImage;
    }
}
