package nts.uk.file.com.ws.company;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.app.file.export.ExportServiceResult;
import nts.arc.layer.ws.WebService;
import nts.uk.file.com.app.company.approval.MasterApproverRootExportService;
import nts.uk.file.com.app.company.approval.MasterApproverRootQuery;

@Path("company/approver/report")
@Produces("application/json") 
public class MasterApproverRootWebService extends WebService{
	
	@Inject
	private MasterApproverRootExportService masterService;
	
	@POST
	@Path("unRegister")
	public ExportServiceResult generate(MasterApproverRootQuery query) {

		return this.masterService.start(query);
	}
	
	@GET
	@Path("b")
	public void test() {
		System.out.println("a");
	}

}
