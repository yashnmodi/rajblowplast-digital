package com.rajblowplast.digital.sms.model;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Document("DISPATCH_DETAILS")
public class DispatchDetails {

    @Id
    private String id;

    private String item;
    private String colour;
    private String weight;
    private String productType;
    private int quantity;
    private String dispatchTime;

    public DispatchDetails(String id, String item, String colour, String weight, String productType, int quantity, String dispatchTime) {
        this.id = id;
        this.item = item;
        this.colour = colour;
        this.weight = weight;
        this.productType = productType;
        this.quantity = quantity;
        this.dispatchTime = dispatchTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDispatchTime() {
        return dispatchTime;
    }

    public void setDispatchTime(String dispatchTime) {
        this.dispatchTime = dispatchTime;
    }

    @Override
    public String toString() {
        return "DispatchDetails{" +
                "item='" + item + '\'' +
                ", colour='" + colour + '\'' +
                ", weight='" + weight + '\'' +
                ", productType='" + productType + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
