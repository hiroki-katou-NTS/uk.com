package nts.uk.ctx.pr.core.ws.payment.banktransfer;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.core.dom.payment.banktransfer.BankTransferRepository;
import nts.uk.shr.find.employment.processing.yearmonth.IPaydayProcessingFinder;
import nts.uk.shr.find.employment.processing.yearmonth.PaydayProcessingDto;

@Path("pr/proto/payment/banktransfer")
@Produces("application/json")
public class Qpp014WebService extends WebService {

	@Inject
	private IPaydayProcessingFinder iPaydayProcessingFinder;
	
	@Inject
	private BankTransferRepository bankTransferRepository;

	@POST
	@Path("findPayDayProcessing/{payBonusAtr}")
	public List<PaydayProcessingDto> findPayDayProcessing(@PathParam("payBonusAtr") int payBonusAtr) {
		return iPaydayProcessingFinder.getPaydayProcessing(payBonusAtr);
	}
}
