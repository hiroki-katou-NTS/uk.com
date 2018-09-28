package nts.uk.ctx.at.shared.ws.calculation.holiday;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.app.command.calculation.holiday.roundingmonth.AddExcoutRoundingCommand;
import nts.uk.ctx.at.shared.app.command.calculation.holiday.roundingmonth.AddExcoutRoundingCommandHandler;
import nts.uk.ctx.at.shared.app.command.calculation.holiday.roundingmonth.RoundingMonthCommand;
import nts.uk.ctx.at.shared.app.command.calculation.holiday.roundingmonth.RoundingMonthCommandHandler;
import nts.uk.ctx.at.shared.app.find.calculation.holiday.roundingmonth.RoundingMonthDto;
import nts.uk.ctx.at.shared.app.find.calculation.holiday.roundingmonth.RoundingMonthFinder;
import nts.uk.ctx.at.shared.app.find.calculation.holiday.roundingmonth.TimeRoundingOfExcessOutsideTimeDto;
import nts.uk.ctx.at.shared.app.find.calculation.holiday.roundingmonth.TimeRoundingOfExcessOutsideTimeFinder;

/**
 * Web service for rounding month
 * @author HoangNDH
 *
 */
@Path("shared/caculation/holiday/rounding")
@Produces("application/json")
public class RoundingMonthWebService extends WebService{
	@Inject
	private RoundingMonthFinder finder;
	
	@Inject
	private RoundingMonthCommandHandler roundingMonthCommandHandler;
	
	/**
	 * Add excout rounding command handler
	 */
	@Inject
	private AddExcoutRoundingCommandHandler excOutHandler;
	
	/**
	 * Time rounding of excess outside finder
	 */
	@Inject
	private TimeRoundingOfExcessOutsideTimeFinder timeExcessFinder;
	
	@Path("findByCid")
	@POST
	public List<RoundingMonthDto> findByCid(String itemTimeId) {
		return finder.findAllRounding(itemTimeId);
	}
	
	@Path("updateRoundingMonth")
	@POST
	public void updateRoundingMonth(List<RoundingMonthCommand> command) {
		this.roundingMonthCommandHandler.handle(command);
	}
	
	
	@Path("updateExcoutRound")
	@POST
	public void updateExcoutRound(AddExcoutRoundingCommand command) {
		this.excOutHandler.handle(command);
	}
	
	@Path("findExcByCid")
	@POST
	public TimeRoundingOfExcessOutsideTimeDto findExcByCid() {
		return timeExcessFinder.findTimeRounding();
	}
}
