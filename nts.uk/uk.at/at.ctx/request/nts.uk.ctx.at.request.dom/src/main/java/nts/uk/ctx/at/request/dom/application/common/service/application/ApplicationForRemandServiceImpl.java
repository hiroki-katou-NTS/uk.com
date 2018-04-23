package nts.uk.ctx.at.request.dom.application.common.service.application;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.dom.application.ApplicationRepository_New;
import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.EmployeeRequestAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.ApprovalRootStateAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalRootContentImport_New;
import nts.uk.ctx.at.request.dom.application.common.service.application.output.ApplicationForRemandOutput;
import nts.uk.ctx.at.request.dom.application.common.service.application.output.ApprovalFrameOutput;
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
	@Override
	public ApplicationForRemandOutput getApplicationForRemand(String appID) {
		String companyID = AppContexts.user().companyId();
		Optional<Application_New> application_New = this.applicationRepository.findByID(companyID, appID);
		ApprovalRootContentImport_New approvalRootContentImport = approvalRootStateAdapter
				.getApprovalRootContent(companyID, null, null, null, appID, false);
		String applicantPosition = "主任";
		List<ApprovalFrameOutput> listApprovalFrame = new ArrayList<ApprovalFrameOutput>();
		approvalRootContentImport.getApprovalRootState().getListApprovalPhaseState().forEach(x -> {
			x.getListApprovalFrame().forEach(y -> {
				List<DetailApproverOutput> listApprover = new ArrayList<DetailApproverOutput>();
				y.getListApprover().forEach(z -> {
					listApprover.add(new DetailApproverOutput(z.getApproverID(), z.getApproverName(), z.getRepresenterID(),
							z.getRepresenterName(), "課長"));
				});
				listApprovalFrame
						.add(new ApprovalFrameOutput(y.getPhaseOrder(), y.getApprovalReason(), listApprover));
			});
		});
		return new ApplicationForRemandOutput(appID, application_New.get().getVersion(),
				approvalRootContentImport.getErrorFlag().value, applicantPosition,
				application_New.isPresent() ? employeeRequestAdapter
						.getEmployeeInfor(application_New.map(Application_New::getEmployeeID).orElse("")) : null,
				listApprovalFrame);
	}
	
}
