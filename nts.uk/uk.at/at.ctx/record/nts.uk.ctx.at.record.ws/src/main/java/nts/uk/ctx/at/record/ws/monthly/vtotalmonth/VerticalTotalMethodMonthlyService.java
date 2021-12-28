package nts.uk.ctx.at.record.ws.monthly.vtotalmonth;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.uk.ctx.at.record.app.command.monthly.vtotalmethod.AddPayItemCountOfMonthlyCommand;
import nts.uk.ctx.at.record.app.command.monthly.vtotalmethod.AddPayItemCountOfMonthlyCommandHandler;
import nts.uk.ctx.at.record.app.command.monthly.vtotalmethod.AddVerticalTotalMethodOfMonthlyCommand;
import nts.uk.ctx.at.record.app.command.monthly.vtotalmethod.AddVerticalTotalMethodOfMonthlyCommandHandler;
import nts.uk.ctx.at.record.app.find.monthly.vtotalmethod.PayItemCountOfMonthlyDto;
import nts.uk.ctx.at.record.app.find.monthly.vtotalmethod.PayItemCountOfMonthlyFinder;
import nts.uk.ctx.at.record.app.find.monthly.vtotalmethod.VerticalTotalMethodOfMonDto;
import nts.uk.ctx.at.record.app.find.monthly.vtotalmethod.VerticalTotalMethodOfMonthlyDto;
import nts.uk.ctx.at.record.app.find.monthly.vtotalmethod.VerticalTotalMethodOfMonthlyFinder;
import nts.uk.ctx.at.record.app.find.monthly.vtotalmethod.WorkTypeDto;
import nts.uk.ctx.at.record.app.find.workrule.specific.SpecificWorkRuleFinder;

/**
 * The Class VerticalTotalMethodMonthlyService.
 *
 * @author HoangNDH
 */
@Path("record/monthly/vtotalmethod")
@Produces("application/json")
public class VerticalTotalMethodMonthlyService {
	
	/** The finder. */
	@Inject
	VerticalTotalMethodOfMonthlyFinder verticalFinder;
	
	/** The vertical handler. */
	@Inject
	AddVerticalTotalMethodOfMonthlyCommandHandler verticalHandler;
	
	/** The pay item handler. */
	@Inject
	AddPayItemCountOfMonthlyCommandHandler payItemHandler;
	
	/** The pay item finder. */
	@Inject
	PayItemCountOfMonthlyFinder payItemFinder;

	@Inject
	SpecificWorkRuleFinder specificWorkRuleFinder;

	@Path("init")
	@POST
	public VerticalTotalMethodOfMonDto initScreen() {
		return verticalFinder.init();
	}

	/**
	 * Register vertical setting.
	 *
	 * @param command the command
	 */
	@Path("registerVertical")
	@POST
	public void registerVerticalSetting(AddVerticalTotalMethodOfMonthlyCommand command) {
		this.verticalHandler.handle(command);
	}

	/**
	 * Find setting.
	 *
	 * @return the vertical total method of monthly dto
	 */
	@Path("read")
	@POST
	public VerticalTotalMethodOfMonthlyDto findVerticalSetting() {
		return verticalFinder.findSetting();
	}
	
	/**
	 * Register pay item setting.
	 *
	 * @param command the command
	 */
	@Path("registerPayItem")
	@POST
	public void registerPayItemSetting(AddPayItemCountOfMonthlyCommand command) {
		this.payItemHandler.handle(command);
	}
	
	/**
	 * Find pay item setting.
	 *
	 * @return the pay item count of monthly dto
	 */
	@Path("readPayItem")
	@POST
	public PayItemCountOfMonthlyDto findPayItemSetting() {
		return payItemFinder.findSetting();
	}
	
	/**
	 * Gets the work type.
	 *
	 * @param workTypeCode the work type code
	 * @return the work type
	 */
	@Path("getWorkType/{workType}")
	@POST
	public WorkTypeDto getWorkType(@PathParam("workType") String workTypeCode) {
		return payItemFinder.findWorkType(workTypeCode);
	}
}
