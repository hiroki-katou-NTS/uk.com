package nts.uk.file.com.ws.approvalmanagement.workroot;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.app.file.export.ExportServiceResult;
import nts.uk.file.com.app.approvalmanagement.workroot.ApproversExportService;
import nts.uk.file.com.app.approvalmanagement.workroot.ApproversQuery;

@Path("com/file/approvalmanagement/workroot/approvers")
@Produces("application/json")
public class ApproversReportWebService {

	@Inject
	private ApproversExportService approversExportService;
	
	@POST
	@Path("export")
	public ExportServiceResult export(ApproversQuery query) {
		return this.approversExportService.start(query);
	}
}
