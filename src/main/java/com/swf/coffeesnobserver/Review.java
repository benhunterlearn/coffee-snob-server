package com.swf.coffeesnobserver;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Entity
@Data
@Accessors(chain = true)
public class Review {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    private String text;
    private String author;

    // Key to Shop table, required
    @ManyToOne
    @JoinColumn(name = "shop_id")
    private Shop shop;
}
