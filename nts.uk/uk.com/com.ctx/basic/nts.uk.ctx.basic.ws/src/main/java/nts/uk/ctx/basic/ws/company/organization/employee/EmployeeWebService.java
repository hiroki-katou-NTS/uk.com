package nts.uk.ctx.basic.ws.company.organization.employee;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.basic.app.find.company.organization.employee.EmployeeDto;
import nts.uk.ctx.basic.app.find.company.organization.employee.EmployeeFinder;

@Path("basic/organization")
@Produces(MediaType.APPLICATION_JSON)
public class EmployeeWebService extends WebService {
	@Inject
	private EmployeeFinder employeeFinder;
	
	@POST
	@Path("getPersonIdByEmployeeCode/{employeeCode}")
	public EmployeeDto getPersonIdByEmployeeCode(@PathParam("employeeCode") String employeeCode) {
		return employeeFinder.getPersonIdByEmployeeCode(employeeCode).orElse(null);
	}
}
