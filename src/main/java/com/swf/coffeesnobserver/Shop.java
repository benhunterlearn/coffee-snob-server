package com.swf.coffeesnobserver;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@Accessors(chain = true)
public class Shop {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    private String name;
    private String address;
    private String website;

    public void patch(Shop updatedShop) {
        if (updatedShop.getName() != null) {
            this.name = updatedShop.getName();
        }
        if (updatedShop.getAddress() != null) {
            this.address = updatedShop.getAddress();
        }
        if (updatedShop.getWebsite() != null) {
            this.website = updatedShop.getWebsite();
        }
    }
}
