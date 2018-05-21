package nts.uk.ctx.at.record.ws.remaingnumber;

import java.util.List;

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
	public List<FurikyuMngDataExtractionDto> getFurikyuMngDataExtraction(@PathParam("empId") String empId,
			GeneralDate startDate, GeneralDate endDate, boolean isPeriod) {
		return finder.getFurikyuMngDataExtraction(empId, startDate, endDate, isPeriod);
	}
}
