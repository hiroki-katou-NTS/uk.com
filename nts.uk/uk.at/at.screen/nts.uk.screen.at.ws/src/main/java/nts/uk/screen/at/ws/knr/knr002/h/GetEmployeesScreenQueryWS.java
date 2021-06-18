package nts.uk.screen.at.ws.knr.knr002.h;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.uk.screen.at.app.query.knr.knr002.h.GetEmployees;
import nts.uk.screen.at.app.query.knr.knr002.h.GetEmployeesDto;

/**
*
* @author xuannt
*
*/
@Path("screen/at/employeestransfer")
@Produces(MediaType.APPLICATION_JSON)
public class GetEmployeesScreenQueryWS {
	@Inject
	private GetEmployees getEmployees;
	@POST
	@Path("getEmployees/{empInfoTerCode}")
	public List<GetEmployeesDto> getEmployees(@PathParam("empInfoTerCode") String empInforTerCode) {
		return this.getEmployees.getEmployees(empInforTerCode);
	}
}