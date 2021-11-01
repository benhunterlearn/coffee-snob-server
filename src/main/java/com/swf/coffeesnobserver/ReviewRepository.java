package com.swf.coffeesnobserver;

import org.springframework.data.repository.CrudRepository;

public interface ReviewRepository extends CrudRepository<Review, Long> {
    public Iterable<Review> findReviewsByShopIdIs(Long id);
}
