package nts.uk.ctx.sys.portal.ws.flowmenu;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.app.file.export.ExportServiceResult;
import nts.arc.layer.ws.WebService;
import nts.uk.ctx.sys.portal.app.command.flowmenu.FileExportCommand;
import nts.uk.ctx.sys.portal.app.command.flowmenu.FileExportService;

@Path("sys/portal/flowmenu")
@Produces("application/json")
public class ExportHtmlWebService extends WebService {

	@Inject
	private FileExportService exportService;
	
	@POST
	@Path("/export")
	public ExportServiceResult generate(FileExportCommand command) {
		return this.exportService.start(command);
	}
}
