package com.swf.coffeesnobserver;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/review")
@CrossOrigin(origins = "http://localhost:3000")
public class ReviewController {
    private final ReviewRepository reviewRepository;
    private final ShopRepository shopRepository;

    public ReviewController(ReviewRepository reviewRepository, ShopRepository shopRepository) {
        this.reviewRepository = reviewRepository;
        this.shopRepository = shopRepository;
    }

    @GetMapping("shop/{id}")
    public Iterable<Review> getReviewsForShopByShopId(@PathVariable Long id) {
        return reviewRepository.findReviewsByShopIdIs(id);
    }

    @PostMapping("shop/{id}")
    public Review postCreateReview(@PathVariable("id") Long shopId, @RequestBody Review newReview) {
        newReview.setShop(shopRepository.findById(shopId).get());
        return reviewRepository.save(newReview);
    }

}
