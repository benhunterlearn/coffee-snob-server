package com.swf.coffeesnobserver;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Accessors(chain = true)
public class Shop {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    private String name;
    private String address;
    private String website;

    @OneToMany(mappedBy = "shop")
    private List<Review> reviews;

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
