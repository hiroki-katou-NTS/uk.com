package nts.uk.ctx.sys.portal.ws.standardmenu;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.app.command.JavaTypeResult;
import nts.arc.layer.ws.WebService;
import nts.uk.ctx.sys.portal.app.command.standardmenu.StandardMenuCommand;
import nts.uk.ctx.sys.portal.app.command.standardmenu.UpdateStandardMenuCommand;
import nts.uk.ctx.sys.portal.app.command.standardmenu.UpdateStandardMenuCommandHandler;
import nts.uk.ctx.sys.portal.app.find.standardmenu.StandardMenuDto;
import nts.uk.ctx.sys.portal.app.find.standardmenu.StandardMenuFinder;

/**
 * @author tanlv
 */
@Path("sys/portal/standardmenu")
@Produces("application/json")
public class StandardMenuWebService extends WebService {
	@Inject
	private StandardMenuFinder finder;
	
	@Inject
	private UpdateStandardMenuCommandHandler updateStandardMenu;

	@POST
	@Path("findAll")
	public List<StandardMenuDto> findAll() {
		return finder.findAll();
	}

	@POST
	@Path("findByAfterLoginDisplay/{afterLoginDisplay}")
	public List<StandardMenuDto> findByAfterLoginDisplay(@PathParam("afterLoginDisplay") int afterLoginDisplay) {
		return finder.findByAfterLoginDisplay(afterLoginDisplay);
	}

	@POST
	@Path("findBySystemMenuCls")
	public List<StandardMenuDto> findBySystemMenuCls() {
		return finder.findBySystemMenuCls();
	}
	
	@POST
	@Path("findDataForAfterLoginDis")
	public List<StandardMenuDto> findDataForAfterLoginDis() {
		return finder.findDataForAfterLoginDis();
	}

	@POST
	@Path("findByAtr")
	public List<StandardMenuDto> findByAtr(int webMenuSetting, int menuAtr) {
		return finder.findByAtr(webMenuSetting, menuAtr);
	}
	
	@POST
	@Path("update")
	public void updateStandardMenu(List<StandardMenuCommand>  command) {
		// List<StandardMenuCommand> 
		//UpdateStandardMenuCommand
		UpdateStandardMenuCommand  obj = new UpdateStandardMenuCommand();
		obj.setStandardMenus(command);
		this.updateStandardMenu.handle(obj);
	}

	@POST
	@Path("findAllDisplay")
	public List<StandardMenuDto> findAllDisplay() {
		return finder.findAllDisplay();

	}
	
	@POST
	@Path("findProgramName/{programID}/{screenID}")
	public JavaTypeResult<String> getProgramName(@PathParam("programID") String programID, @PathParam("screenID") String screenID) {
		return new JavaTypeResult<String>(finder.getProgramName(programID, screenID));
	}
	
}
