package nts.uk.ctx.at.function.ws.holidaysremaining;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.app.file.export.ExportServiceResult;
import nts.uk.ctx.at.function.app.export.holidaysremaining.HolidaysRemainingReportHandler;
import nts.uk.ctx.at.function.app.export.holidaysremaining.HolidaysRemainingReportQuery;

@Path("at/function/holidaysremaining")
@Produces("application/json") 
public class HolidaysRemainingReportWebService {
	
	@Inject
	private HolidaysRemainingReportHandler reportHandler;
	
	@POST
	@Path("report")
	public ExportServiceResult generate(HolidaysRemainingReportQuery query) {
		return this.reportHandler.start(query);
	}
}
