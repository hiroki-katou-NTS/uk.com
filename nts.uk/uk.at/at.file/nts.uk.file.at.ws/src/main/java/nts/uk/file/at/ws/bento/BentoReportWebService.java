package nts.uk.file.at.ws.bento;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.app.file.export.ExportServiceResult;
import nts.uk.file.at.app.export.bento.ReservationMonthExportService;
import nts.uk.file.at.app.export.bento.ReservationMonthQuery;

@Path("bento/report")
@Produces("application/json")
public class BentoReportWebService {
	
	@Inject
	private ReservationMonthExportService reservationMonthExportService;
	
	@POST
    @Path("reservation/month")
    public ExportServiceResult generate(ReservationMonthQuery query) {
		return reservationMonthExportService.start(query);
    }
	
}
