package com.r.cardtc.Model;

public class CarList {

    private String carName,carImage,mKey;

    public CarList() {
    }

    public CarList(String carName, String carImage, String mKey) {
        this.carName = carName;
        this.carImage = carImage;
        this.mKey = mKey;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public String getCarImage() {
        return carImage;
    }

    public void setCarImage(String carImage) {
        this.carImage = carImage;
    }

    public String getmKey() {
        return mKey;
    }

    public void setmKey(String mKey) {
        this.mKey = mKey;
    }
}
