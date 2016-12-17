package nts.uk.pr.file.ws.paymentdata;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.pr.file.infra.paymentdata.GetPaymentDataQueryProcessor;
import nts.uk.pr.file.infra.paymentdata.PaymentDataPrintFileGenerator;
import nts.uk.pr.file.infra.paymentdata.PaymentDataQuery;
import nts.uk.pr.file.infra.paymentdata.result.PaymentDataResult;

@Path("/file/paymentdata")
@Produces("application/json")
public class PaymentDataPrintWebservice {

	@Inject
	private PaymentDataPrintFileGenerator paymentDataPrint;
	@Inject
	private GetPaymentDataQueryProcessor getPaymentDataQueryProcessor;
	
	@POST
	@Path("print")
	public String print(List<PaymentDataQuery> query) {
		PaymentDataResult result = this.getPaymentDataQueryProcessor.find(query.get(0));
		return this.paymentDataPrint.start(result).getTask();
	}
	
}
