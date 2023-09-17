package com.myapplication;

public class PetData {
    String DogName;
    String Weight;
    String Breed;
    String Color;

    public PetData(){}
    public PetData(String name,String Weight,String Breed, String Color){
        this.DogName=name;
        this.Weight=Weight;
        this.Breed=Breed;
        this.Color=Color;
    }

    public String getDogName() {
        return DogName;
    }

    public void setDogName(String dogName) {
        DogName = dogName;
    }

    public String getWeight() {
        return Weight;
    }

    public void setWeight(String weight) {
        Weight = weight;
    }

    public String getBreed() {
        return Breed;
    }

    public void setBreed(String breed) {
        Breed = breed;
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String color) {
        Color = color;
    }
}
