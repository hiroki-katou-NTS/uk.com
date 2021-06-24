package nts.uk.screen.com.ws.cmm015;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.screen.com.app.find.cmm015.EmployeesInWorkplace;
import nts.uk.screen.com.app.find.cmm015.EmployeesInWorkplaceScreenQuery;
import nts.uk.screen.com.app.find.cmm015.TransferOfDay;
import nts.uk.screen.com.app.find.cmm015.TransferOfDayScreenQuery;
import nts.uk.screen.com.ws.cmm015.params.EmployeesInWorkplaceParams;
import nts.uk.screen.com.ws.cmm015.params.TransferOfDayParams;

@Path("com/screen/cmm015")
@Produces("application/json")
public class Cmm015WebService extends WebService {
	
	@Inject
	private EmployeesInWorkplaceScreenQuery employeesInWorkplaceScreenQuery;
	
	@Inject
	private TransferOfDayScreenQuery transferOfDayScreenQuery;
	
	@Path("getEmployeesInWorkplace")
    @POST
	public List<EmployeesInWorkplace> getEmployeesInWorkplace(EmployeesInWorkplaceParams params) {
		return this.employeesInWorkplaceScreenQuery.get(params.getWkpIds()
				, params.getReferDate()
				, params.getIncumbent()
				, params.getClosed()
				, params.getLeave()
				, params.getRetiree());
	}
	
	@Path("getTransferOfDay")
	@POST
	public TransferOfDay getTransferOfDay(TransferOfDayParams params) {
		return this.transferOfDayScreenQuery.get(params.getSids(), params.getBaseDate());
	}
}
