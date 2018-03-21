package nts.uk.screen.at.ws.monthlyperformance.correction;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.screen.at.app.monthlyperformance.correction.MonthlyPerformanceCorrectionProcessor;
import nts.uk.screen.at.app.monthlyperformance.correction.dto.MonthlyPerformanceCorrectionDto;

/**
 * TODO
 */
@Path("screen/at/correctionofmonthlyperformance")
@Produces("application/json")
public class MonthlyPerformanceCorrectionWebService {
	@Inject
	MonthlyPerformanceCorrectionProcessor processor;

	@POST
	@Path("startScreen")
	public MonthlyPerformanceCorrectionDto startScreen() throws InterruptedException {
		return processor.generateData();
	}
}
