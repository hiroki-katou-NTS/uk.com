package nts.uk.ctx.core.ws.system.welfarepensioninsurance;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.uk.ctx.core.app.find.socialinsurance.welfarepensioninsurance.WelfarePensionInsuranceFinder;
import nts.uk.ctx.core.app.find.socialinsurance.welfarepensioninsurance.dto.WelfarePensionInsuraceRateDto;

@Path("ctx/core/socialinsurance/welfarepensioninsurance")
@Produces("application/json")
public class WelfarePensionInsuranceHistoryWebservice {
	
	@Inject
	private WelfarePensionInsuranceFinder welfarePensionInsuranceFinder;
	
	@POST
	@Path("/getByHistoryId/{historyId}")
	public WelfarePensionInsuraceRateDto getByHistoryId(@PathParam("historyId") String historyId) {
		return welfarePensionInsuranceFinder.findWelfarePensionByHistoryID(historyId);
	}
}