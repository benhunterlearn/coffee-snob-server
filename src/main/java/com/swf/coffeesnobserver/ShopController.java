package com.swf.coffeesnobserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shop")
public class ShopController {
    @Autowired
    ShopRepository repository;

    @GetMapping("")
    public Iterable<Shop> getAllShops() {
        return this.repository.findAll();
    }

    @GetMapping("{id}")
    public Shop getShopById(@PathVariable Long id) {
        return this.repository.findById(id).get();
    }

    @PostMapping("")
    public Shop createShop(@RequestBody Shop newShop) {
        return this.repository.save(newShop);
    }

    @PatchMapping("{id}")
    public Shop patchShopById(@PathVariable Long id, @RequestBody Shop updatedShop) {
        Shop currentShop = this.repository.findById(id).get();
        currentShop.patch(updatedShop);
        currentShop = this.repository.save(currentShop);
        return currentShop;
    }

    @DeleteMapping("{id}")
    public void deleteShopById(@PathVariable Long id) {
        this.repository.deleteById(id);
    }
}
