package nts.uk.file.com.ws.approval;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.app.file.export.ExportServiceResult;
import nts.arc.time.GeneralDate;
import nts.uk.file.com.app.EmployeeUnregisterOutputExportService;
import nts.uk.file.com.app.company.approval.MasterApproverRootExportService;
import nts.uk.file.com.app.company.approval.MasterApproverRootQuery;

@Path("approval/report")
@Produces("application/json") 
public class ApprovalReportWebService {

	@Inject
	private EmployeeUnregisterOutputExportService reportService;
	@Inject
	private MasterApproverRootExportService masterService;
	
	@POST
	@Path("employeeUnregister")
	public ExportServiceResult generate(GeneralDate query) {

		return this.reportService.start(query);
	}
	@GET
	@Path("b")
	public void test() {
		System.out.println("a");
	}
	
	@POST
	@Path("masterData")
	public ExportServiceResult generate(MasterApproverRootQuery query) {

		return this.masterService.start(query);
	}
}
