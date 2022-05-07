package com.example.vehiclerental;

 public class Vehicle {
    private String name;
    private String rating;
    private String type;
    private String image;
    private String phone;
    private String description;
    private String category;



    public Vehicle(){}
    public Vehicle(String name, String image){
        this.name = name;
        this.image = image;
    }

     public String getRating() {
         return rating;
     }

     public String getCategory() {
         return category;
     }

     public void setCategory(String category) {
         this.category = category;
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

     public String getPhone() {
         return phone;
     }

     public void setPhone(String phone) {
         this.phone = phone;
     }

     public void setType(String type) {
         this.type = type;
     }

     public void setImage(String image) {
        this.image = image;
    }

}
