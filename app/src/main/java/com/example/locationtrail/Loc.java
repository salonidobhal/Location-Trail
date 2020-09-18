package com.example.locationtrail;

class Loc {

        public String id;
        public String latitude;
        public String longitude;
        String time;

        public Loc() {
        }


    public Loc(String id, String latitude, String longitude, String time) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.time=time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
