package nts.uk.ctx.at.request.ws.application.hdworkapplicationsetting;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.request.app.find.setting.applicationapprovalsetting.hdworkapplicationsetting.WithdrawalAppSetDto;
import nts.uk.ctx.at.request.app.find.setting.applicationapprovalsetting.hdworkapplicationsetting.WithdrawalAppSetFinder;

@Path("at/approverset/hdwork")
@Produces("application/json")
public class WithdrawalAppSetWebservice extends WebService{
	@Inject
	private WithdrawalAppSetFinder withFinder;
	
	@POST
	@Path("getbycom")
	public WithdrawalAppSetDto getAllByCom() {
		return this.withFinder.findByCid();
	}
}
