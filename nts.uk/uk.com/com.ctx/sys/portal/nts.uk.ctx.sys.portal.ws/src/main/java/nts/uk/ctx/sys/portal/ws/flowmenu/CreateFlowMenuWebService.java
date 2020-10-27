package nts.uk.ctx.sys.portal.ws.flowmenu;

import java.util.Map;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.app.file.export.ExportServiceResult;
import nts.arc.layer.ws.WebService;
import nts.uk.ctx.sys.portal.app.command.flowmenu.CopyFlowMenuCommand;
import nts.uk.ctx.sys.portal.app.command.flowmenu.CopyFlowMenuCommandHandler;
import nts.uk.ctx.sys.portal.app.command.flowmenu.DeleteCreateFlowMenuCommandHandler;
import nts.uk.ctx.sys.portal.app.command.flowmenu.DeleteFlowMenuCommand;
import nts.uk.ctx.sys.portal.app.command.flowmenu.FileExportCommand;
import nts.uk.ctx.sys.portal.app.command.flowmenu.FileExportService;
import nts.uk.ctx.sys.portal.app.command.flowmenu.RegisterFlowMenuCommand;
import nts.uk.ctx.sys.portal.app.command.flowmenu.RegisterFlowMenuCommandHandler;
import nts.uk.ctx.sys.portal.app.command.flowmenu.UpdateCreateFlowMenuCommandHandler;
import nts.uk.ctx.sys.portal.app.command.flowmenu.UpdateFlowMenuCommand;
import nts.uk.ctx.sys.portal.app.command.flowmenu.UpdateFlowMenuLayoutCommand;
import nts.uk.ctx.sys.portal.app.command.flowmenu.UpdateFlowMenuLayoutCommandHandler;
import nts.uk.ctx.sys.portal.app.screenquery.flowmenu.CreateFlowMenuDto;
import nts.uk.ctx.sys.portal.app.screenquery.flowmenu.GetFlowMenuListScreenQuery;
import nts.uk.ctx.sys.portal.app.screenquery.flowmenu.GetFlowMenuScreenQuery;

@Path("sys/portal/flowmenu")
@Produces("application/json")
public class CreateFlowMenuWebService extends WebService {

	@Inject
	private GetFlowMenuScreenQuery getFlowMenuScreenQuery;
	
	@Inject
	private GetFlowMenuListScreenQuery getFlowMenuListScreenQuery;
	
	@Inject
	private RegisterFlowMenuCommandHandler registerFlowMenuCommandHandler;
	
	@Inject
	private DeleteCreateFlowMenuCommandHandler deleteFlowMenuCommandHandler;
	
	@Inject
	private UpdateCreateFlowMenuCommandHandler updateFlowMenuCommandHandler;
	
	@Inject
	private UpdateFlowMenuLayoutCommandHandler updateFlowMenuLayoutCommandHandler;
	
	@Inject
	private CopyFlowMenuCommandHandler copyFlowMenuCommandHandler;
	
	@Inject
	private FileExportService exportService;
	
	@POST
	@Path("/getFlowMenu/{flowMenuCode}")
	public CreateFlowMenuDto getFlowMenu(@PathParam(value = "flowMenuCode") String flowMenuCode) {
		return this.getFlowMenuScreenQuery.getFlowMenu(flowMenuCode);
	}
	
	@POST
	@Path("/getFlowMenu")
	public Map<String, String> getFlowMenu() {
		return this.getFlowMenuListScreenQuery.getList();
	}
	
	@POST
	@Path("/register")
	public void register(RegisterFlowMenuCommand command) {
		this.registerFlowMenuCommandHandler.handle(command);
	}
	
	@POST
	@Path("/update")
	public void update(UpdateFlowMenuCommand command) {
		this.updateFlowMenuCommandHandler.handle(command);
	}
	
	@POST
	@Path("/updateLayout")
	public void update(UpdateFlowMenuLayoutCommand command) {
		this.updateFlowMenuLayoutCommandHandler.handle(command);
	}
	
	@POST
	@Path("/delete")
	public void delete(DeleteFlowMenuCommand command) {
		this.deleteFlowMenuCommandHandler.handle(command);
	}
	
	@POST
	@Path("/copy")
	public void copy(CopyFlowMenuCommand command) {
		this.copyFlowMenuCommandHandler.handle(command);
	}
	
	@POST
	@Path("/export")
	public ExportServiceResult generate(FileExportCommand command) {
		return this.exportService.start(command);
	}
	
	@POST
	@Path("/extract/{fileId}")
	public String extractData(@PathParam("fileId") String fileId) {
		return null;
	}
}
