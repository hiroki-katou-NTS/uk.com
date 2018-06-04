package nts.uk.ctx.at.shared.ws.remaingnumber;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.app.find.remainingnumber.paymana.FurikyuMngDataExtractionDto;
import nts.uk.ctx.at.record.app.find.remainingnumber.paymana.FurikyuMngDataExtractionFinder;

@Path("at/record/remainnumber/paymana")
@Produces("application/json")
public class FurikyuMngDataExtractionWebService extends WebService {
	@Inject
	private FurikyuMngDataExtractionFinder finder;

	@POST
	@Path("getFurikyuMngDataExtraction/{empId}/{startDate}/{endDate}/{isPeriod}")
	public FurikyuMngDataExtractionDto getFurikyuMngDataExtraction(@PathParam("empId") String empId,
			@PathParam("startDate") String startDate, @PathParam("endDate") String endDate, @PathParam("isPeriod") boolean isPeriod) {
		
		GeneralDate startDateFormat = isPeriod ? GeneralDate.fromString(startDate, "yyyy-MM-dd") : null;
		GeneralDate endDateFormat = isPeriod ? GeneralDate.fromString(endDate, "yyyy-MM-dd") : null;
		return finder.getFurikyuMngDataExtraction(empId, startDateFormat, endDateFormat, isPeriod);
	}	
}
