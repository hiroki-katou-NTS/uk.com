package nts.uk.ctx.basic.ws.company.organization.employee;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.bs.employee.app.find.empfilemanagement.EmployeeFileManagementFinder;
import nts.uk.ctx.bs.employee.app.find.empfilemanagement.dto.EmployeeFileManagementSimpleDto;

@Path("basic/organization/empfilemanagement")
@Produces({ "application/json", "text/plain" })
public class EmpFileManagementWebService extends WebService{

	@Inject
	EmployeeFileManagementFinder employeeFileManagementFinder;
	/**
	 * Gets the all employee.
	 *
	 * @return the all employee
	 */
	@POST
	@Path("getAvaOrMap")
	public EmployeeFileManagementSimpleDto getAvaOrMap(String employeeId) {
		return this.employeeFileManagementFinder.getAvaOrMap(employeeId);
	}
}
