package nts.uk.ctx.at.schedule.ws.shift.estimate.aggregateset;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.schedule.app.command.shift.estimate.aggregateset.AggregateSettingCommand;
import nts.uk.ctx.at.schedule.app.command.shift.estimate.aggregateset.AggregateSettingCommandHandler;
import nts.uk.ctx.at.schedule.app.find.budget.premium.dto.PremiumItemDto;
import nts.uk.ctx.at.schedule.app.find.shift.estimate.aggregateset.AggregateSettingFindDto;
import nts.uk.ctx.at.schedule.app.find.shift.estimate.aggregateset.AggregateSettingFinder;

/**
 * The Class AggregateSettingWebService.
 */
@Path("at/schedule/shift/estimate/aggregateset")
@Produces("application/json")
public class AggregateSettingWebService extends WebService{
	
	/** The finder. */
	@Inject
	private AggregateSettingFinder finder;
	
	/** The save handler. */
	@Inject
	private AggregateSettingCommandHandler saveHandler;
	
	/**
	 * Find aggregate setting.
	 *
	 * @return the aggregate setting find dto
	 */
	@POST
	@Path("find")
	public AggregateSettingFindDto findAggregateSetting() {
		return this.finder.findData();
	}

	/**
	 * Save aggregate setting.
	 *
	 * @param command the command
	 */
	@POST
	@Path("save")
	void saveAggregateSetting(AggregateSettingCommand command) {
		this.saveHandler.handle(command);
	}
	
	@POST
	@Path("getListPremiumNo")
	public List<PremiumItemDto> getListPremiumNo() {
		return this.finder.findPremiumNo();
	}
}
