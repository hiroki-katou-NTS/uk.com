package nts.uk.ctx.workflow.ws.approvermanagement.workroot;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.apache.logging.log4j.util.Strings;

import nts.arc.enums.EnumAdaptor;
import nts.arc.enums.EnumConstant;
import nts.arc.layer.ws.WebService;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.workflow.app.command.approvermanagement.setting.RegisterQCommand;
import nts.uk.ctx.workflow.app.command.approvermanagement.setting.SettingUseUnitCommand;
import nts.uk.ctx.workflow.app.command.approvermanagement.setting.StartQCommand;
import nts.uk.ctx.workflow.app.command.approvermanagement.workroot.ApplicationUseAtrFinderAppSet;
import nts.uk.ctx.workflow.app.command.approvermanagement.workroot.ApproverRegisterSetDto;
import nts.uk.ctx.workflow.app.command.approvermanagement.workroot.DeleteHistoryCmm053CmdHandler;
import nts.uk.ctx.workflow.app.command.approvermanagement.workroot.HistoryCmm053Command;
import nts.uk.ctx.workflow.app.command.approvermanagement.workroot.InsertHistoryCmm053CmdHandler;
import nts.uk.ctx.workflow.app.command.approvermanagement.workroot.RegisterAppApprovalRootCommand;
import nts.uk.ctx.workflow.app.command.approvermanagement.workroot.RegisterAppApprovalRootCommandHandler;
import nts.uk.ctx.workflow.app.command.approvermanagement.workroot.SettingUseUnitDto;
import nts.uk.ctx.workflow.app.command.approvermanagement.workroot.UpdateHistoryCmm053CmdHandler;
import nts.uk.ctx.workflow.app.command.approvermanagement.workroot.UpdateWorkAppApprovalRByHistCommand;
import nts.uk.ctx.workflow.app.command.approvermanagement.workroot.UpdateWorkAppApprovalRByHistCommandHandler;
import nts.uk.ctx.workflow.app.find.approvermanagement.workroot.Cmm053EmployeeSearchParam;
import nts.uk.ctx.workflow.app.find.approvermanagement.workroot.CommonApprovalRootDto;
import nts.uk.ctx.workflow.app.find.approvermanagement.workroot.CommonApprovalRootFinder;
import nts.uk.ctx.workflow.app.find.approvermanagement.workroot.DataFullDto;
import nts.uk.ctx.workflow.app.find.approvermanagement.workroot.EmployeeSearch;
import nts.uk.ctx.workflow.app.find.approvermanagement.workroot.OutputCheckRegCmm053;
import nts.uk.ctx.workflow.app.find.approvermanagement.workroot.ParamCheckRegCmm053;
import nts.uk.ctx.workflow.app.find.approvermanagement.workroot.ParamDto;
import nts.uk.ctx.workflow.app.find.approvermanagement.workroot.PastHistoryDto;
import nts.uk.ctx.workflow.dom.adapter.bs.EmployeeAdapter;
import nts.uk.ctx.workflow.dom.adapter.bs.PersonAdapter;
import nts.uk.ctx.workflow.dom.adapter.bs.SyJobTitleAdapter;
import nts.uk.ctx.workflow.dom.adapter.bs.dto.EmployeeImport;
import nts.uk.ctx.workflow.dom.adapter.bs.dto.JobTitleImport;
import nts.uk.ctx.workflow.dom.adapter.bs.dto.PersonImport;
import nts.uk.ctx.workflow.dom.adapter.employee.EmployeeWithRangeLoginImport;
import nts.uk.ctx.workflow.dom.adapter.workplace.WkpDepInfo;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApplicationType;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ConfirmationRootType;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.ApprovalRootCommonService;
import nts.uk.shr.com.context.AppContexts;
@Path("workflow/approvermanagement/workroot")
@Produces("application/json")
public class WorkAppApprovalRootWebService extends WebService{

	@Inject
	private CommonApprovalRootFinder comFinder;
	@Inject
	private UpdateWorkAppApprovalRByHistCommandHandler updateHist;
	@Inject
	private RegisterAppApprovalRootCommandHandler updateRoot;
	@Inject
	private PersonAdapter psInfo;
	@Inject
	private EmployeeAdapter employeeAdapter;
	@Inject
	private SyJobTitleAdapter jobTitle;
	@Inject
	private InsertHistoryCmm053CmdHandler insertByManager;
	@Inject
	private UpdateHistoryCmm053CmdHandler updateByManager;
	@Inject
	private DeleteHistoryCmm053CmdHandler deleteByManager;
	@Inject
	private ApprovalRootCommonService appRootCm;
	
