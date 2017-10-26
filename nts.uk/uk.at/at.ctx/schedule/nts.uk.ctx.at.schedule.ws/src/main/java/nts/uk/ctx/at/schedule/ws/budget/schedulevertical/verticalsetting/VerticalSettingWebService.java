package nts.uk.ctx.at.schedule.ws.budget.schedulevertical.verticalsetting;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.uk.ctx.at.schedule.app.command.budget.schedulevertical.verticalsetting.DeleteVerticalSettingCommandHandler;
import nts.uk.ctx.at.schedule.app.command.budget.schedulevertical.verticalsetting.VerticalSettingCommand;
import nts.uk.ctx.at.schedule.app.command.budget.schedulevertical.verticalsetting.VerticalSettingCommandHandler;
import nts.uk.ctx.at.schedule.app.find.budget.schedulevertical.verticalsetting.DailyItemsDto;
import nts.uk.ctx.at.schedule.app.find.budget.schedulevertical.verticalsetting.VerticalSettingDto;
import nts.uk.ctx.at.schedule.app.find.budget.schedulevertical.verticalsetting.VerticalSettingFinder;

/**
 * 
 * @author tanlv
 *
 */

@Path("ctx/at/schedule/budget/verticalsetting")
@Produces("application/json")
public class VerticalSettingWebService {
	@Inject
	private VerticalSettingFinder verticalSettingFinder;
	
	@Inject
	private VerticalSettingCommandHandler verticalSettingCommandHandler;
	
	@Inject
	private DeleteVerticalSettingCommandHandler deleteVerticalSettingCommandHandler;
	
	/** Find all vertical cal set. */
	@Path("findAllVerticalCalSet")
	@POST
	public List<VerticalSettingDto> findAllVerticalCalSet() {
		return verticalSettingFinder.findAllVerticalCalSet();
	}
	
	/** Find vertical cal set by Cd. */
	@Path("getVerticalCalSetByCode/{verticalCalCd}")
	@POST
	public VerticalSettingDto getVerticalCalSetByCode(@PathParam("verticalCalCd") String verticalCalCd) {
		return verticalSettingFinder.getVerticalCalSetByCode(verticalCalCd);
	}
	
	/** Find Daily Items by attribute. */
	@Path("getDailyItems/{attribute}")
	@POST
	public List<DailyItemsDto> getDailyItems(@PathParam("attribute") int attribute) {
		return verticalSettingFinder.getDailyItems(attribute);
	}
	
	/** Add new vertical cal set. */
	@Path("addVerticalCalSet")
	@POST
	public void addVerticalCalSet(VerticalSettingCommand command) { 
		this.verticalSettingCommandHandler.handle(command);
	}
	
	/** Delete vertical cal set. */
	@Path("deleteVerticalCalSet")
	@POST
	public void deleteVerticalCalSet(VerticalSettingCommand command) { 
		this.deleteVerticalSettingCommandHandler.handle(command);
	}
}
