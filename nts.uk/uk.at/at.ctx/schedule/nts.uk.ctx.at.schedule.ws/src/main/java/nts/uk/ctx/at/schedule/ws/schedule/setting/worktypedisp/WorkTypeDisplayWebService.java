package nts.uk.ctx.at.schedule.ws.schedule.setting.worktypedisp;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.schedule.app.command.schedule.setting.worktypedisplay.AddWorktypeDisplayCommand;
import nts.uk.ctx.at.schedule.app.command.schedule.setting.worktypedisplay.AddWorktypeDisplayCommandHandler;
import nts.uk.ctx.at.schedule.app.find.schedule.setting.worktypedisplay.WorktypeDisplayDto;
import nts.uk.ctx.at.schedule.app.find.schedule.setting.worktypedisplay.WorktypeDisplayFinder;

@Path("ctx/at/schedule/setting/worktypedisp")
@Produces("application/json")
public class WorkTypeDisplayWebService extends WebService{
	@Inject
	private WorktypeDisplayFinder  finder;
	
	@Inject
	private AddWorktypeDisplayCommandHandler handler;
	
	/**
	 * Find 
	 * 
	 * @return
	 */
	@Path("find")
	@POST
	public WorktypeDisplayDto findWorktypeControl() {
		return finder.getWorkType();
	}
	
	@Path("add")
	@POST
	public void add(AddWorktypeDisplayCommand command) {
		this.handler.handle(command);
	}
	
	@Path("update")
	@POST
	public void update(AddWorktypeDisplayCommand command) {
		this.handler.handle(command);
	}
}
