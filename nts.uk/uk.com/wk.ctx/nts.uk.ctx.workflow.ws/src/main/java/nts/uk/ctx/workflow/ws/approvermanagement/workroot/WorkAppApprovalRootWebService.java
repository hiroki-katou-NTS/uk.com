package nts.uk.ctx.workflow.ws.approvermanagement.workroot;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.workflow.app.find.approvermanagement.workroot.CommonApprovalRootDto;
import nts.uk.ctx.workflow.app.find.approvermanagement.workroot.CommonApprovalRootFinder;
import nts.uk.ctx.workflow.app.find.approvermanagement.workroot.PrivateApprovalRootDto;
import nts.uk.ctx.workflow.app.find.approvermanagement.workroot.PrivateApprovalRootFinder;
@Path("workflow/approvermanagement/workroot")
@Produces("application/json")
public class WorkAppApprovalRootWebService extends WebService{

	@Inject
	private CommonApprovalRootFinder comFinder;
	@Inject
	private PrivateApprovalRootFinder privateFinder;
	
	@POST
	@Path("getbycom")
	public List<CommonApprovalRootDto> getAllByCom() {
		return this.comFinder.getAllCommonApprovalRoot();
	}
	
	@POST
	@Path("getbyperson")
	public List<PrivateApprovalRootDto> getAllByPerson(@PathParam("employeeId") String employeeId) {
		return this.privateFinder.getAllPrivateApprovalRoot(employeeId);
	}
}