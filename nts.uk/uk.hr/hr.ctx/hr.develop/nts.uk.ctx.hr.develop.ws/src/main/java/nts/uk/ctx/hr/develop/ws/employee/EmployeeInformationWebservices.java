package nts.uk.ctx.hr.develop.ws.employee;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.uk.ctx.hr.develop.app.employee.EmployeeInformationFinder;
import nts.uk.ctx.hr.develop.app.employee.EmployeeInformationQuery;
import nts.uk.ctx.hr.shared.dom.adapter.EmployeeInformationImport;

@Path("employee-info")
@Produces(MediaType.APPLICATION_JSON)
public class EmployeeInformationWebservices {
	@Inject
	private EmployeeInformationFinder finder;

	@POST
	@Path("/find")
	public EmployeeInformationImport findEmployeeInfo(EmployeeInformationQuery query) {
		return this.finder.findEmployeeInfo(query);
	}
}
