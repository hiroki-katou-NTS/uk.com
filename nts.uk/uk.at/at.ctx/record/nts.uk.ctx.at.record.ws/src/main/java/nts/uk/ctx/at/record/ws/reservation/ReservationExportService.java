package nts.uk.ctx.at.record.ws.reservation;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.record.app.query.reservation.ReservationExportDto;
import nts.uk.ctx.at.record.app.query.reservation.ReservationExportParam;
import nts.uk.ctx.at.record.app.query.reservation.ReservationExportQuery;

@Path("bento/report")
@Produces("application/json")
public class ReservationExportService extends WebService {
	
	@Inject
	private ReservationExportQuery reservationExportQuery;
	
	@POST
	@Path("startup")
	public ReservationExportDto startup(ReservationExportParam param) {
		return reservationExportQuery.startup(param);
	} 
	
}
