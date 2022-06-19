package com.rajblowplast.digital.sms.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@JsonIgnoreProperties
@Document("ITEM_DETAILS")
public class ItemDetails {
    @Id
    private String id;
    private String skuID;
    private String name;

    public ItemDetails(String skuID, String name) {
        this.skuID = skuID;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSkuID() {
        return skuID;
    }

    public void setSkuID(String skuID) {
        this.skuID = skuID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ItemDetails{" +
                "skuID='" + skuID + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
