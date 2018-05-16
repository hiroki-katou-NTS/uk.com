package nts.uk.ctx.at.record.ws.remaingnumber;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.record.app.find.remainingnumber.paymana.SubstitutionOfHDManagementDto;
import nts.uk.ctx.at.record.app.find.remainingnumber.paymana.SubstitutionOfHDManagementFinder;

@Path("at/record/remainnumber/paymana")
@Produces("application/json")
public class SubstitutionOfHDManagementWebService extends WebService {
	@Inject
	SubstitutionOfHDManagementFinder finder;
	
	@POST
	@Path("getBysiDRemCod/{empId}")
	// get SubstitutionOfHDManagement by SID and remainsDays > 0
	public List<SubstitutionOfHDManagementDto> getBysiDRemCod(@PathParam("empId") String employeeId) {
		return finder.getBysiDRemCod(employeeId);
	}
}
