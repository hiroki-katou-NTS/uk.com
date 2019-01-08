package nts.uk.ctx.at.record.ws.calculation.holiday;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.at.record.app.command.holiday.roundingmonth.AddExcoutRoundingCommand;
import nts.uk.ctx.at.record.app.command.holiday.roundingmonth.AddExcoutRoundingCommandHandler;
import nts.uk.ctx.at.record.app.command.holiday.roundingmonth.RoundingMonthCommand;
import nts.uk.ctx.at.record.app.command.holiday.roundingmonth.RoundingMonthCommandHandler;
import nts.uk.ctx.at.record.app.find.holiday.roundingmonth.RoundingMonthDto;
import nts.uk.ctx.at.record.app.find.holiday.roundingmonth.RoundingMonthFinder;
import nts.uk.ctx.at.record.app.find.holiday.roundingmonth.TimeRoundingOfExcessOutsideTimeDto;
import nts.uk.ctx.at.record.app.find.holiday.roundingmonth.TimeRoundingOfExcessOutsideTimeFinder;

/**
 * Add monthly item rounding web service.
 *
 */
@Path("shared/caculation/holiday/rounding")
@Produces("application/json")
public class MonthItemRoundingWebService {

	/** The finder. */
	@Inject
	private RoundingMonthFinder finder;

	/** The rounding month command handler. */
	@Inject
	private RoundingMonthCommandHandler roundingMonthCommandHandler;
	
	/** Add excout rounding command handler. */
	@Inject
	private AddExcoutRoundingCommandHandler excOutHandler;

	/** Time rounding of excess outside finder. */
	@Inject
	private TimeRoundingOfExcessOutsideTimeFinder timeExcessFinder;

	/**
	 * Adds the.
	 *
	 * @param command
	 *            the command
	 */
//	@Path("add")
//	@POST
//	public void add(List<AddRoundingMonthCommand> command) {
//		this.handler.handle(command);
//	}

	/**
	 * Update.
	 *
	 * @param command
	 *            the command
	 */
//	@Path("update")
//	@POST
//	public void update(List<AddRoundingMonthCommand> command) {
//		this.handler.handle(command);
//	}

	/**
	 * Find by cid.
	 *
	 * @param itemTimeId
	 *            the item time id
	 * @return the list
	 */
	@Path("findByCid")
	@POST
	public List<RoundingMonthDto> findByCid() {
		return finder.findAllRounding();
	}

	/**
	 * Update rounding month.
	 *
	 * @param command
	 *            the command
	 */
	@Path("updateRoundingMonth")
	@POST
	public void updateRoundingMonth(List<RoundingMonthCommand> command) {
		this.roundingMonthCommandHandler.handle(command);
	}

	/**
	 * Update excout round.
	 *
	 * @param command
	 *            the command
	 */
	@Path("updateExcoutRound")
	@POST
	public void updateExcoutRound(AddExcoutRoundingCommand command) {
		this.excOutHandler.handle(command);
	}

	/**
	 * Find exc by cid.
	 *
	 * @return the time rounding of excess outside time dto
	 */
	@Path("findExcByCid")
	@POST
	public TimeRoundingOfExcessOutsideTimeDto findExcByCid() {
		return timeExcessFinder.findTimeRounding();
	}
}
