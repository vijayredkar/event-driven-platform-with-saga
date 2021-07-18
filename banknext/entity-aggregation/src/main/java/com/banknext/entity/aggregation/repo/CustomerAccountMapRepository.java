package com.banknext.entity.aggregation.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.banknext.entity.aggregation.repo.model.CustomerAccountMap;


@Repository
public interface CustomerAccountMapRepository extends JpaRepository<CustomerAccountMap, Integer> {

}