package com.ibm.services.data.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ibm.services.data.entity.Vendor;

public interface IVendorRepo extends JpaRepository<Vendor, Long> {

}
