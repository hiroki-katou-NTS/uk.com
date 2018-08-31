package nts.uk.ctx.at.schedule.ws.schedule.setting.schemodifydeadline;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.schedule.app.command.schedule.setting.modifydeadline.AddPermissonCommand;
import nts.uk.ctx.at.schedule.app.command.schedule.setting.modifydeadline.AddPermissonCommandHandler;
import nts.uk.ctx.at.schedule.app.find.schedule.setting.modifydeadline.PermissonDto;
import nts.uk.ctx.at.schedule.app.find.schedule.setting.modifydeadline.PermissonFinder;
import nts.uk.ctx.at.schedule.app.find.schedule.setting.modifydeadline.description.ScheduleDescriptionDto;

@Path("ctx/at/schedule/setting/schemodifydeadline")
@Produces("application/json")
public class ScheModifyDeadlineWebService extends WebService{

	@Inject
	private PermissonFinder  finder;
	
	@Inject
	private AddPermissonCommandHandler handler;
	
	/**
	 * Find 
	 * 
	 * @return
	 */
	@Path("find/{roleId}")
	@POST
	public PermissonDto  findWorktypeControl(@PathParam ("roleId") String roleId) {
		return finder.getAll(roleId);
	}
	
	/**
	 * Find 
	 * 
	 * @return
	 */
	@Path("findDes")
	@POST
	public ScheduleDescriptionDto  findDes() {
		return finder.getAllDes();
	}
	
	/**
	 * 
	 * @param command
	 */
	@Path("add")
	@POST
	public void add(AddPermissonCommand command) {
		this.handler.handle(command);
	}
	
	/**
	 * 
	 * @param command
	 */
	@Path("update")
	@POST
	public void update(AddPermissonCommand command) {
		this.handler.handle(command);
	}
}
