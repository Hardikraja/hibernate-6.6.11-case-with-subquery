package com.test.memory.demo.repositories;

import com.test.memory.demo.entities.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, Long>, JpaSpecificationExecutor<CustomerOrder> {
    @Query("SELECT o FROM CustomerOrder o WHERE o.appUser.id = :userId")
    List<CustomerOrder> findByUserId(Long userId);
}
