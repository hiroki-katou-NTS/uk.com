package nts.uk.ctx.pr.screen.ws.paymentdata;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.pr.screen.app.query.paymentdata.processor.GetPaymentDataQueryProcessor;
import nts.uk.ctx.pr.screen.app.query.paymentdata.query.PaymentDataQuery;

@Path("/ctx/pr/screen/paymentdata")
@Produces("application/json")
public class PaymentDataScreenWebService {
	@Inject
	private GetPaymentDataQueryProcessor getPaymentDataQueryProcessor;

	@POST
	@Path("find")
	public void find(PaymentDataQuery query) {
		this.getPaymentDataQueryProcessor.find(query);
	}
}
