package nts.uk.ctx.workflow.ws.approvermanagement.workroot;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.enums.EnumAdaptor;
import nts.arc.enums.EnumConstant;
import nts.arc.layer.ws.WebService;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.workflow.app.command.approvermanagement.workroot.UpdateWorkAppApprovalRByHistCommand;
import nts.uk.ctx.workflow.app.command.approvermanagement.workroot.UpdateWorkAppApprovalRByHistCommandHandler;
import nts.uk.ctx.workflow.app.find.approvermanagement.workroot.CommonApprovalRootDto;
import nts.uk.ctx.workflow.app.find.approvermanagement.workroot.CommonApprovalRootFinder;
import nts.uk.ctx.workflow.app.find.approvermanagement.workroot.DataFullDto;
import nts.uk.ctx.workflow.app.find.approvermanagement.workroot.EmployeeAdapterInforFinder;
import nts.uk.ctx.workflow.app.find.approvermanagement.workroot.EmployeeSearch;
import nts.uk.ctx.workflow.app.find.approvermanagement.workroot.EmployeeUnregisterFinder;
import nts.uk.ctx.workflow.app.find.approvermanagement.workroot.MasterApproverRootDto;
import nts.uk.ctx.workflow.app.find.approvermanagement.workroot.ParamDto;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApplicationType;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.employee.EmployeeApproveDto;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output.EmployeeUnregisterOutput;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output.MasterApproverRootOutput;
@Path("workflow/approvermanagement/workroot")
@Produces("application/json")
public class WorkAppApprovalRootWebService extends WebService{

	@Inject
	private CommonApprovalRootFinder comFinder;
	@Inject
	private EmployeeAdapterInforFinder employeeInfor;
	@Inject
	private UpdateWorkAppApprovalRByHistCommandHandler updateHist;
	
	@Inject
	private EmployeeUnregisterFinder empUnregister;
 
	@POST
	@Path("getbycom")
	public DataFullDto getAllByCom(ParamDto param) {
		return this.comFinder.getAllCommonApprovalRoot(param);
	}
	
	@POST
	@Path("getbyprivate")
	public CommonApprovalRootDto getAllByPrivate(ParamDto param) {
		return this.comFinder.getPrivate(param);
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
		
	/**
	 * Enumクラスの値一覧取得.
	 *
	 * @return the list
	 */
	@POST
	@Path("find/applicationType")
	public List<EnumConstant> findApplicationType() {
		return EnumAdaptor.convertToValueNameList(ApplicationType.class);
	}
	
	@POST
	@Path("testInUnregistry")
	public List<EmployeeUnregisterOutput> lstEmployeeUnregister(GeneralDate baseDate){
		//GeneralDate date = GeneralDate.fromString(baseDate, "yyyy-mm-dd");
		List<EmployeeUnregisterOutput> data =empUnregister.lstEmployeeUnregister(baseDate); 
		return data;
	}
	
	@POST
	@Path("testMasterDat")
	public MasterApproverRootOutput masterInfor(MasterApproverRootDto dto) {
		MasterApproverRootOutput data = empUnregister.masterInfors(dto);
		return data;
	}
}