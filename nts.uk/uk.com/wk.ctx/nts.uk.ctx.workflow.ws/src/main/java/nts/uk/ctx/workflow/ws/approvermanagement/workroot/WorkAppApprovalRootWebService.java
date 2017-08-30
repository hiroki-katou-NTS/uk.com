package nts.uk.ctx.workflow.ws.approvermanagement.workroot;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.workflow.app.command.approvermanagement.workroot.UpdateWorkAppApprovalRByHistCommand;
import nts.uk.ctx.workflow.app.command.approvermanagement.workroot.UpdateWorkAppApprovalRByHistCommandHandler;
import nts.uk.ctx.workflow.app.find.approvermanagement.workroot.CommonApprovalRootDto;
import nts.uk.ctx.workflow.app.find.approvermanagement.workroot.CommonApprovalRootFinder;
import nts.uk.ctx.workflow.app.find.approvermanagement.workroot.EmployeeAdapterInforFinder;
import nts.uk.ctx.workflow.app.find.approvermanagement.workroot.EmployeeSearch;
import nts.uk.ctx.workflow.app.find.approvermanagement.workroot.PrivateApprovalRootDto;
import nts.uk.ctx.workflow.app.find.approvermanagement.workroot.PrivateApprovalRootFinder;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.employee.EmployeeApproveDto;
@Path("workflow/approvermanagement/workroot")
@Produces("application/json")
public class WorkAppApprovalRootWebService extends WebService{

	@Inject
	private CommonApprovalRootFinder comFinder;
	@Inject
	private PrivateApprovalRootFinder privateFinder;
	@Inject
	private EmployeeAdapterInforFinder employeeInfor;
	@Inject
	private UpdateWorkAppApprovalRByHistCommandHandler updateHist;
 
	@POST
	@Path("getbycom")
	public CommonApprovalRootDto getAllByCom(int rootType, String employeeId) {
		return this.comFinder.getAllCommonApprovalRoot(rootType, employeeId);
	}
	
	@POST
	@Path("getbyperson")
	public List<PrivateApprovalRootDto> getAllByPerson(@PathParam("employeeId") String employeeId) {
		return this.privateFinder.getAllPrivateApprovalRoot(employeeId);
	}
	@POST
	@Path("getEmployeesInfo")
	public List<EmployeeApproveDto> findByWpkIds(EmployeeSearch employeeSearch){
		return employeeInfor.findEmployeeByWpIdAndBaseDate(employeeSearch.getWorkplaceCodes(), employeeSearch.getBaseDate());		
	}
	 @POST
	 @Path("updateHistory")
	 public void updateHistory(UpdateWorkAppApprovalRByHistCommand command){
		 this.updateHist.handle(command);
	 }
}