	@Inject
	private ApplicationUseAtrFinderAppSet applicationUseAtrFinder;
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
	public List<EmployeeImport> findByWpkIds(EmployeeSearch employeeSearch){
		return employeeAdapter.findByWpkIdsWithParallel(AppContexts.user().companyId(),
				employeeSearch.getWorkplaceIds(), employeeSearch.getBaseDate(), employeeSearch.getSysAtr());		
	}
	 @POST
	 @Path("updateHistory")
	 public void updateHistory(UpdateWorkAppApprovalRByHistCommand command){
		 this.updateHist.handle(command);
	 }
		
	/**
	 * Enum 申請種類.
	 *
	 * @return the list
	 */
	@POST
	@Path("find/applicationType")
	public List<EnumConstant> findApplicationType() {
		return EnumAdaptor.convertToValueNameList(ApplicationType.class);
	}
	
	@POST
	@Path("updateRoot")
	public void updateRoot(RegisterAppApprovalRootCommand command){
		updateRoot.handle(command);
	}
	@POST
	@Path("getInforPerson")
	public PersonImport getPsInfor(String SID) {
		if(Strings.isBlank(SID)){
			SID = AppContexts.user().employeeId();
		}
		return psInfo.getPersonInfo(SID);
	}
	@POST
	@Path("getInforPsLogin")
	public PersonImport getPsInforLogin() {
		String sId = AppContexts.user().employeeId();
		return psInfo.getPersonInfo(sId);
	}
	@POST
	@Path("getInforJobTitle")
	public List<JobTitleImport> findAllJobTitle(GeneralDate baseDate){
		String companyId = AppContexts.user().companyId();
		return jobTitle.findAll(companyId, baseDate);
	}
	@POST
	@Path("getJobtitleName")
	public JobTitleImport findJobTitleByPositionId(JobTitleImport jobTitleInfo) {
		String companyId = AppContexts.user().companyId();
		return jobTitle.findJobTitleByPositionId(companyId, jobTitleInfo.getPositionId(), jobTitleInfo.getStartDate());
	}
	/**
	 * Enum 確認ルート種類.
	 *
	 * @return the list
	 */
	@POST
	@Path("find/confirmRootType")
	public List<EnumConstant> findConfirmRootType() {
		return EnumAdaptor.convertToValueNameList(ConfirmationRootType.class);
	}
	@POST
	@Path("find/wkpDepInfo")
	public WkpDepInfo getWpInfo(WkpDepParam param){
		return appRootCm.getWkpDepInfo(param.getId(), param.getSysAtr());
	}

	@POST
	@Path("find/getEmployeeByCode")
	public EmployeeWithRangeLoginImport getEmployeeByCode(Cmm053EmployeeSearchParam param){
		return comFinder.getEmployeeInfoByCode(param);
	}

	@POST
	@Path("find/settingOfManager/getPastHistory/{employeeId}")
	public List<PastHistoryDto> getPastHistory(@PathParam("employeeId") String employeeId) {
		return comFinder.getPastHistory(employeeId);
	}

	@POST
	@Path("managersetting/insert")
	public void insertHistoryByManagerSetting(HistoryCmm053Command command) {
		this.insertByManager.handle(command);
	}
	
	@POST
	@Path("managersetting/update")
	public void updateHistoryByManagerSetting(HistoryCmm053Command command) {
		this.updateByManager.handle(command);
	}
	
	@POST
	@Path("managersetting/delete")
	public void deleteHistoryByManagerSetting(HistoryCmm053Command command) {
		this.deleteByManager.handle(command);
	}
	@POST
	@Path("find/wkpInfo-login")
	public WkpDepInfo getWpInfoLogin(){
		return appRootCm.getWkpDepInfoLogin(0);
	}
	
	@POST
	@Path("find/depInfo-login")
	public WkpDepInfo getDepInfoLogin(){
		return appRootCm.getWkpDepInfoLogin(1);
	}
	
	@POST
	@Path("checkBfRegCMM053")
	public OutputCheckRegCmm053 checkBfResCmm053(ParamCheckRegCmm053 param){
		return comFinder.checkReg(param);
	}
	
	@POST
	@Path("appSet")
	public ApproverRegisterSetDto getAppSet(){
		return applicationUseAtrFinder.getAppSet(AppContexts.user().companyId());
	}
	// refactor5
	@POST
	@Path("appSetQ")
	public SettingUseUnitDto getAppSetForQ(StartQCommand command){
		return applicationUseAtrFinder.getStartQ(command);
	}
	
	@POST
	@Path("appSetM")
	public ApproverRegisterSetDto getAppUnitM(StartQCommand command) {
		return applicationUseAtrFinder.getStartM(command);
	}
	
	@POST
	@Path("checkRegisterQ")
	public void checkRegisterQ(RegisterQCommand command){
		applicationUseAtrFinder.checkRegisterQ(command);
	}
	
	@POST
	@Path("registerQ")
	public void registerQ(SettingUseUnitCommand command){
		applicationUseAtrFinder.registerQ(command);
	}
	
}