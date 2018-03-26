package nts.uk.ctx.at.record.ws.monthly.vtotalmonth;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.at.record.app.command.monthly.vtotalmethod.AddVerticalTotalMethodOfMonthlyCommand;
import nts.uk.ctx.at.record.app.command.monthly.vtotalmethod.AddVerticalTotalMethodOfMonthlyCommandHandler;
import nts.uk.ctx.at.record.app.find.monthly.vtotalmethod.VerticalTotalMethodOfMonthlyFinder;
import nts.uk.ctx.at.record.dom.monthly.vtotalmethod.VerticalTotalMethodOfMonthly;

/**
 * 
 * @author HoangNDH
 *
 */
@Path("record/monthly/vtotalmethod")
@Produces("application/json")
public class VerticalTotalMethodMonthlyService {
	@Inject
	VerticalTotalMethodOfMonthlyFinder finder;
	
	@Inject
	AddVerticalTotalMethodOfMonthlyCommandHandler handler;
	
	@Path("read")
	@POST
	public VerticalTotalMethodOfMonthly findSetting() {
		return finder.findSetting();
	}
	
	@Path("register")
	@POST
	public void registerSetting(AddVerticalTotalMethodOfMonthlyCommand command) {
		this.handler.handle(command);
	}
}
