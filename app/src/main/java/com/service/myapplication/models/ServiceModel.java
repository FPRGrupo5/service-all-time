package com.service.myapplication.models;

public class ServiceModel {
    private String uid;
    private String name;
    private String description;
    private String imageUrl;
    private String category;
    private String openingHours; //horario de atención
    private String dateCreated;
    private int laborCost; //costo de la mano de obra
    private int toolsWearCost; //costo del desgaste de las herramientas

    public ServiceModel(String uid, String name, String dateCreated) {
        this.uid = uid;
        this.name = name;
        this.dateCreated = dateCreated;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(String openingHours) {
        this.openingHours = openingHours;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public int getLaborCost() {
        return laborCost;
    }

    public void setLaborCost(int laborCost) {
        this.laborCost = laborCost;
    }

    public int getToolsWearCost() {
        return toolsWearCost;
    }

    public void setToolsWearCost(int toolsWearCost) {
        this.toolsWearCost = toolsWearCost;
    }
}
