package nts.uk.ctx.at.request.dom.application.common.service.newscreen.init;
/*import nts.uk.ctx.at.request.dom.setting.request.application.common.BaseDateFlg;*/
import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.EmploymentRootAtr;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.ApprovalRootStateAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalRootContentImport_New;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.init.output.ApprovalRootPattern;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.service.RootBaseDateGet;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@Stateless
public class CollectApprovalRootPatternImpl implements CollectApprovalRootPatternService {
	
	@Inject
	private RootBaseDateGet rootBaseDateGet;
	
	@Inject
	private ApprovalRootStateAdapter approvalRootStateAdapter;
	
	@Override
	public ApprovalRootPattern getApprovalRootPatternService(String companyID, String employeeID, EmploymentRootAtr rootAtr,
			ApplicationType appType, GeneralDate appDate, String appID, Boolean isCreate) {
		return rootBaseDateGet.getBaseDateFromRoot(companyID, employeeID, rootAtr,
			appType, appDate, appID, isCreate);
	}

	@Override
	public ApprovalRootContentImport_New getApprovalRootPatternNew(String companyID, String employeeID,
			EmploymentRootAtr rootAtr, ApplicationType appType, GeneralDate appDate) {
		// 1.社員の対象申請の承認ルートを取得する
		ApprovalRootContentImport_New approvalRootContentImport = approvalRootStateAdapter.getApprovalRootContent(companyID, employeeID, appType.value, appDate, "", true);
		return approvalRootContentImport;
	}

}
