package com.test.memory.demo.repositories;

import com.test.memory.demo.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long>, JpaSpecificationExecutor<Review> {
    @Query("SELECT r FROM Review r WHERE r.product.id = :productId AND r.rating >= :minRating")
    List<Review> findByProductAndMinRating(Long productId, Integer minRating);
}
