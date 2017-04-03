package nts.uk.ctx.pr.core.ws.paymentdata.banktransfer;

import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.shr.find.employment.processing.yearmonth.IPaydayProcessingFinder;

@Path("pr/proto/paymentdata/banktransfer")
@Produces("application/json")
public class Qpp014WebService extends WebService {

	@Inject
	private IPaydayProcessingFinder iPaydayProcessingFinder;

//	@POST
//	@Path("findPayDayProcessing/{companyCode}/{pay_bonus_atr}")
//	public PaydayProcessingDto findPayDayProcessing(@PathParam("companyCode") String companyCode,
//			@PathParam("pay_bonus_atr") int pay_bonus_atr) {
//		return iPaydayProcessingFinder.getPaydayProcessing(companyCode, pay_bonus_atr);
//	}
}
