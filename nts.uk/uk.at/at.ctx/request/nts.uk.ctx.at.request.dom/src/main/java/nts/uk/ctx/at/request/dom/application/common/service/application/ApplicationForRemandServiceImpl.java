package nts.uk.ctx.at.request.dom.application.common.service.application;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.EmployeeRequestAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.jobtitle.AtJobTitleAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.jobtitle.dto.AffJobTitleHistoryImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.ApprovalRootStateAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApproverRemandImport;
import nts.uk.ctx.at.request.dom.application.common.service.application.output.ApplicationForRemandOutput;
import nts.uk.ctx.at.request.dom.application.common.service.application.output.RemandInfoKDL034;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class ApplicationForRemandServiceImpl implements IApplicationForRemandService{
	
	@Inject
	private ApplicationRepository applicationRepository;
	
	@Inject
	private ApprovalRootStateAdapter apprRootStt;
	
	@Inject
	private EmployeeRequestAdapter employeeRequestAdapter;
	
	@Inject
	private AtJobTitleAdapter jobTitleAdapter;
	
	/**
	 * 画面表示
	 * KDL034
	 * 差し戻し先リストを取得する
	 */
	@Override
	public ApplicationForRemandOutput getApplicationForRemand(List<String> lstAppID) {
		String companyID = AppContexts.user().companyId();
		String sidLogin = AppContexts.user().employeeId();
		String appID = lstAppID.get(0);
		//申請IDをキーにドメインモデル「申請」「承認フェーズ」「承認枠」を取得する
		Optional<Application> application = this.applicationRepository.findByID(companyID, appID);
		if (!application.isPresent()){
			return null;
		}
		Application app = application.get();
		//Imported（承認申請）「差し戻し対象者一覧を取得」
		List<ApproverRemandImport> lstRemand = apprRootStt.getListApproverRemand(appID);
		
		List<RemandInfoKDL034> lstApprover  = new ArrayList<>();
		int phaseLogin = 0;
		for (ApproverRemandImport remand : lstRemand) {
			String sID = remand.getSID();
			phaseLogin = sID.equals(sidLogin) ? remand.getPhaseOrder() : phaseLogin;
			Optional<AffJobTitleHistoryImport> approverJobTitle = jobTitleAdapter.getJobTitlebBySIDAndDate(sID, app.getAppDate().getApplicationDate());
			String jobTitle = approverJobTitle.isPresent() ? approverJobTitle.get().getJobTitleName() : "";
			String approverName = employeeRequestAdapter.getEmployeeName(sID);
			lstApprover.add(new RemandInfoKDL034(remand.getPhaseOrder(), sID, approverName, jobTitle, remand.isAgent()));
		}
		String applicantName = employeeRequestAdapter.getEmployeeName(app.getEmployeeID());
		Optional<AffJobTitleHistoryImport> job = jobTitleAdapter.getJobTitlebBySIDAndDate(app.getEmployeeID(), app.getAppDate().getApplicationDate());
		String applicantJob = job.isPresent() ? job.get().getJobTitleName() : "";
		return new ApplicationForRemandOutput(appID, app.getVersion(),
									app.getEmployeeID(), applicantName, applicantJob,
									phaseLogin, lstApprover);
	}
}
