package nts.uk.ctx.at.request.dom.application.common.service.application;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.dom.application.ApplicationRepository_New;
import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.EmployeeRequestAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.jobtitle.AtJobTitleAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.jobtitle.dto.AffJobTitleHistoryImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.ApprovalRootAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.ApprovalRootStateAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalRootContentImport_New;
import nts.uk.ctx.at.request.dom.application.common.service.application.output.ApplicationForRemandOutput;
import nts.uk.ctx.at.request.dom.application.common.service.application.output.DetailApprovalFrameOutput;
import nts.uk.ctx.at.request.dom.application.common.service.application.output.DetailApproverOutput;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class ApplicationForRemandServiceImpl implements IApplicationForRemandService{
	
	@Inject
	private ApplicationRepository_New applicationRepository;
	
	@Inject
	private ApprovalRootStateAdapter approvalRootStateAdapter;
	
	@Inject
	private EmployeeRequestAdapter employeeRequestAdapter;
	
	@Inject
	private AtJobTitleAdapter jobTitleAdapter;
	
	@Inject
	private ApprovalRootAdapter approvalRootAdapter;
	/**
	 * 画面表示
	 * KDL034
	 */
	@Override
	public ApplicationForRemandOutput getApplicationForRemand(String appID) {
		String companyID = AppContexts.user().companyId();
		//申請IDをキーにドメインモデル「申請」「承認フェーズ」「承認枠」を取得する
		Optional<Application_New> application_New = this.applicationRepository.findByID(companyID, appID);
		if (application_New.isPresent()){
			Application_New app = application_New.get();
			//承認者（社員ID）から承認枠を特定して「順序」を取得する
			Integer phaseOrder = approvalRootAdapter.getCurrentApprovePhase(app.getAppID(), 0);
			
			ApprovalRootContentImport_New approvalRootContentImport = approvalRootStateAdapter
					.getApprovalRootContent(companyID, null, null, null, appID, false);
			Optional<AffJobTitleHistoryImport> jobTitle = jobTitleAdapter.getJobTitlebBySIDAndDate(app.getEmployeeID(), app.getAppDate());
			String applicantPosition = jobTitle.isPresent() ? jobTitle.get().getJobTitleName() : "";
			List<DetailApprovalFrameOutput> listApprovalFrame = new ArrayList<DetailApprovalFrameOutput>();
			approvalRootContentImport.getApprovalRootState().getListApprovalPhaseState().forEach(x -> {
				x.getListApprovalFrame().forEach(y -> {
					List<DetailApproverOutput> listApprover = new ArrayList<DetailApproverOutput>();
					y.getListApprover().forEach(z -> {
						Optional<AffJobTitleHistoryImport> approverJobTitle = jobTitleAdapter.getJobTitlebBySIDAndDate(z.getApproverID(), app.getAppDate());
						String approverPosition = approverJobTitle.isPresent() ? approverJobTitle.get().getJobTitleName() : "";
						listApprover.add(new DetailApproverOutput(z.getApproverID(), z.getApproverName(), z.getRepresenterID(),
								z.getRepresenterName(), approverPosition));
					});
					listApprovalFrame
							.add(new DetailApprovalFrameOutput(y.getPhaseOrder(), y.getApprovalReason(), listApprover));
				});
			});
			List<DetailApprovalFrameOutput> listApprFrameFil = listApprovalFrame.stream()
					.filter(c->c.phaseOrder < phaseOrder).collect(Collectors.toList());
			return new ApplicationForRemandOutput(appID, application_New.get().getVersion(),
					approvalRootContentImport.getErrorFlag().value, applicantPosition,
					application_New.isPresent() ? employeeRequestAdapter
							.getEmployeeInfor(application_New.map(Application_New::getEmployeeID).orElse("")) : null,
							listApprFrameFil);
		}
		return null;
	}
	
}
