package com.test.memory.demo.repositories;

import com.test.memory.demo.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long>, JpaSpecificationExecutor<Payment> {

    // 1. Retrieve all payments.
    @Query(value = "SELECT * FROM payment", nativeQuery = true)
    List<Payment> findAllPaymentsNative();

    // 2. Retrieve a payment by its id.
    @Query(value = "SELECT * FROM payment WHERE id = :id", nativeQuery = true)
    Payment findPaymentByIdNative(@Param("id") Long id);

    // 3. Retrieve payments by status.
    @Query(value = "SELECT * FROM payment WHERE status = :status", nativeQuery = true)
    List<Payment> findPaymentsByStatusNative(@Param("status") String status);

    // 4. Retrieve payments by order id.
    @Query(value = "SELECT * FROM payment WHERE order_id = :orderId", nativeQuery = true)
    List<Payment> findPaymentsByOrderIdNative(@Param("orderId") Long orderId);

    // 5. Count all payments.
    @Query(value = "SELECT COUNT(*) FROM payment", nativeQuery = true)
    Long countPaymentsNative();

    // 6. Sum of payment IDs (for demonstration).
    @Query(value = "SELECT SUM(id) FROM payment", nativeQuery = true)
    Long sumPaymentIdsNative();

    // 7. Retrieve distinct statuses from payments.
    @Query(value = "SELECT DISTINCT status FROM payment", nativeQuery = true)
    List<String> findDistinctStatusesNative();

    // 8. Retrieve payments ordered by status ascending.
    @Query(value = "SELECT * FROM payment ORDER BY status ASC", nativeQuery = true)
    List<Payment> findPaymentsOrderByStatusAscNative();

    // 9. Retrieve payments ordered by status descending.
    @Query(value = "SELECT * FROM payment ORDER BY status DESC", nativeQuery = true)
    List<Payment> findPaymentsOrderByStatusDescNative();

    // 10. Retrieve payments with order details (join with customer_order table).
    @Query(value = "SELECT p.* FROM payment p JOIN customer_order co ON p.order_id = co.id", nativeQuery = true)
    List<Payment> findPaymentsWithOrderDetailsNative();

    // 11. Retrieve payments with order and user details (join with customer_order and app_user).
    @Query(value = "SELECT p.* FROM payment p " +
            "JOIN customer_order co ON p.order_id = co.id " +
            "JOIN app_user au ON co.user_id = au.id", nativeQuery = true)
    List<Payment> findPaymentsWithOrderAndUserNative();

    // 12. Using CTE: Retrieve payments with orders via a CTE.
    @Query(value = "WITH orders AS (SELECT id FROM customer_order) " +
            "SELECT p.* FROM payment p JOIN orders o ON p.order_id = o.id", nativeQuery = true)
    List<Payment> findPaymentsUsingCTE();

    // 13. Using CTE: Retrieve payments and count orders per status.
    @Query(value = "WITH status_cte AS (SELECT status, COUNT(*) AS cnt FROM payment GROUP BY status) " +
            "SELECT * FROM status_cte WHERE cnt > :minCount", nativeQuery = true)
    List<Object[]> findPaymentStatusCountsUsingCTE(@Param("minCount") int minCount);

    // 14. Retrieve payments with a status pattern.
    @Query(value = "SELECT * FROM payment WHERE status LIKE CONCAT('%', :pattern, '%')", nativeQuery = true)
    List<Payment> findPaymentsByStatusPatternNative(@Param("pattern") String pattern);

    // 15. Retrieve payments by status and order id.
    @Query(value = "SELECT * FROM payment WHERE status = :status AND order_id = :orderId", nativeQuery = true)
    List<Payment> findPaymentsByStatusAndOrderIdNative(@Param("status") String status, @Param("orderId") Long orderId);

    // 16. Retrieve payments with status in a given list.
    @Query(value = "SELECT * FROM payment WHERE status IN (:statuses)", nativeQuery = true)
    List<Payment> findPaymentsByStatusesNative(@Param("statuses") List<String> statuses);

    // 17. Retrieve payments with pagination (limit & offset).
    @Query(value = "SELECT * FROM payment LIMIT :limit OFFSET :offset", nativeQuery = true)
    List<Payment> findPaymentsWithPaginationNative(@Param("limit") int limit, @Param("offset") int offset);

    // 18. Retrieve payments ordered by id descending.
    @Query(value = "SELECT * FROM payment ORDER BY id DESC", nativeQuery = true)
    List<Payment> findPaymentsOrderByIdDescNative();

    // 19. Retrieve the latest payment (highest id).
    @Query(value = "SELECT * FROM payment ORDER BY id DESC LIMIT 1", nativeQuery = true)
    Payment findLatestPaymentNative();

    // 20. Retrieve the oldest payment (lowest id).
    @Query(value = "SELECT * FROM payment ORDER BY id ASC LIMIT 1", nativeQuery = true)
    Payment findOldestPaymentNative();

    // 21. Retrieve payments with non-null status.
    @Query(value = "SELECT * FROM payment WHERE status IS NOT NULL", nativeQuery = true)
    List<Payment> findPaymentsWithNonNullStatusNative();

    // 22. Retrieve payments with null order_id.
    @Query(value = "SELECT * FROM payment WHERE order_id IS NULL", nativeQuery = true)
    List<Payment> findPaymentsWithNullOrderIdNative();

    // 23. Retrieve payments using a CASE statement.
    @Query(value = "SELECT id, CASE WHEN status = 'COMPLETED' THEN 'Yes' ELSE 'No' END AS isCompleted FROM payment", nativeQuery = true)
    List<Object[]> findPaymentsUsingCaseNative();

    // 24. Group payments by status.
    @Query(value = "SELECT status, COUNT(*) FROM payment GROUP BY status", nativeQuery = true)
    List<Object[]> groupPaymentsByStatusNative();

    // 25. Retrieve payments having count greater than a threshold (using subquery).
    @Query(value = "SELECT * FROM payment WHERE status IN (SELECT status FROM payment GROUP BY status HAVING COUNT(*) > :threshold)", nativeQuery = true)
    List<Payment> findPaymentsHavingCountGreaterThanNative(@Param("threshold") int threshold);

    // 26. Retrieve payments joining with customer_order using a subquery.
    @Query(value = "SELECT * FROM payment WHERE order_id IN (SELECT id FROM customer_order WHERE id IS NOT NULL)", nativeQuery = true)
    List<Payment> findPaymentsWithValidOrderNative();

    // 27. Retrieve payments where id is within a range.
    @Query(value = "SELECT * FROM payment WHERE id BETWEEN :startId AND :endId", nativeQuery = true)
    List<Payment> findPaymentsByIdRangeNative(@Param("startId") Long startId, @Param("endId") Long endId);

    // 28. Retrieve payments using a UNION of two queries.
    @Query(value = "SELECT * FROM payment WHERE status = :status " +
            "UNION " +
            "SELECT * FROM payment WHERE order_id = :orderId", nativeQuery = true)
    List<Payment> findPaymentsUsingUnionNative(@Param("status") String status, @Param("orderId") Long orderId);

    // 29. Retrieve payments with an aggregate function (average id).
    @Query(value = "SELECT AVG(id) FROM payment", nativeQuery = true)
    Double findAveragePaymentIdNative();

    // 30. Retrieve payments with a custom condition on id.
    @Query(value = "SELECT * FROM payment WHERE id % 2 = 0", nativeQuery = true)
    List<Payment> findPaymentsWithEvenIdNative();

    // 31. Retrieve payments using COALESCE on status.
    @Query(value = "SELECT * FROM payment WHERE COALESCE(status, 'N/A') = :status", nativeQuery = true)
    List<Payment> findPaymentsUsingCoalesceNative(@Param("status") String status);

    // 32. Retrieve payments using NULLIF on order_id.
    @Query(value = "SELECT * FROM payment WHERE NULLIF(order_id, 0) IS NOT NULL", nativeQuery = true)
    List<Payment> findPaymentsUsingNullIfNative();

    // 33. Retrieve payments using GREATEST function.
    @Query(value = "SELECT * FROM payment WHERE GREATEST(id, order_id) > :value", nativeQuery = true)
    List<Payment> findPaymentsUsingGreatestNative(@Param("value") Long value);

    // 34. Retrieve payments using LEAST function.
    @Query(value = "SELECT * FROM payment WHERE LEAST(id, order_id) < :value", nativeQuery = true)
    List<Payment> findPaymentsUsingLeastNative(@Param("value") Long value);

    // 35. Retrieve payments with UPPER function on status.
    @Query(value = "SELECT * FROM payment WHERE UPPER(status) = UPPER(:status)", nativeQuery = true)
    List<Payment> findPaymentsUsingUpperNative(@Param("status") String status);

    // 36. Retrieve payments with LOWER function on status.
    @Query(value = "SELECT * FROM payment WHERE LOWER(status) = LOWER(:status)", nativeQuery = true)
    List<Payment> findPaymentsUsingLowerNative(@Param("status") String status);

    // 37. Retrieve payments using a subquery for statuses.
    @Query(value = "SELECT * FROM payment WHERE status IN (SELECT status FROM payment WHERE status IS NOT NULL)", nativeQuery = true)
    List<Payment> findPaymentsUsingInSubqueryNative();

    // 38. Retrieve payments excluding a set of statuses.
    @Query(value = "SELECT * FROM payment WHERE status NOT IN (:statuses)", nativeQuery = true)
    List<Payment> findPaymentsExcludingStatusesNative(@Param("statuses") List<String> statuses);

    // 39. Retrieve payments with a window function: row_number over ordered by id.
    @Query(value = "SELECT * FROM (SELECT p.*, ROW_NUMBER() OVER (ORDER BY id) AS rn FROM payment p) sub WHERE rn = :rowNum", nativeQuery = true)
    List<Payment> findPaymentByRowNumberNative(@Param("rowNum") int rowNum);

    // 40. Retrieve payments using a CTE with window function.
    @Query(value = "WITH ranked_payments AS ( " +
            "  SELECT p.*, ROW_NUMBER() OVER (ORDER BY id DESC) AS rn FROM payment p " +
            ") " +
            "SELECT * FROM ranked_payments WHERE rn <= :limit", nativeQuery = true)
    List<Payment> findTopNPaymentsUsingCTE(@Param("limit") int limit);

    // 41. Retrieve payments where status is not empty.
    @Query(value = "SELECT * FROM payment WHERE status <> ''", nativeQuery = true)
    List<Payment> findPaymentsWithNonEmptyStatusNative();

    // 42. Retrieve payments joining with customer_order and filtering on a sub-condition.
    @Query(value = "SELECT p.* FROM payment p " +
            "JOIN customer_order co ON p.order_id = co.id " +
            "WHERE co.id IN (SELECT id FROM customer_order WHERE id > :minOrderId)", nativeQuery = true)
    List<Payment> findPaymentsByCustomerOrderConditionNative(@Param("minOrderId") Long minOrderId);

    // 43. Retrieve payments where order_id is not in a subquery.
    @Query(value = "SELECT * FROM payment WHERE order_id NOT IN (SELECT id FROM customer_order WHERE user_id IS NULL)", nativeQuery = true)
    List<Payment> findPaymentsWithValidUserNative();

    // 44. Retrieve payments using a nested subquery for status.
    @Query(value = "SELECT * FROM payment WHERE status = (SELECT status FROM payment WHERE id = :refId)", nativeQuery = true)
    List<Payment> findPaymentsByNestedSubqueryNative(@Param("refId") Long refId);

    // 45. Retrieve payments with a custom ordering using CASE.
    @Query(value = "SELECT * FROM payment ORDER BY CASE WHEN status = 'COMPLETED' THEN 1 ELSE 2 END, id", nativeQuery = true)
    List<Payment> findPaymentsOrderedByCustomCaseNative();

    // 46. Retrieve payments using a recursive CTE (if supported).
    @Query(value = "WITH RECURSIVE payment_cte AS ( " +
            "  SELECT * FROM payment WHERE id = :startId " +
            "  UNION ALL " +
            "  SELECT p.* FROM payment p JOIN payment_cte pc ON p.order_id = pc.id " +
            ") " +
            "SELECT * FROM payment_cte", nativeQuery = true)
    List<Payment> findPaymentsUsingRecursiveCTE(@Param("startId") Long startId);

    // 47. Retrieve payments where id is in a given list.
    @Query(value = "SELECT * FROM payment WHERE id IN (:ids)", nativeQuery = true)
    List<Payment> findPaymentsByIdListNative(@Param("ids") List<Long> ids);

    // 48. Retrieve payments with a specific status and limit the result.
    @Query(value = "SELECT * FROM payment WHERE status = :status LIMIT :limit", nativeQuery = true)
    List<Payment> findPaymentsByStatusWithLimitNative(@Param("status") String status, @Param("limit") int limit);

    // 49. Retrieve payments with order_id not null.
    @Query(value = "SELECT * FROM payment WHERE order_id IS NOT NULL", nativeQuery = true)
    List<Payment> findPaymentsWithNonNullOrderIdNative();

    // 50. Retrieve payments using multiple joins (customer_order and app_user) with a CTE.
    @Query(value = "WITH order_users AS ( " +
            "  SELECT co.id AS orderId, au.name AS userName FROM customer_order co " +
            "  JOIN app_user au ON co.user_id = au.id " +
            ") " +
            "SELECT p.* FROM payment p " +
            "JOIN order_users ou ON p.order_id = ou.orderId " +
            "WHERE ou.userName LIKE CONCAT('%', :userNamePattern, '%')", nativeQuery = true)
    List<Payment> findPaymentsByUserNamePatternUsingCTE(@Param("userNamePattern") String userNamePattern);

}
