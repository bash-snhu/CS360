package com.zybooks.mobile2appinventory;

public class InventoryModel {
    int id;
    String name;
    String description;
    String count;

    public InventoryModel(String id, String name, String description, String count) {
        this.id = Integer.parseInt(id);
        this.name = name;
        this.description = description;
        this.count = count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
