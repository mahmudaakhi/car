package com.r.cardtc.Model;

public class Data {
    private String carName;
    private String carDetails;
    private Integer Image;

    public Data(String carName, String carDetails, Integer image) {
        this.carName = carName;
        this.carDetails = carDetails;
        Image = image;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public String getCarDetails() {
        return carDetails;
    }

    public void setCarDetails(String carDetails) {
        this.carDetails = carDetails;
    }

    public Integer getImage() {
        return Image;
    }

    public void setImage(Integer image) {
        Image = image;
    }
}
