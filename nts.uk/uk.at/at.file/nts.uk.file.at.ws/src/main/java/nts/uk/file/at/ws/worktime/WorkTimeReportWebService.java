package nts.uk.file.at.ws.worktime;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.app.file.export.ExportServiceResult;
import nts.arc.layer.ws.WebService;
import nts.uk.file.at.app.export.worktime.WorkTimExportDto;
import nts.uk.file.at.app.export.worktime.WorkTimeExportService;

@Path("at/file/worktime/report")
@Produces("application/json")
public class WorkTimeReportWebService extends WebService {
	
	@Inject
	private WorkTimeExportService exportService;
	
	@POST
	@Path("export")
	public ExportServiceResult generate(WorkTimExportDto workTimeExportDto) {
		return this.exportService.start(workTimeExportDto);
	}
}
