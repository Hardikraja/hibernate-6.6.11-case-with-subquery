package com.test.memory.demo.repositories;

import com.test.memory.demo.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AppUserRepository extends JpaRepository<AppUser, Long>, JpaSpecificationExecutor<AppUser> {
    // 1. Find users whose name contains a given substring.
    @Query("SELECT u FROM AppUser u WHERE u.name LIKE %:name%")
    List<AppUser> findByNameContains(@Param("name") String name);

    // 2. Find users with an exact name match.
    @Query("SELECT u FROM AppUser u WHERE u.name = :name")
    List<AppUser> findByExactName(@Param("name") String name);

    // 3. Find users whose name starts with a specific prefix.
    @Query("SELECT u FROM AppUser u WHERE u.name LIKE CONCAT(:prefix, '%')")
    List<AppUser> findByNameStartingWith(@Param("prefix") String prefix);

    // 4. Find users whose name ends with a specific suffix.
    @Query("SELECT u FROM AppUser u WHERE u.name LIKE CONCAT('%', :suffix)")
    List<AppUser> findByNameEndingWith(@Param("suffix") String suffix);

    // 5. Find users who have placed at least one order.
    @Query("SELECT DISTINCT u FROM AppUser u JOIN u.orders o")
    List<AppUser> findUsersWithOrders();

    // 6. Find users without any orders.
    @Query("SELECT u FROM AppUser u LEFT JOIN u.orders o WHERE o IS NULL")
    List<AppUser> findUsersWithoutOrders();

    // 7. Find users by filtering on the city of one of their addresses.
    @Query("SELECT DISTINCT u FROM AppUser u JOIN u.addresses a WHERE a.city = :city")
    List<AppUser> findUsersByAddressCity(@Param("city") String city);

    // 8. Find users with at least one review having a rating greater than a given value.
    @Query("SELECT DISTINCT u FROM AppUser u JOIN u.reviews r WHERE r.rating > :rating")
    List<AppUser> findUsersByReviewRatingGreaterThan(@Param("rating") Integer rating);

    // 9. Count the number of orders for each user.
    @Query("SELECT u, COUNT(o) FROM AppUser u LEFT JOIN u.orders o GROUP BY u")
    List<Object[]> countUsersWithOrders();

    // 10. Retrieve users ordered by name in ascending order.
    @Query("SELECT u FROM AppUser u ORDER BY u.name ASC")
    List<AppUser> findUsersOrderedByNameAsc();

    // 11. Retrieve users ordered by name in descending order.
    @Query("SELECT u FROM AppUser u ORDER BY u.name DESC")
    List<AppUser> findUsersOrderedByNameDesc();

    // 12. Find users having at least a specified number of orders.
    @Query("SELECT u FROM AppUser u WHERE SIZE(u.orders) >= :orderCount")
    List<AppUser> findUsersWithAtLeastNOrders(@Param("orderCount") int orderCount);

    // 13. Find users with a specific name and at least a given number of orders.
    @Query("SELECT u FROM AppUser u LEFT JOIN u.orders o GROUP BY u HAVING u.name = :name AND COUNT(o) >= :orderCount")
    List<AppUser> findUsersByNameAndOrderCount(@Param("name") String name, @Param("orderCount") long orderCount);

    // 14. Find users whose name contains a given substring and have at least one review with an exact rating.
    @Query("SELECT DISTINCT u FROM AppUser u JOIN u.reviews r WHERE u.name LIKE %:name% AND r.rating = :rating")
    List<AppUser> findUsersByMultipleCriteria(@Param("name") String name, @Param("rating") Integer rating);

    // 15. Find users whose orders include a shipment.
    @Query("SELECT DISTINCT u FROM AppUser u JOIN u.orders o JOIN o.shipment s")
    List<AppUser> findUsersWithShipment();

    // 16. Find users whose orders include a payment.
    @Query("SELECT DISTINCT u FROM AppUser u JOIN u.orders o JOIN o.payment p")
    List<AppUser> findUsersWithPayment();

    // 17. Find users whose orders include order items.
    @Query("SELECT DISTINCT u FROM AppUser u JOIN u.orders o JOIN o.orderItems oi")
    List<AppUser> findUsersWithOrderItems();

    // 18. Find users who have purchased products from a specific category.
    @Query("SELECT DISTINCT u FROM AppUser u JOIN u.orders o JOIN o.orderItems oi JOIN oi.product p JOIN p.category c WHERE c.name = :categoryName")
    List<AppUser> findUsersByCategory(@Param("categoryName") String categoryName);

    // 19. Find users who received discounts based on a discount code pattern.
    @Query("SELECT DISTINCT u FROM AppUser u JOIN u.orders o JOIN o.orderItems oi JOIN oi.product p JOIN p.discounts d WHERE d.code LIKE %:code%")
    List<AppUser> findUsersByDiscount(@Param("code") String code);

    // 20. Find users who have both reviews and orders.
    @Query("SELECT DISTINCT u FROM AppUser u JOIN u.orders o JOIN u.reviews r")
    List<AppUser> findUsersWithReviewsAndOrders();



    @Query(value = """
            SELECT DISTINCT u.id
            FROM AppUser u
            LEFT JOIN u.addresses a
            LEFT JOIN u.orders o
            LEFT JOIN o.orderItems oi
            LEFT JOIN oi.product p
            LEFT JOIN p.category c
            LEFT JOIN p.discounts d
            WHERE u.id = :userId
            AND (
                CASE
                    WHEN u.name = 'SPECIAL_USER' THEN TRUE
                    ELSE (
                        CASE
                            WHEN a.city = 'New York' THEN TRUE
                            ELSE (
                                c.name = 'Electronics'
                                OR d.code LIKE '%DISC%'
                                OR u.id IN (
                                    SELECT u2.id
                                    FROM AppUser u2
                                    JOIN u2.orders o2
                                    JOIN o2.orderItems oi2
                                    JOIN oi2.product p2
                                    WHERE p2.price > (SELECT AVG(p3.price) FROM Product p3)
                                )
                            )
                        END
                    )
                END
            )
            """)
    List<Long> findComplexAppUserByUserId(@Param("userId") Long userId);
}
