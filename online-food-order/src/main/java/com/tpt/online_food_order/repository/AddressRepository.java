package com.tpt.online_food_order.repository;

import com.tpt.online_food_order.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
