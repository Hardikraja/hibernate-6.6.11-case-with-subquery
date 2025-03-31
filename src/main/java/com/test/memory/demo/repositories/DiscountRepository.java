package com.test.memory.demo.repositories;

import com.test.memory.demo.entities.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DiscountRepository extends JpaRepository<Discount, Long>, JpaSpecificationExecutor<Discount> {

    // 1. Find discounts by a code pattern.
    @Query("SELECT d FROM Discount d WHERE d.code LIKE %:codePattern%")
    List<Discount> findDiscountsByCodePattern(@Param("codePattern") String codePattern);

    // 2. Find discounts where the percentage is between a minimum and maximum.
    @Query("SELECT d FROM Discount d WHERE d.percentage BETWEEN :minPerc AND :maxPerc")
    List<Discount> findDiscountsByPercentageRange(@Param("minPerc") Double minPerc, @Param("maxPerc") Double maxPerc);

    // 3. Find discounts for products with an exact name.
    @Query("SELECT d FROM Discount d JOIN d.product p WHERE p.name = :productName")
    List<Discount> findDiscountsByProductName(@Param("productName") String productName);

    // 4. Retrieve discounts along with product details using a fetch join.
    @Query("SELECT d FROM Discount d JOIN FETCH d.product p")
    List<Discount> findAllDiscountsWithProduct();

    // 5. Find discounts where the associated product's price is greater than a given value.
    @Query("SELECT d FROM Discount d JOIN d.product p WHERE p.price > :price")
    List<Discount> findDiscountsByProductPriceGreaterThan(@Param("price") Double price);

    // 6. Find discounts where the associated product's category has a specific name.
    @Query("SELECT d FROM Discount d JOIN d.product p JOIN p.category c WHERE c.name = :categoryName")
    List<Discount> findDiscountsByProductCategory(@Param("categoryName") String categoryName);

    // 7. Find discounts for products that have at least one order item.
    @Query("SELECT DISTINCT d FROM Discount d JOIN d.product p JOIN p.orderItems oi")
    List<Discount> findDiscountsForProductsWithOrderItems();

    // 8. Find discounts for products that have reviews with an average rating above a given value.
    @Query("SELECT d FROM Discount d JOIN d.product p JOIN p.reviews r GROUP BY d, p HAVING AVG(r.rating) > :minAvgRating")
    List<Discount> findDiscountsByProductAvgReviewRating(@Param("minAvgRating") Double minAvgRating);

    // 9. Count the number of discounts available for each product.
    @Query("SELECT p.name, COUNT(d) FROM Discount d JOIN d.product p GROUP BY p.name")
    List<Object[]> countDiscountsByProduct();

    // 10. Find distinct discounts for products that have a non-null discount code.
    @Query("SELECT DISTINCT d FROM Discount d JOIN d.product p WHERE d.code IS NOT NULL")
    List<Discount> findDistinctDiscountsWithCode();

}
