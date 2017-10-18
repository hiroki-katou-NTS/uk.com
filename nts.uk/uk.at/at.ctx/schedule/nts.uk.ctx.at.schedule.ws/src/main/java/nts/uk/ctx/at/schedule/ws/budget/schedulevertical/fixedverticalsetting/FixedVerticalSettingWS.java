package nts.uk.ctx.at.schedule.ws.budget.schedulevertical.fixedverticalsetting;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.schedule.app.command.budget.schedulevertical.fixedverticalsetting.AddFixedVerticalSettingCommandHandler;
import nts.uk.ctx.at.schedule.app.command.budget.schedulevertical.fixedverticalsetting.AddVerticalCntSettingCommand;
import nts.uk.ctx.at.schedule.app.command.budget.schedulevertical.fixedverticalsetting.AddVerticalCntSettingCommandHandler;
import nts.uk.ctx.at.schedule.app.command.budget.schedulevertical.fixedverticalsetting.AddVerticalTimeSettingCommand;
import nts.uk.ctx.at.schedule.app.command.budget.schedulevertical.fixedverticalsetting.AddVerticalTimeSettingCommandHandler;
import nts.uk.ctx.at.schedule.app.command.budget.schedulevertical.fixedverticalsetting.FixedVerticalSettingCommand;
import nts.uk.ctx.at.schedule.app.find.budget.schedulevertical.fixedverticalsetting.FixedVerticalSettingDto;
import nts.uk.ctx.at.schedule.app.find.budget.schedulevertical.fixedverticalsetting.FixedVerticalSettingFinder;
import nts.uk.ctx.at.schedule.app.find.budget.schedulevertical.fixedverticalsetting.VerticalCntSettingDto;
import nts.uk.ctx.at.schedule.app.find.budget.schedulevertical.fixedverticalsetting.VerticalCntSettingFinder;
import nts.uk.ctx.at.schedule.app.find.budget.schedulevertical.fixedverticalsetting.VerticalTimeSettingDto;
import nts.uk.ctx.at.schedule.app.find.budget.schedulevertical.fixedverticalsetting.VerticalTimeSettingFinder;
/**
 * 
 * @author phongtq
 *
 */
@Path("ctx/at/schedule/budget/fixedverticalsetting")
@Produces("application/json")
public class FixedVerticalSettingWS extends WebService{
	@Inject
	private FixedVerticalSettingFinder verticalSettingFinder;
	
	@Inject
	private VerticalTimeSettingFinder verticalTimeSettingFinder;
	
	@Inject
	private AddFixedVerticalSettingCommandHandler addVerticalSettingCommandHandler;
	
	@Inject
	private AddVerticalTimeSettingCommandHandler addVerticalTimeSettingCommandHandler;
	
	@Inject
	private AddVerticalCntSettingCommandHandler addVerticalCountSettingCommandHandler;
	
	@Inject
	private VerticalCntSettingFinder verticalCntSettingFinder;
	
	@Path("findByCid")
	@POST
	public List<FixedVerticalSettingDto> findByCid() {
		return verticalSettingFinder.findByCid();

	}
	
	@Path("find/{fixedItemAtr}")
	@POST
	public List<VerticalTimeSettingDto> findAll(@PathParam("fixedItemAtr") int fixedItemAtr) {
		return verticalTimeSettingFinder.findAll(fixedItemAtr);

	}
	
	@Path("findCnt/{fixedItemAtr}")
	@POST
	public List<VerticalCntSettingDto> findAllCnt(@PathParam("fixedItemAtr") int fixedItemAtr) {
		return verticalCntSettingFinder.findAll(fixedItemAtr);

	}
	
	@Path("addFixedVertical")
	@POST
	public void addCnt(List<FixedVerticalSettingCommand> command) { 
		this.addVerticalSettingCommandHandler.handle(command);
	}
	
	@Path("addVerticalTime")
	@POST
	public void add(AddVerticalTimeSettingCommand command) {
		this.addVerticalTimeSettingCommandHandler.handle(command);
	}
	
	@Path("addVerticalCnt")
	@POST
	public void add(AddVerticalCntSettingCommand command) {
		this.addVerticalCountSettingCommandHandler.handle(command);
	}
	
}
