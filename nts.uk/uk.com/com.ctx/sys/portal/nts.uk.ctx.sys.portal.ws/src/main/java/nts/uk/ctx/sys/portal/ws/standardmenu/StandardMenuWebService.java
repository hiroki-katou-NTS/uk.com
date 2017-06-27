package nts.uk.ctx.sys.portal.ws.standardmenu;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.sys.portal.app.command.standardmenu.StandardMenuCommand;
import nts.uk.ctx.sys.portal.app.command.standardmenu.UpdateStandardMenuCommand;
import nts.uk.ctx.sys.portal.app.command.standardmenu.UpdateStandardMenuCommandHandler;
import nts.uk.ctx.sys.portal.app.command.titlemenu.UpdateTitleMenuCommand;
import nts.uk.ctx.sys.portal.app.command.titlemenu.UpdateTitleMenuCommandHandler;
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
	@Path("findByAfterLoginDisplay")
	public List<StandardMenuDto> findByAfterLoginDisplay() {
		return finder.findByAfterLoginDisplay(0);
	}

	@POST
	@Path("findByAfterLgDisSysMenuCls")
	public List<StandardMenuDto> findByAfterLgDisSysMenuCls() {
		return finder.findByAfterLgDisSysMenuCls();
	}
	
	@POST
	@Path("findByAtr")
	public List<StandardMenuDto> findByAtr(int webMenuSetting, int menuAtr  ) {
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
}
