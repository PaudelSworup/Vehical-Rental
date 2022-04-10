package com.example.vehiclerental;

 public class Vehicle {
    private String name;
    private String rating;
    private String type;
    private String image;
    private String description;



    public Vehicle(){}
    public Vehicle(String name, String image){
        this.name = name;
        this.image = image;
    }

     public String getRating() {
         return rating;
     }

     public void setRating(String rating) {
         this.rating = rating;
     }

     public String getDescription() {
         return description;
     }

     public void setDescription(String description) {
         this.description = description;
     }

     public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

     public String getType() {
         return type;
     }

     public void setType(String type) {
         this.type = type;
     }

     public void setImage(String image) {
        this.image = image;
    }

}
