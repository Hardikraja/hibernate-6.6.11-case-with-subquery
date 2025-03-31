package com.test.memory.demo.repositories;

import com.test.memory.demo.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long>, JpaSpecificationExecutor<Address> {

    // 1. Find addresses by a city pattern.
    @Query("SELECT a FROM Address a WHERE a.city LIKE %:city%")
    List<Address> findAddressesByCity(@Param("city") String city);

    // 2. Find addresses by the associated user ID.
    @Query("SELECT a FROM Address a WHERE a.appUser.id = :userId")
    List<Address> findAddressesByUserId(@Param("userId") Long userId);

    // 3. Find addresses where the associated user has placed at least one order.
    @Query("SELECT DISTINCT a FROM Address a JOIN a.appUser u JOIN CustomerOrder o ON o.appUser = u")
    List<Address> findAddressesOfUsersWithOrders();

    // 4. Fetch addresses along with their user's details using a fetch join.
    @Query("SELECT a FROM Address a JOIN FETCH a.appUser u WHERE u.name = :userName")
    List<Address> findAddressesByUserName(@Param("userName") String userName);

    // 5. Retrieve all addresses ordered by the city in ascending order.
    @Query("SELECT a FROM Address a ORDER BY a.city ASC")
    List<Address> findAllAddressesOrderByCity();

    // 6. Count addresses grouped by city.
    @Query("SELECT a.city, COUNT(a) FROM Address a GROUP BY a.city")
    List<Object[]> countAddressesByCity();

    // 7. Find addresses with a street that matches a given pattern.
    @Query("SELECT a FROM Address a WHERE a.street LIKE %:street%")
    List<Address> findAddressesByStreet(@Param("street") String street);

    // 8. Find addresses where the associated user has at least one review.
    @Query("SELECT DISTINCT a FROM Address a JOIN a.appUser u WHERE EXISTS (SELECT r FROM Review r WHERE r.appUser = u)")
    List<Address> findAddressesByUsersWithReviews();

    // 9. Find distinct addresses by joining with their associated user.
    @Query("SELECT DISTINCT a FROM Address a JOIN a.appUser u")
    List<Address> findDistinctAddressesByUser();

    // 10. Find addresses by joining with orders to ensure the user has related orders.
    @Query("SELECT DISTINCT a FROM Address a JOIN a.appUser u JOIN CustomerOrder o ON o.appUser = u WHERE o IS NOT NULL")
    List<Address> findAddressesWithOrders();

}
