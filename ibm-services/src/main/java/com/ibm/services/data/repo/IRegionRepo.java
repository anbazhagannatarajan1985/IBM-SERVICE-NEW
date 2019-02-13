package com.ibm.services.data.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ibm.services.data.entity.Region;

public interface IRegionRepo extends JpaRepository<Region, String> {

}
