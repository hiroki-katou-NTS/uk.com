package nts.uk.ctx.pr.proto.ws.paymentdata.paymentdatemaster;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.proto.app.find.paymentdata.PaymentDateProcessingMasterDto;
import nts.uk.ctx.pr.proto.app.find.paymentdata.PaymentDateProcessingMasterFinder;
import nts.uk.ctx.pr.proto.dom.paymentdata.PayBonusAtr;

/**
 * Perform action with payment date master
 * 
 * @author chinhbv
 *
 */
@Path("pr/proto/paymentdatemaster")
@Produces("application/json")
public class PaymentDateWebService extends WebService {
	@Inject
	private PaymentDateProcessingMasterFinder paymentDateProcessingFinder;
	
	
	@POST
	@Path("processing/find")
	public PaymentDateProcessingMasterDto find() {
		return this.paymentDateProcessingFinder.find(PayBonusAtr.SALARY.value, 1).get();
	}
	

	@POST
	@Path("processing/findall")
	public List<PaymentDateProcessingMasterDto> findAll() {
		return this.paymentDateProcessingFinder.findAll(PayBonusAtr.SALARY.value);
	}
}
