package nts.uk.ctx.pereg.ws.addemployee;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.app.command.JavaTypeResult;
import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pereg.app.command.addemployee.AddEmployeeCommand;
import nts.uk.ctx.pereg.app.command.addemployee.AddEmployeeCommandHandler;
import nts.uk.ctx.pereg.app.find.employee.EmpInfoDto;
import nts.uk.ctx.pereg.app.find.employee.EmployeeInfoFinder;

/**
 * @author sonnlb
 *
 */

@Path("ctx/pereg/addemployee")
@Produces("application/json")
public class AddEmployeeWebService extends WebService {
	@Inject
	AddEmployeeCommandHandler addEmpHandler;

	@Inject
	private EmployeeInfoFinder employeeFinder;

	@POST
	@Path("validateEmpInfo")
	public void validateEmpInfo(EmpInfoDto empInfo) {

		this.employeeFinder.validateEmpInfo(empInfo);
	}

	@POST
	@Path("addNewEmployee")
	public JavaTypeResult<String> addNewEmployee(AddEmployeeCommand command) {
		return new JavaTypeResult<String>(this.addEmpHandler.handle(command));
	}

	// sonnlb end
}
