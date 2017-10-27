package be.ste.st.datasheet.rest.service;


import java.util.List;

import be.ste.ts.datasheet.dto.DTOEmploye;
import be.steformations.java_data.timesheets.entities.Employee;
import be.steformations.java_data.timesheets.service.TimesheetsDataService;

@javax.ws.rs.Path("service")
public class TimeSheetService {
	private TimesheetsDataService dao;
	
	public TimeSheetService() {
		System.out.println("TimeSheetService.TimeSheetService()");
		this.dao= DaoRestFactory.getInstance().getDao();
	}
	
	@javax.ws.rs.GET
	@javax.ws.rs.Path("employe/{id:[1-9]+}")
	@javax.ws.rs.Produces(javax.ws.rs.core.MediaType.APPLICATION_XML)
	public javax.ws.rs.core.Response getEmployeById(
			@javax.ws.rs.PathParam("id") int id) {
		javax.ws.rs.core.Response response = null;
		Employee emp = this.dao.findOneEmployeeById(id);
		if (emp == null) {
			response = javax.ws.rs.core.Response.status(404).build();
		} else {
			DTOEmploye dto = DTOBuilder.build(emp);
			response = javax.ws.rs.core.Response.ok(dto).build();
		}
		return response;
	}
	
	@javax.ws.rs.Path("employe/list")
	@javax.ws.rs.GET
	@javax.ws.rs.Produces(javax.ws.rs.core.MediaType.APPLICATION_XML)
	public javax.ws.rs.core.Response getAllEmploye() {
		javax.ws.rs.core.Response response = null;
	
		List<? extends Employee> employes = this.dao.findAllEmployees();
		List<DTOEmploye> dtos = new java.util.ArrayList<DTOEmploye>();
		for (Employee emp : employes) {
			dtos.add(DTOBuilder.build(emp));
		}
		
		javax.ws.rs.core.GenericEntity<List<DTOEmploye>> entity
			= new javax.ws.rs.core.GenericEntity<List<DTOEmploye>>(dtos) {}; 
		response = javax.ws.rs.core.Response.ok(entity).build();
		return response;
	}
	

}
