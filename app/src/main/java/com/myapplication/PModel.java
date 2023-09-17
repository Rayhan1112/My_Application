package com.myapplication;

public class PModel {
    String imageUri;
    String name;

    PModel(){}

    public PModel(String imageUri, String name) {
        this.imageUri = imageUri;
        this.name = name;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
