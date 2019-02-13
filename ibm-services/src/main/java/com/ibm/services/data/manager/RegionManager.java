package com.ibm.services.data.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ibm.services.data.entity.Region;
import com.ibm.services.data.repo.IRegionRepo;

@Component
public class RegionManager {
	
	@Autowired
	IRegionRepo regionRepo;
	
	public List<Region> findAll() {
		return regionRepo.findAll();
	}

}
