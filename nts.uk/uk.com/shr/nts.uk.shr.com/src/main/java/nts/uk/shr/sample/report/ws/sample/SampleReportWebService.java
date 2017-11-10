package nts.uk.shr.sample.report.ws.sample;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.app.file.export.ExportServiceResult;
import nts.uk.shr.sample.aspose.SampleAsposeExportService;
import nts.uk.shr.sample.report.app.export.sample.SampleReportExportService;
import nts.uk.shr.sample.report.app.export.sample.SampleReportQuery;

@Path("/sample/report")
@Produces("application/json")
public class SampleReportWebService {

	@Inject
	private SampleReportExportService exportService;
	
	@Inject
	private SampleAsposeExportService asposeService;
	
	@POST
	@Path("generate")
	public ExportServiceResult generate(SampleReportQuery query) {
		return this.exportService.start(query);
	}
	
	@POST
	@Path("stripe")
	public ExportServiceResult stripe() {
		return this.asposeService.start(null);
	}
}
