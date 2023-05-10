package com.example.pizzaq;

public class Pizza {
    private int id;
    private String name;
    private int price;
    private int calories;
    private String ingredients;
    private String img_id;
    private int amount;

    public Pizza(int id, String name, int price, int calories, String ingredients, String img_id) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.calories = calories;
        this.ingredients = ingredients;
        this.img_id = img_id;
        amount = 0;
    }

    public int add(int id) {
        if (this.id == id) this.amount++;
        return this.amount;
    }

    public int remove(int id) {
        if (this.amount > 0 && this.id == id) this.amount--;
        return this.amount;
    }

    // Getters
    public int getId() {
        return this.id;
    }
    public String getName() {
        return this.name;
    }
    public int getPrice() {
        return this.price;
    }
    public int getCalories() {
        return this.calories;
    }
    public String getIngredients() {
        return this.ingredients;
    }
    public String getImgId() {
        return this.img_id;
    }
    public int getAmount() {
        return this.amount;
    }
}
