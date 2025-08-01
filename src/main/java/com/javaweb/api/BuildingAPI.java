package com.javaweb.api;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.javaweb.model.BuildingDTO;
import com.javaweb.model.BuildingRequestDTO;
import com.javaweb.repository.BuildingRepository;
import com.javaweb.repository.entity.BuildingEntity;
import com.javaweb.repository.entity.DistrictEntity;
import com.javaweb.service.BuildingService;

import customexceptions.FieldRequiredException;

@RestController
@PropertySource("classpath:application.properties")
public class BuildingAPI {
	@Autowired
	private BuildingService buildingService;
	
	@Autowired
	private BuildingRepository buildingRepository;
	@Value("${dev.dao}")
	private String data;
	@PersistenceContext
	private EntityManager entityManager;

	@GetMapping(value = "/api/building/")
	public List<BuildingDTO> getBuilding(@RequestParam Map<String, Object> params,
			@RequestParam(name = "typeCode", required = false) List<String> typeCode) {
		List<BuildingDTO> result = buildingService.findAll(params, typeCode);
		return result;
	}

	public void valiDate(BuildingDTO buildingDTO) {
		if (buildingDTO.getName() == null || buildingDTO.getName().equals("")) {
			throw new FieldRequiredException("Name or numberOfBasemenet is null");
		}
	}

//	@PostMapping(value = "/api/building/")
//	public BuildingDTO getBuilding2(@RequestParam BuildingDTO buildingDTO) {
//		// xu ly duoi DB
//		return buildingDTO;
//	}
	@PostMapping(value = "/api/building/")
	@Transactional
	public void createBuilding(@RequestBody BuildingRequestDTO buildingRequestDTO) {
		BuildingEntity buildingEntity = new BuildingEntity();
		buildingEntity.setName(buildingRequestDTO.getName());
		buildingEntity.setStreet(buildingRequestDTO.getStreet());
		buildingEntity.setWard(buildingRequestDTO.getWard());
		DistrictEntity districtEntity = new DistrictEntity();
		districtEntity.setId(buildingRequestDTO.getDistrictId());
		buildingEntity.setDistrict(districtEntity);
		entityManager.persist(buildingEntity);
		System.out.println("ok");
	}
	@PutMapping(value = "/api/building/")
	public void updateBuilding(@RequestBody BuildingRequestDTO buildingRequestDTO) {
		BuildingEntity buildingEntity = buildingRepository.findById(buildingRequestDTO.getId()).get();
		buildingEntity.setName(buildingRequestDTO.getName());
		buildingEntity.setStreet(buildingRequestDTO.getStreet());
		buildingEntity.setWard(buildingRequestDTO.getWard());
		DistrictEntity districtEntity = new DistrictEntity();
		districtEntity.setId(buildingRequestDTO.getDistrictId());
		buildingEntity.setDistrict(districtEntity);
		buildingRepository.save(buildingEntity);
		System.out.println("ok");
	}

	@DeleteMapping("/api/building/{ids}")
	@Transactional
	public void deleteBuilding(@PathVariable Long[] ids) {
		buildingRepository.deleteByIdIn(ids);
	}
	@GetMapping(value = "/api/building/{name}/{street}")
	public BuildingDTO getBuildingById(@PathVariable String name,
			@PathVariable String street) {
		BuildingDTO result = new BuildingDTO();
		List<BuildingEntity> building = buildingRepository.findByNameContainingAndStreet(name, street);
		return result;
	}
}
