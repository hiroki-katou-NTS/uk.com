package nts.uk.cnv.ws.td.service;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.cnv.app.cnv.dto.ExportToFileDto;
import nts.uk.cnv.app.cnv.dto.TableDesignExportDto;
import nts.uk.cnv.app.td.service.ExcportDdlService;
import nts.uk.cnv.dom.td.service.ExportDdlServiceResult;

@Path("td/ddl")
@Produces("application/json")
public class ExportDDLWebService extends WebService{

	@Inject
	private ExcportDdlService tdService;

	@POST
	@Path("exportddl")
	public ExportDdlServiceResult export(TableDesignExportDto params) {
		return tdService.exportDdl(params);
	}

	@POST
	@Path("exporttofile")
	public void exportToFile(ExportToFileDto param) {
		tdService.exportToFile(param);
	}

	@POST
	@Path("exportconstraints")
	public void exportConstraintsDdl(ExportToFileDto param) {
		tdService.exportConstraintsDdl(param);
	}
}
