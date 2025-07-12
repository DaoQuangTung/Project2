package com.javaweb.api;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.javaweb.model.BuildingDTO;
import com.javaweb.service.BuildingService;

import customexceptions.FieldRequiredException;

@RestController
public class BuildingAPI {
	@Autowired
	private BuildingService buildingService;

	@GetMapping(value = "/api/building/")
	public List<BuildingDTO> getBuilding(@RequestParam Map<String, Object> params) {
		List<BuildingDTO> result = buildingService.findAll(params);
		return result;
	}

	public void valiDate(BuildingDTO buildingDTO) {
		if (buildingDTO.getName() == null || buildingDTO.getNumberOfBasement() == null
				|| buildingDTO.getName().equals("")) {
			throw new FieldRequiredException("Name or numberOfBasemenet is null");
		}
	}

//	@PostMapping(value = "/api/building/")
//	public BuildingDTO getBuilding2(@RequestParam BuildingDTO buildingDTO) {
//		// xu ly duoi DB
//		return buildingDTO;
//	}

	@DeleteMapping("/api/building/{id}")
	public void deleteBuilding(@PathVariable Integer id) {
		System.out.println("Da xoa toa nha co id la " + id + " roi nha!");
	}
}
