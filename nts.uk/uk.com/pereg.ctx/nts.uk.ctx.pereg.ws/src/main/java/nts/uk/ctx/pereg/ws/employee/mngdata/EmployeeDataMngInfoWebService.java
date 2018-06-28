package nts.uk.ctx.pereg.ws.employee.mngdata;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.layer.app.command.JavaTypeResult;
import nts.uk.ctx.pereg.app.find.employee.EmployeeInfoFinder;

@Path("ctx/pereg/employee/mngdata")
@Produces(MediaType.APPLICATION_JSON)
public class EmployeeDataMngInfoWebService {

	@Inject
	private EmployeeInfoFinder employeeFinder;
	
	@POST
	@Path("getGenerateEmplCode")
	public JavaTypeResult<String> getGenerateEmplCode(String startLetters) {
		return new JavaTypeResult<String>(this.employeeFinder.generateEmplCode(startLetters));
	}

	@POST
	@Path("getGenerateCardNo")
	public JavaTypeResult<String> getGenerateCardNo(String startLetters) {
		return new JavaTypeResult<String>(employeeFinder.generateCardNo(startLetters));
	}
	
	@POST
	@Path("getInitCardNo")
	public JavaTypeResult<String> getInitCardNo(String newEmployeeCode) {
		String newCardNo = employeeFinder.initCardNo(newEmployeeCode);
		return new JavaTypeResult<String>(newCardNo);
	}
	
}
