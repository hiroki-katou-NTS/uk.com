package nts.uk.cnv.ws.service;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.cnv.app.command.TableDesignImportCommand;
import nts.uk.cnv.app.command.TableDesignImportCommandHandler;
import nts.uk.cnv.app.dto.TableDesignExportDto;
import nts.uk.cnv.app.service.TableDesignerService;

@Path("cnv/tabledesign")
@Produces("application/json")
public class TableDesignWebService extends WebService{

	@Inject
	private TableDesignImportCommandHandler handler;

	@Inject
	private TableDesignerService tdService;
	
	@POST
	@Path("import")
	public void importTable(TableDesignImportCommand command) {
		handler.handle(command);
	}
	
	@POST
	@Path("exportddl")
	public String export(TableDesignExportDto params) {
		return tdService.exportDdl(params);
	}
}
