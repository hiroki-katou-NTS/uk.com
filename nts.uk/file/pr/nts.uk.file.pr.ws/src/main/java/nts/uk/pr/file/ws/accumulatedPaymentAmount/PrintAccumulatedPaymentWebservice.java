package nts.uk.pr.file.ws.accumulatedPaymentAmount;

import java.util.Arrays;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.pr.file.infra.accumulatedPaymentAmount.PaymentAmountDto;
import nts.uk.pr.file.infra.accumulatedPaymentAmount.AccumulatedPaymentReportGenerator;
import nts.arc.layer.ws.WebService;

@Path("/screen/pr/QET002")
@Produces("application/json")
public class PrintAccumulatedPaymentWebservice extends WebService {

	@Inject
	private AccumulatedPaymentReportGenerator accumulatedPrint;
	
	@POST
	@Path("print")
	public String print(PaymentAmountDto paymentAmountDto) {
		// Validate
		if (paymentAmountDto.getTargetYear() == 0) {
			throw new RuntimeException("Target Year is Empty");
		}
		if ((paymentAmountDto.isLowerLimit() == true) && (paymentAmountDto.getLowerLimitValue() == 0)) {			
			throw new RuntimeException("Lower Limit Value is Not Entered.");			
		}
		if ((paymentAmountDto.isUpperLimit() == true) && (paymentAmountDto.getUpperLimitValue() == 0)) {			
			throw new RuntimeException("Upper Limit Value is Not Entered.");			
		}
		if ((paymentAmountDto.isLowerLimit() == true) && (paymentAmountDto.isUpperLimit() == true)) {
			if (paymentAmountDto.getLowerLimitValue() > paymentAmountDto.getUpperLimitValue()) {
				throw new RuntimeException("Lower Limit Value is greater than Upper Limit Value.");
			}
		}
		// TODO Validate Unselected Employees
		return null;
	}
}
