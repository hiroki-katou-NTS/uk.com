package nts.uk.shr.sample.report.ws;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.app.file.export.ExportServiceResult;
import nts.uk.shr.sample.report.app.SampleReportExportService;
import nts.uk.shr.sample.report.app.SampleReportQuery;

@Path("/sample/report")
@Produces("application/json")
public class SampleReportWebService {

	@Inject
	private SampleReportExportService exportService;
	
	@POST
	@Path("generate")
	public ExportServiceResult generate(SampleReportQuery query) {
		
		return this.exportService.start(query);
	}
}
