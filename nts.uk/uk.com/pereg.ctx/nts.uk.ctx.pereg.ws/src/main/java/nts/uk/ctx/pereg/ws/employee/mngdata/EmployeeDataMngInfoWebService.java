package nts.uk.ctx.pereg.ws.employee.mngdata;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.layer.app.command.JavaTypeResult;
import nts.uk.ctx.bs.employee.app.find.classification.employee.mngdata.EmployeeDataMngInfoFinder;

@Path("bs/employee/mngdata")
@Produces(MediaType.APPLICATION_JSON)
public class EmployeeDataMngInfoWebService {

	@Inject
	private EmployeeDataMngInfoFinder employeeFinder;

	@POST
	@Path("getGenerateEmplCode")
	public JavaTypeResult<String> getGenerateEmplCode(String startLetters) {
		return new JavaTypeResult<String>(this.employeeFinder.generateEmplCode(startLetters));
	}

	@POST
	@Path("getGenerateCardNo")
	public JavaTypeResult<String> getGenerateCardNo(String startLetters) {
		return new JavaTypeResult<String>(this.employeeFinder.generateCardNo(startLetters));
	}

}
