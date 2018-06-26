package nts.uk.ctx.at.record.ws.resultsperiod.optionalaggregationperiod;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.app.command.resultsperiod.optionalaggregationperiod.SaveOptionalAggrPeriodCommand;
import nts.uk.ctx.at.record.app.command.resultsperiod.optionalaggregationperiod.SaveOptionalAggrPeriodCommandHandler;
import nts.uk.ctx.at.record.app.find.resultsperiod.optionalaggregationperiod.OptionalAggrPeriodDto;
import nts.uk.ctx.at.record.app.find.resultsperiod.optionalaggregationperiod.OptionalAggrPeriodExecLogDto;
import nts.uk.ctx.at.record.app.find.resultsperiod.optionalaggregationperiod.OptionalAggrPeriodExecLogFinder;
import nts.uk.ctx.at.record.app.find.resultsperiod.optionalaggregationperiod.OptionalAggrPeriodFinder;

@Path("ctx/at/record/optionalaggr/")
@Produces("application/json")
public class OptionalAggrPeriodWs {

	@Inject
	private OptionalAggrPeriodFinder finder;
	
	@Inject
	private SaveOptionalAggrPeriodCommandHandler handler;
	
	@Inject
	private OptionalAggrPeriodExecLogFinder logFinder;
	
	/**
	 * Find all.
	 *
	 * @return the list
	 */
	@POST
	@Path("findall")
	public List<OptionalAggrPeriodDto> findAll() {
		return this.finder.findAll();
	}
	
	/**
	 * 
	 */
	@POST
	@Path("find/{aggrFrameCode}")
	public OptionalAggrPeriodDto find(@PathParam("aggrFrameCode") String aggrFrameCode) {
		return this.finder.find(aggrFrameCode);
	}
	
	/**
	 * Save.
	 *
	 * @param command the command
	 */
	@POST
	@Path("save")
	public void save(SaveOptionalAggrPeriodCommand command) {
		this.handler.handle(command);
	}
	
	/**
	 * Save.
	 *
	 * @param command the command
	 */
	@POST
	@Path("update")
	public void update(SaveOptionalAggrPeriodCommand command) {
		this.handler.handle(command);
	}
	
	@POST
	@Path("findbyperiod/{start}/{end}")
	public List<OptionalAggrPeriodExecLogDto> findByPeriod(@PathParam("start") String start, @PathParam("end") String end) {
		return this.logFinder.findLog(GeneralDate.fromString(end, "yyyy-MM-dd"), GeneralDate.fromString(end, "yyyy-MM-dd"));
	}
}
