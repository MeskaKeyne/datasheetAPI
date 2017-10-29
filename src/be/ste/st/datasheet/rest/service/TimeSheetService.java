package be.ste.st.datasheet.rest.service;


import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import be.ste.ts.datasheet.dto.DTOBuilder;
import be.ste.ts.datasheet.dto.DTOEmploye;
import be.ste.ts.datasheet.dto.DTOPrestation;
import be.ste.ts.datasheet.dto.DTOProject;
import be.steformations.java_data.timesheets.entities.Employee;
import be.steformations.java_data.timesheets.entities.Prestation;
import be.steformations.java_data.timesheets.entities.Project;
import be.steformations.java_data.timesheets.service.TimesheetsDataService;

@Path("service")
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
	@Path("employe/{login}/{password}")
	@GET //http://localhost:8080/timesheet/service/employe/wonder/woman
	@Produces(MediaType.APPLICATION_JSON)
	public Response getEmployeByLoginPassword(@PathParam("login") String login,
											  @PathParam("password") String password) {
		Response response = null;
	
		Employee emp = this.dao.findOneEmployeeByLoginAndPassword(login, password);
		if (emp == null) response = Response.status(404).build();
		 else{
			DTOEmploye dto = DTOBuilder.build(emp);
			response = Response.ok(dto).build();
		}
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
	@Path("project/list")
	@GET //http://localhost:8080/timesheet/service/project/list
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllProject() {
		Response response = null;
		
		List<? extends Project> projets = this.dao.findAllProjects();
		List<DTOProject> dtos = new ArrayList<DTOProject>();
		for (Project emp : projets) dtos.add(DTOBuilder.build(emp));
		
		GenericEntity<List<DTOProject>> entity = new GenericEntity<List<DTOProject>>(dtos) {}; 
		response = Response.ok(entity).build();
		return response;
	}
	@POST //http://localhost:8080/timesheet/service/prestation/add
	@Path("prestation/add")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	public javax.ws.rs.core.Response addPrestation(DTOPrestation input) {
		Response response = null;
		Prestation p = this.dao.addPrestation(input.getEmployee().getId(), 
												input.getProject().getId(), 
												input.getComment(), 
												input.getDay(), 
												input.getDuration());
		if (p == null) response = Response.status(500).build(); 
		else {
			DTOPrestation dto = DTOBuilder.build(p);
			response = Response.ok(dto).build();
		}
		return response;
	}
}
