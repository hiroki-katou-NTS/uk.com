package nts.uk.ctx.at.schedule.ws.budget.schedulevertical.wkptimezonepeoplenumber;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.schedule.app.command.budget.schedulevertical.wkptimezonepeoplenumber.RegisterWkpTimeZonePeopleNumberCommand;
import nts.uk.ctx.at.schedule.app.command.budget.schedulevertical.wkptimezonepeoplenumber.RegisterWkpTimeZonePeopleNumberCommandHandler;
import nts.uk.ctx.at.schedule.app.find.budget.schedulevertical.wkptimezone.WkpCounterStartTimeDto;
import nts.uk.ctx.at.schedule.app.find.budget.schedulevertical.wkptimezone.WkpTimeZonePeopleNumberFinder;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;

/**
 * Screen E
 */
@Path("ctx/at/schedule/budget/wkpTimeZone")
@Produces("application/json")
public class WkpTimeZonePeopleNumberWebService extends WebService {
	@Inject
	private WkpTimeZonePeopleNumberFinder wkpTimeZonePeopleNumberFinder;
	
	@Inject
	private RegisterWkpTimeZonePeopleNumberCommandHandler timeZonePeopleNumberCommandHandler;

	@Path("getById")
	@POST
	public List<WkpCounterStartTimeDto> findByCid() {
		return wkpTimeZonePeopleNumberFinder.findById();
	}

	@Path("register")
	@POST
	public void registerTimeZone(RegisterWkpTimeZonePeopleNumberCommand command) {
		this.timeZonePeopleNumberCommandHandler.handle(command);
	}

	
}
