package nts.uk.ctx.at.record.ws.remaingnumber;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.record.app.find.remainingnumber.paymana.PayoutManagementDataFinder;
import nts.uk.ctx.at.record.app.find.remainingnumber.paymana.PayoutManagementDataDto;

@Path("at/record/remainnumber/paymana")
@Produces("application/json")
public class PayoutManagementDataWebService extends WebService {
	@Inject
	PayoutManagementDataFinder finder;
	
	@POST
	@Path("getBysiDRemCod/{empId}/{state}")
	// get SubstitutionOfHDManagement by SID and stateAtr = ?
	public List<PayoutManagementDataDto> getBysiDRemCod(@PathParam("empId") String employeeId, @PathParam("state") int state) {
		return finder.getBysiDRemCod(employeeId, state);
	}
}
