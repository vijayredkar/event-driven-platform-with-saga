package com.banknext.account.mgt.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.banknext.account.mgt.repo.model.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

}