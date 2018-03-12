/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.ws.company.organization.employee.deletemanagement;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.bs.employee.app.command.employee.deletemanagement.CompletelyDelEmpCommandHandler;
import nts.uk.ctx.bs.employee.app.command.employee.deletemanagement.EmployeeDeleteCommand;
import nts.uk.ctx.bs.employee.app.command.employee.deletemanagement.EmployeeDeleteCommandHandler;
import nts.uk.ctx.bs.employee.app.command.employee.deletemanagement.EmployeeDeleteToRestoreCommand;
import nts.uk.ctx.bs.employee.app.command.employee.deletemanagement.RestoreDataEmpCommandHandler;
import nts.uk.ctx.bs.employee.app.find.employee.EmployeeToDeleteDetailDto;
import nts.uk.ctx.bs.employee.app.find.employee.EmployeeToDeleteDto;
import nts.uk.ctx.bs.employee.app.find.employee.deletemanagement.EmployeeDeleteFinder;

@Path("basic/organization/deleteempmanagement")
@Produces({ "application/json", "text/plain" })
public class DelEmpManagementWebService extends WebService {

	@Inject
	private RestoreDataEmpCommandHandler restoreEmpHandler;

	@Inject
	private EmployeeDeleteCommandHandler empDeleteHandler;

	@Inject
	private CompletelyDelEmpCommandHandler completelyDelEmpHandler;

	@Inject
	private EmployeeDeleteFinder employeeDeleteFinder;


	/**
	 * Get Employee Info to Display Screen Delete Emp
	 * 
	 * @param employeeId
	 * @return
	 */
	@POST
	@Path("getemployeetodelete/{employeeId}")
	public EmployeeToDeleteDto getEmployee(@PathParam("employeeId") String employeeId) {
			return this.employeeDeleteFinder.getEmployeeInfo(employeeId);
	}

	@POST
	@Path("deleteemployee")
	public void deleteEmployee(EmployeeDeleteCommand command) {
		this.empDeleteHandler.handle(command);
	}

	@POST
	@Path("getallemployeetodelete")
	public List<EmployeeToDeleteDto> getAllEmployeeDelete() {
		return employeeDeleteFinder.getAllEmployeeInfoToDelete();
	}

	@POST
	@Path("getdetailemployeetodelete/{employeeId}")
	public EmployeeToDeleteDetailDto getDetailEmpDelete(@PathParam("employeeId") String employeeId) {
		return employeeDeleteFinder.getDetailEmployeeInfoToDelete(employeeId);
	}

	@POST
	@Path("restoredata")
	public void restoreData(EmployeeDeleteToRestoreCommand command) {
		this.restoreEmpHandler.handle(command);
	}

	@POST
	@Path("deleteemp/{employeeId}")
	public void deleteEmp(@PathParam("employeeId") String employeeId) {
		this.completelyDelEmpHandler.handle(employeeId);
	}
	
	@POST
	@Path("checkexit/{empCode}")
	public boolean checkExit(@PathParam("empCode") String empCode) {
		return this.checkExit(empCode);
	}
}
