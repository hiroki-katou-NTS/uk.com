/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pereg.ws.deleteemployee;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pereg.app.command.deleteemployee.CompletelyDelEmpCommandHandler;
import nts.uk.ctx.pereg.app.command.deleteemployee.EmployeeDeleteCommand;
import nts.uk.ctx.pereg.app.command.deleteemployee.EmployeeDeleteCommandHandler;
import nts.uk.ctx.pereg.app.command.deleteemployee.EmployeeDeleteToRestoreCommand;
import nts.uk.ctx.pereg.app.command.deleteemployee.RestoreDataEmpCommandHandler;
import nts.uk.ctx.pereg.app.find.deleteemployee.EmployeeDeleteFinder;
import nts.uk.ctx.pereg.app.find.deleteemployee.EmployeeToDeleteDetailDto;
import nts.uk.ctx.pereg.app.find.deleteemployee.EmployeeToDeleteDto;


@Path("ctx/pereg/deleteemployee")
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
