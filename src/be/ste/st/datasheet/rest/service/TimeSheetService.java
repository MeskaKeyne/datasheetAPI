package be.ste.st.datasheet.rest.service;


import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import be.ste.ts.datasheet.dto.DTOBuilder;
import be.ste.ts.datasheet.dto.DTOEmploye;
import be.ste.ts.datasheet.dto.DTOPrestation;
import be.steformations.java_data.timesheets.entities.Employee;
import be.steformations.java_data.timesheets.entities.Prestation;
import be.steformations.java_data.timesheets.service.TimesheetsDataService;

@javax.ws.rs.Path("service")
public class TimeSheetService {
	private TimesheetsDataService dao;
	
	public TimeSheetService() {
		super();
		this.dao= DaoRestFactory.getInstance().getDao();
	}
	
	@GET //http://localhost:8080/timesheet/service/employe/2/
	@Path("employe/{id:[1-9]+}")
	@Produces(MediaType.APPLICATION_XML)
	public Response getEmployeById(@PathParam("id") int id) {
		Response response = null;
		Employee emp = this.dao.findOneEmployeeById(id);
		if (emp == null) response = Response.status(404).build();
		 else{
			DTOEmploye dto = DTOBuilder.build(emp);
			response = Response.ok(dto).build();
		}
		return response;
	}
	
	@Path("employe/list")
	@GET //http://localhost:8080/timesheet/service/employe/list
	@Produces(MediaType.APPLICATION_XML)
	public Response getAllEmploye() {
		Response response = null;
	
		List<? extends Employee> employes = this.dao.findAllEmployees();
		List<DTOEmploye> dtos = new ArrayList<DTOEmploye>();
		for (Employee emp : employes) dtos.add(DTOBuilder.build(emp));
		
		GenericEntity<List<DTOEmploye>> entity = new GenericEntity<List<DTOEmploye>>(dtos) {}; 
		response = Response.ok(entity).build();
		return response;
	}
	@Path("employe/{id:[1-9]+}/plist")
	@GET //http://localhost:8080/timesheet/service/employe/2/plist
	@Produces(MediaType.APPLICATION_XML)
	public Response getAllPrestationByIdEmploye(@PathParam("id") int id) {
		Response response = null;
	
		List<? extends Prestation> prestations = this.dao.findAllPrestationsByEmployeeId(id);
		List<DTOPrestation> dtos = new ArrayList<DTOPrestation>();
		for (Prestation pres : prestations) dtos.add(DTOBuilder.build(pres));
		
		GenericEntity<List<DTOPrestation>> entity = new GenericEntity<List<DTOPrestation>>(dtos) {}; 
		response = Response.ok(entity).build();
		return response;
	}
	
}
