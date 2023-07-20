package com.saif.servicebilling.dao;

import com.saif.servicebilling.entities.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.webmvc.RepositoryRestController;

@RepositoryRestResource
public interface BillRepository extends JpaRepository<Bill, Long> {
}
