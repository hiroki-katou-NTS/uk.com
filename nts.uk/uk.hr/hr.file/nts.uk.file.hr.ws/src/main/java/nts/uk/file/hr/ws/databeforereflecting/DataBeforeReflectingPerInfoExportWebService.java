package nts.uk.file.hr.ws.databeforereflecting;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.app.file.export.ExportServiceResult;
import nts.uk.file.hr.app.databeforereflecting.DataBeforeReflectingPerInfoExportService;

@Path("file/hr/report/databeforereflecting")
@Produces("application/json")
public class DataBeforeReflectingPerInfoExportWebService {
	@Inject
	private DataBeforeReflectingPerInfoExportService service;
	
	@POST
	@Path("export")
	public ExportServiceResult generate() {
		return service.start(null);
	}
}
