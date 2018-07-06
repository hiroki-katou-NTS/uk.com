package nts.uk.ctx.at.record.ws.calculation.holiday;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.at.record.app.command.calculation.holiday.roundingmonth.AddRoundingMonthCommand;
import nts.uk.ctx.at.record.app.command.calculation.holiday.roundingmonth.AddRoundingMonthCommandHandler;


/**
 * Add monthly item rounding web service
 * @author HoangNDH
 *
 */
@Path("shared/caculation/holiday/rounding")
@Produces("application/json")
public class MonthItemRoundingWebService {
	@Inject
	private AddRoundingMonthCommandHandler handler;
	
	@Path("add")
	@POST
	public void add(List<AddRoundingMonthCommand> command) {
		this.handler.handle(command);
	}
	
	@Path("update")
	@POST
	public void update(List<AddRoundingMonthCommand> command) {
		this.handler.handle(command);
	}
}
