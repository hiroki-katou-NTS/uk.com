package nts.uk.file.at.ws.bento;

import java.util.Arrays;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.app.file.export.ExportServiceResult;
import nts.uk.ctx.at.record.app.query.reservation.ReservationExportQuery;
import nts.uk.file.at.app.export.bento.ReservationMonthExportService;
import nts.uk.file.at.app.export.bento.ReservationMonthQuery;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Path("bento/report")
@Produces("application/json")
public class BentoReportWebService {
	
	@Inject
	private ReservationMonthExportService reservationMonthExportService;
	
	@Inject ReservationExportQuery reservationExportQuery;
	
	@POST
    @Path("reservation/month")
    public ExportServiceResult generate(ReservationMonthQuery query) {
		return reservationMonthExportService.start(query);
    }
	
	@POST
	@Path("reservation/data")
	public ExportServiceResult printData() {
		String title = "月間予約台帳";
		DatePeriod datePeriol = reservationExportQuery.startup();
		ReservationMonthQuery query = new ReservationMonthQuery(
						Arrays.asList(AppContexts.user().employeeId()), title,
						datePeriol.start().toString(), datePeriol.end().toString(), false);
		return reservationMonthExportService.start(query);
	}
	
}
