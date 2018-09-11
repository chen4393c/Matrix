package com.laioffer.matrix;

public class Event {

    private String mTitle;
    private String mAddress;
    private String mDescription;

    public Event(String title, String address, String description) {
        mTitle = title;
        mAddress = address;
        mDescription = description;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getAddress() {
        return mAddress;
    }

    public String getDescription() {
        return mDescription;
    }
}
