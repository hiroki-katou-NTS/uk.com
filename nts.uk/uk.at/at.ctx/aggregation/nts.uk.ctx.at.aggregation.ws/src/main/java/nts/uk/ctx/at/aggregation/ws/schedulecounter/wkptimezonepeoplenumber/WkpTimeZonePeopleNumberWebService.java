package nts.uk.ctx.at.aggregation.ws.schedulecounter.wkptimezonepeoplenumber;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.aggregation.app.command.schedulecounter.wkptimezonepeoplenumber.RegisterWkpTimeZonePeopleNumberCommand;
import nts.uk.ctx.at.aggregation.app.command.schedulecounter.wkptimezonepeoplenumber.RegisterWkpTimeZonePeopleNumberCommandHandler;
import nts.uk.ctx.at.aggregation.app.find.schedulecounter.wkptimezone.WkpCounterStartTimeDto;
import nts.uk.ctx.at.aggregation.app.find.schedulecounter.wkptimezone.WkpTimeZonePeopleNumberFinder;

/**
 * KML002 Screen E
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
