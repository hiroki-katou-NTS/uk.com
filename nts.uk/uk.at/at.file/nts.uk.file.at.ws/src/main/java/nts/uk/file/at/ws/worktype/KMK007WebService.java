package nts.uk.file.at.ws.worktype;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.app.file.export.ExportServiceResult;
import nts.arc.layer.ws.WebService;
import nts.uk.file.at.app.export.worktype.WorkTypeReportQuery;
import nts.uk.file.at.app.export.worktype.WorkTypeReportService;

@Path("/file/at/worktypereport")
@Produces("application/json")
public class KMK007WebService extends WebService {

	@Inject
	private WorkTypeReportService workTypeReportWebService;
	
	@POST
	@Path("saveAsExcel")
	public ExportServiceResult exportWorkTypeReport(WorkTypeReportQuery query){
		return workTypeReportWebService.start(query);
	}
}
