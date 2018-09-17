package nts.uk.ctx.core.ws.socialinsurance.welfarepensioninsurance;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.uk.ctx.core.app.command.socialinsurance.welfarepensioninsurance.WelfarePensionInsuranceCommandHandler;
import nts.uk.ctx.core.app.command.socialinsurance.welfarepensioninsurance.command.WelfarePensionInsuraceRateCommand;
import nts.uk.ctx.core.app.find.socialinsurance.welfarepensioninsurance.WelfarePensionInsuranceFinder;
import nts.uk.ctx.core.app.find.socialinsurance.welfarepensioninsurance.dto.WelfarePensionInsuraceRateDto;

@Path("ctx/core/socialinsurance/welfarepensioninsurance")
@Produces("application/json")
public class WelfarePensionInsuranceHistoryWebservice {
	
	@Inject
	private WelfarePensionInsuranceFinder welfarePensionInsuranceFinder;
	
	@Inject
	private WelfarePensionInsuranceCommandHandler welfarePensionInsuranceCommandHandler;
	
	@POST
	@Path("/getByHistoryId/{historyId}")
	public WelfarePensionInsuraceRateDto getByHistoryId(@PathParam("historyId") String historyId) {
		return welfarePensionInsuranceFinder.findWelfarePensionByHistoryID(historyId);
	}
	
	@POST
	@Path("/addEmployeePension")
	public void addEmployeePension(WelfarePensionInsuraceRateCommand command) {
		welfarePensionInsuranceCommandHandler.handle(command);
	}
}