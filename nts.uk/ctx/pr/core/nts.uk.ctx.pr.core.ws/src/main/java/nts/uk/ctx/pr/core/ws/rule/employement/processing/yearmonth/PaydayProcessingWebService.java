package nts.uk.ctx.pr.core.ws.rule.employement.processing.yearmonth;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.core.app.command.paymentdata.CreatePaymentDataCommand;
import nts.uk.ctx.pr.core.app.command.paymentdata.InsertPaymentDataCommand;
import nts.uk.ctx.pr.core.app.command.paymentdata.UpdatePaymentDataCommand;
import nts.uk.ctx.pr.core.app.find.rule.employement.processing.yearmonth.PaydayProcessingFinder;

@Path("pr/core/paydayprocessing")
@Produces("application/json")
public class PaydayProcessingWebService extends WebService {

	@Inject
	private PaydayProcessingFinder paydayProcessingFinder;
	
	@POST
	@Path("getstring")
	public Object GetString(String name){
		Object obj = new Object();
		return obj;
	}	

	@POST
	@Path("add")
	public void add(CreatePaymentDataCommand command) {
		
	}

	@POST
	@Path("insert")
	public void add(InsertPaymentDataCommand command) {
		
	}

	@POST
	@Path("update")
	public void add(UpdatePaymentDataCommand command) {
		
	}
}
