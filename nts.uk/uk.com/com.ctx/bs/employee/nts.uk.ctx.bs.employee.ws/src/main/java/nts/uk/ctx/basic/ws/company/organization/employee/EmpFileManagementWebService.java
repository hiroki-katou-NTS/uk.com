package nts.uk.ctx.basic.ws.company.organization.employee;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.bs.employee.app.command.empfilemanagement.AddEmpAvaOrMapCommand;
import nts.uk.ctx.bs.employee.app.command.empfilemanagement.AddEmpAvaOrMapCommandHandler;
import nts.uk.ctx.bs.employee.app.command.empfilemanagement.AddEmpDocumentFileCommand;
import nts.uk.ctx.bs.employee.app.command.empfilemanagement.EmpDocumentFileCommandHandler;
import nts.uk.ctx.bs.employee.app.find.empfilemanagement.EmployeeFileManagementFinder;
import nts.uk.ctx.bs.employee.app.find.empfilemanagement.dto.EmployeeFileManagementDto;

@Path("basic/organization/empfilemanagement")
@Produces({ "application/json", "text/plain" })
public class EmpFileManagementWebService extends WebService {

	@Inject
	EmployeeFileManagementFinder employeeFileManagementFinder;
	
	@Inject
	EmpDocumentFileCommandHandler empDocumentFileCommandHandler; 
	
	@Inject
	AddEmpAvaOrMapCommandHandler addEmpAvaOrMapCommandHandler;

	/**
	 * Gets employee file management by employeeId.
	 *
	 * @return employee file management
	 */
	//vinhpx: start
	@POST
	@Path("find/getAvaOrMap")
	public EmployeeFileManagementDto getAvaOrMap(String employeeId) {
		return this.employeeFileManagementFinder.getAvaOrMap(employeeId, 1);
	}
	
	@POST
	@Path("command/insertAvaOrMap")
	public void insertAvaOrMap(AddEmpAvaOrMapCommand command) {
		this.addEmpAvaOrMapCommandHandler.handle(command);
	}
	//vinhpx: end

	@POST
	@Path("getlistdocfile/{employeeId}")
	public List<Object> getListDocumentFile(@PathParam("employeeId") String employeeId) {
		return this.employeeFileManagementFinder.getListDocumentFile(employeeId);
	}

	@POST
	@Path("savedocfile")
	public void saveDocumentFile(AddEmpDocumentFileCommand command) {
		this.empDocumentFileCommandHandler.handle(command);
	}
}
