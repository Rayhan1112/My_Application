package com.myapplication;

public class Model {

    private String userID;
    private String userName;

    private String imageUri;
    private String name;
    private String breed;
    private String color;
    private String weight;


    public Model(){

    }

    public Model(String userID,String userName,String imageUri, String name,String breed,String color,String weight){
        this.userID=userID;
        this.userName=userName;
        this.imageUri=imageUri;
        this.name=name;
        this.breed=breed;
        this.color=color;
        this.weight=weight;


    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }
}
