package nts.uk.ctx.at.schedule.ws.shift.businesscalendar.businesscalendar;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.schedule.app.find.shift.businesscalendar.businesscalendar.StartDayDto;
import nts.uk.ctx.at.schedule.app.find.shift.businesscalendar.businesscalendar.StartDayFinder;

@Path("at/schedule/shift/businesscalendar/businesscalendar")
@Produces("application/json")
public class CompanyStartDayWebService extends WebService{
	@Inject
	private StartDayFinder startDayFinder;
	
	@POST
	@Path("getcompanystartday")
	public StartDayDto getCompanyStartDay() {
		return this.startDayFinder.findByCompanyId();
	}
}
