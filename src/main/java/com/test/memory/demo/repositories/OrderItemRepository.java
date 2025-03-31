package com.test.memory.demo.repositories;

import com.test.memory.demo.entities.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long>, JpaSpecificationExecutor<OrderItem> {
    // 1. Find order items by customer order id.
    @Query("SELECT oi FROM OrderItem oi WHERE oi.customerOrder.id = :orderId")
    List<OrderItem> findByOrderId(@Param("orderId") Long orderId);

    // 2. Find order items by product name.
    @Query("SELECT oi FROM OrderItem oi JOIN oi.product p WHERE p.name = :productName")
    List<OrderItem> findByProductName(@Param("productName") String productName);

    // 3. Find order items where product name contains a pattern.
    @Query("SELECT oi FROM OrderItem oi JOIN oi.product p WHERE p.name LIKE %:productName%")
    List<OrderItem> findByProductNameLike(@Param("productName") String productName);

    // 4. Find order items with quantity greater than a given value.
    @Query("SELECT oi FROM OrderItem oi WHERE oi.quantity > :quantity")
    List<OrderItem> findByQuantityGreaterThan(@Param("quantity") Integer quantity);

    // 5. Find order items with quantity less than a given value.
    @Query("SELECT oi FROM OrderItem oi WHERE oi.quantity < :quantity")
    List<OrderItem> findByQuantityLessThan(@Param("quantity") Integer quantity);

    // 6. Find order items with quantity between two values.
    @Query("SELECT oi FROM OrderItem oi WHERE oi.quantity BETWEEN :minQuantity AND :maxQuantity")
    List<OrderItem> findByQuantityBetween(@Param("minQuantity") Integer minQuantity, @Param("maxQuantity") Integer maxQuantity);

    // 7. Count order items for a given order.
    @Query("SELECT COUNT(oi) FROM OrderItem oi WHERE oi.customerOrder.id = :orderId")
    Long countByOrderId(@Param("orderId") Long orderId);

    // 8. Sum quantities of order items for a given order.
    @Query("SELECT SUM(oi.quantity) FROM OrderItem oi WHERE oi.customerOrder.id = :orderId")
    Long sumQuantitiesByOrderId(@Param("orderId") Long orderId);

    // 9. Find distinct order items by product id.
    @Query("SELECT DISTINCT oi FROM OrderItem oi WHERE oi.product.id = :productId")
    List<OrderItem> findDistinctByProductId(@Param("productId") Long productId);

    // 10. Find order items by order id and product id.
    @Query("SELECT oi FROM OrderItem oi WHERE oi.customerOrder.id = :orderId AND oi.product.id = :productId")
    List<OrderItem> findByOrderIdAndProductId(@Param("orderId") Long orderId, @Param("productId") Long productId);



    // 12. Find order items with quantity greater than or equal to min and less than or equal to max.
    @Query("SELECT oi FROM OrderItem oi WHERE oi.quantity >= :minQuantity AND oi.quantity <= :maxQuantity")
    List<OrderItem> findByQuantityMinAndMax(@Param("minQuantity") Integer minQuantity, @Param("maxQuantity") Integer maxQuantity);

    // 13. Find order items for an order ordered by quantity ascending.
    @Query("SELECT oi FROM OrderItem oi WHERE oi.customerOrder.id = :orderId ORDER BY oi.quantity ASC")
    List<OrderItem> findByOrderIdOrderByQuantityAsc(@Param("orderId") Long orderId);

    // 14. Find order items for an order ordered by quantity descending.
    @Query("SELECT oi FROM OrderItem oi WHERE oi.customerOrder.id = :orderId ORDER BY oi.quantity DESC")
    List<OrderItem> findByOrderIdOrderByQuantityDesc(@Param("orderId") Long orderId);

    // 15. Find order items by product price greater than a specified value.
    @Query("SELECT oi FROM OrderItem oi JOIN oi.product p WHERE p.price > :price")
    List<OrderItem> findByProductPriceGreaterThan(@Param("price") Double price);

    // 16. Find order items by product price less than a specified value.
    @Query("SELECT oi FROM OrderItem oi JOIN oi.product p WHERE p.price < :price")
    List<OrderItem> findByProductPriceLessThan(@Param("price") Double price);

    // 17. Find order items by product category name.
    @Query("SELECT oi FROM OrderItem oi JOIN oi.product p JOIN p.category c WHERE c.name = :categoryName")
    List<OrderItem> findByProductCategoryName(@Param("categoryName") String categoryName);

    // 18. Find order items by customer order's user id.
    @Query("SELECT oi FROM OrderItem oi JOIN oi.customerOrder co WHERE co.appUser.id = :userId")
    List<OrderItem> findByCustomerOrderUserId(@Param("userId") Long userId);

    // 19. Find order items by product name and exact quantity.
    @Query("SELECT oi FROM OrderItem oi JOIN oi.product p WHERE p.name = :productName AND oi.quantity = :quantity")
    List<OrderItem> findByProductNameAndQuantity(@Param("productName") String productName, @Param("quantity") Integer quantity);

    // 20. Find the order item with maximum quantity in a specific order.
    @Query("SELECT oi FROM OrderItem oi WHERE oi.customerOrder.id = :orderId AND oi.quantity = (SELECT MAX(oi2.quantity) FROM OrderItem oi2 WHERE oi2.customerOrder.id = :orderId)")
    OrderItem findMaxQuantityInOrder(@Param("orderId") Long orderId);

    // 21. Find the order item with minimum quantity in a specific order.
    @Query("SELECT oi FROM OrderItem oi WHERE oi.customerOrder.id = :orderId AND oi.quantity = (SELECT MIN(oi2.quantity) FROM OrderItem oi2 WHERE oi2.customerOrder.id = :orderId)")
    OrderItem findMinQuantityInOrder(@Param("orderId") Long orderId);

    // 22. Group order items by product id and count them.
    @Query("SELECT oi.product.id, COUNT(oi) FROM OrderItem oi GROUP BY oi.product.id")
    List<Object[]> groupByProductIdCount();

    // 23. Find order items by order id with quantity greater than a specified value.
    @Query("SELECT oi FROM OrderItem oi WHERE oi.customerOrder.id = :orderId AND oi.quantity > :quantity")
    List<OrderItem> findByOrderIdAndQuantityGreaterThan(@Param("orderId") Long orderId, @Param("quantity") Integer quantity);

    // 24. Find order items by order id with quantity less than a specified value.
    @Query("SELECT oi FROM OrderItem oi WHERE oi.customerOrder.id = :orderId AND oi.quantity < :quantity")
    List<OrderItem> findByOrderIdAndQuantityLessThan(@Param("orderId") Long orderId, @Param("quantity") Integer quantity);

    // 25. Find order items for a given product id ordered by quantity.
    @Query("SELECT oi FROM OrderItem oi WHERE oi.product.id = :productId ORDER BY oi.quantity")
    List<OrderItem> findByProductIdOrderByQuantity(@Param("productId") Long productId);

    // 26. Find distinct order items for a given order id.
    @Query("SELECT DISTINCT oi FROM OrderItem oi WHERE oi.customerOrder.id = :orderId")
    List<OrderItem> findDistinctByOrderId(@Param("orderId") Long orderId);

    // 27. Find order items by product id with quantity between two values.
    @Query("SELECT oi FROM OrderItem oi WHERE oi.product.id = :productId AND oi.quantity BETWEEN :minQuantity AND :maxQuantity")
    List<OrderItem> findByProductIdAndQuantityBetween(@Param("productId") Long productId,
                                                      @Param("minQuantity") Integer minQuantity,
                                                      @Param("maxQuantity") Integer maxQuantity);

    // 28. Find order items with a specific quantity.
    @Query("SELECT oi FROM OrderItem oi WHERE oi.quantity = :quantity")
    List<OrderItem> findByQuantity(@Param("quantity") Integer quantity);

    // 29. Find order items with quantity not equal to a specific value.
    @Query("SELECT oi FROM OrderItem oi WHERE oi.quantity <> :quantity")
    List<OrderItem> findByQuantityNotEqual(@Param("quantity") Integer quantity);

    // 30. Find order items for an order with non-null quantity.
    @Query("SELECT oi FROM OrderItem oi WHERE oi.customerOrder.id = :orderId AND oi.quantity IS NOT NULL")
    List<OrderItem> findByOrderIdWithNonNullQuantity(@Param("orderId") Long orderId);

    // 31. Find order items for a product with non-null quantity.
    @Query("SELECT oi FROM OrderItem oi WHERE oi.product.id = :productId AND oi.quantity IS NOT NULL")
    List<OrderItem> findByProductIdWithNonNullQuantity(@Param("productId") Long productId);

    // 32. Find order items by product name starting with a given prefix.
    @Query("SELECT oi FROM OrderItem oi JOIN oi.product p WHERE p.name LIKE CONCAT(:prefix, '%')")
    List<OrderItem> findByProductNameStartingWith(@Param("prefix") String prefix);

    // 33. Find order items by product name ending with a given suffix.
    @Query("SELECT oi FROM OrderItem oi JOIN oi.product p WHERE p.name LIKE CONCAT('%', :suffix)")
    List<OrderItem> findByProductNameEndingWith(@Param("suffix") String suffix);

    // 34. Find order items by order id and product name pattern.
    @Query("SELECT oi FROM OrderItem oi JOIN oi.product p WHERE oi.customerOrder.id = :orderId AND p.name LIKE %:productName%")
    List<OrderItem> findByOrderIdAndProductNameLike(@Param("orderId") Long orderId, @Param("productName") String productName);

    // 35. Find order items by order id and exact product name.
    @Query("SELECT oi FROM OrderItem oi JOIN oi.product p WHERE oi.customerOrder.id = :orderId AND p.name = :productName")
    List<OrderItem> findByOrderIdAndProductName(@Param("orderId") Long orderId, @Param("productName") String productName);

    // 36. Find order items by customer order's user name.
    @Query("SELECT oi FROM OrderItem oi JOIN oi.customerOrder co JOIN co.appUser u WHERE u.name = :userName")
    List<OrderItem> findByCustomerOrderUserName(@Param("userName") String userName);

    // 37. Find order items by a list of product ids.
    @Query("SELECT oi FROM OrderItem oi WHERE oi.product.id IN :productIds")
    List<OrderItem> findByProductIds(@Param("productIds") List<Long> productIds);

    // 38. Find order items by a list of order ids.
    @Query("SELECT oi FROM OrderItem oi WHERE oi.customerOrder.id IN :orderIds")
    List<OrderItem> findByOrderIds(@Param("orderIds") List<Long> orderIds);

    // 39. Find order items whose quantity is a multiple of a given number.
    @Query("SELECT oi FROM OrderItem oi WHERE MOD(oi.quantity, :multiple) = 0")
    List<OrderItem> findByQuantityMultipleOf(@Param("multiple") Integer multiple);

    // 40. Find order items with an even quantity.
    @Query("SELECT oi FROM OrderItem oi WHERE MOD(oi.quantity, 2) = 0")
    List<OrderItem> findByEvenQuantity();

    // 41. Find order items with an odd quantity.
    @Query("SELECT oi FROM OrderItem oi WHERE MOD(oi.quantity, 2) = 1")
    List<OrderItem> findByOddQuantity();

}
