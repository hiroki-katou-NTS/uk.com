/**
 * @author hieult
 */
package nts.uk.ctx.sys.portal.ws.flowmenu;

import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import nts.arc.layer.ws.WebService;
import nts.uk.ctx.sys.portal.app.command.flowmenu.CreateFlowMenuCommand;
import nts.uk.ctx.sys.portal.app.command.flowmenu.CreateFlowMenuCommandHandler;
import nts.uk.ctx.sys.portal.app.command.flowmenu.DeleteFlowMenuCommand;
import nts.uk.ctx.sys.portal.app.command.flowmenu.DeleteFlowMenuCommandHandler;
import nts.uk.ctx.sys.portal.app.command.flowmenu.UpdateFlowMenuCommand;
import nts.uk.ctx.sys.portal.app.command.flowmenu.UpdateFlowMenuCommandHandler;
import nts.uk.ctx.sys.portal.app.find.flowmenu.FlowMenuDto;
import nts.uk.ctx.sys.portal.app.find.flowmenu.FlowMenuFinder;

@Path("sys/portal/flowmenu")
@Produces("application/json")
public class FlowMenuWebservice extends WebService {

	@Inject
	private CreateFlowMenuCommandHandler createFlowMenu;

	@Inject
	private DeleteFlowMenuCommandHandler deleteFlowMenu;

	@Inject
	private UpdateFlowMenuCommandHandler updateFlowMenu;

	@Inject
	private FlowMenuFinder finder;

	@POST
	@Path("findall")
	public List<FlowMenuDto> getAllFlowMenu() {
		return this.finder.getAllFlowMenu();
	}

	@POST
	@Path("findbycode")
	public void getByCode(@PathParam("companyID") String toppagePartID) {
		this.finder.getFlowMenu(toppagePartID);

	}

	@POST
	@Path("create")
	public void createFlowMenu(CreateFlowMenuCommand command) {
		this.createFlowMenu.handle(command);
	}

	@POST
	@Path("delete")
	public void deleteFlowMenu(DeleteFlowMenuCommand command) {
		this.deleteFlowMenu.handle(command);
	}
	
	@POST
	@Path("update")
	public void UpdateFlowMenu(UpdateFlowMenuCommand command) {
		this.updateFlowMenu.handle(command);
	}

}
