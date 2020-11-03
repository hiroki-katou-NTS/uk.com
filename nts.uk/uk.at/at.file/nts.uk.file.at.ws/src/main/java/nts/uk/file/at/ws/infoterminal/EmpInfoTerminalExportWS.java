package nts.uk.file.at.ws.infoterminal;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.app.file.export.ExportServiceResult;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.repo.EmpInfoTerminalRepository;
import nts.uk.file.at.app.export.employmentinfoterminal.infoterminal.EmpInfoTerminalExportDatasource;
import nts.uk.file.at.app.export.employmentinfoterminal.infoterminal.EmpInfoTerminalExportService;

/**
 * 
 * @author huylq
 *
 */
@Path("file/empInfoTerminal/report")
@Produces("application/json")
public class EmpInfoTerminalExportWS {
	
	@Inject
	private EmpInfoTerminalRepository empInfoTerminalRepository;
	
	@Inject
	private EmpInfoTerminalExportService empInfoTerminalExportService;
	
	@POST
	@Path("export")
	public ExportServiceResult generate() {
		EmpInfoTerminalExportDatasource datasource = null;
		return this.empInfoTerminalExportService.start(datasource);
	}
}
