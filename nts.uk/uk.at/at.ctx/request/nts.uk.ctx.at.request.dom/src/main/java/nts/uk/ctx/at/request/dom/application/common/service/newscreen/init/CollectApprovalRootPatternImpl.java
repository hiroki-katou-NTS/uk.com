package nts.uk.ctx.at.request.dom.application.common.service.newscreen.init;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.EmploymentRootAtr;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.ApprovalRootStateAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalRootContentImport_New;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.init.output.ApprovalRootPattern;
import nts.uk.ctx.at.request.dom.setting.request.application.common.BaseDateFlg;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@Stateless
public class CollectApprovalRootPatternImpl implements CollectApprovalRootPatternService {

	@Inject
	private ApprovalRootStateAdapter approvalRootStateAdapter;
	
	@Override
	public ApprovalRootPattern getApprovalRootPatternService(String companyID, String employeeID, EmploymentRootAtr rootAtr,
			ApplicationType appType, GeneralDate appDate, String appID, Boolean isCreate) {
		GeneralDate baseDate = null;
		ApprovalRootContentImport_New approvalRootContentImport = null;
		// ドメインモデル「申請設定」．承認ルートの基準日をチェックする
		// 承認ルートの基準日
		BaseDateFlg baseDateFlg = BaseDateFlg.SYSTEM_DATE;
		
		if(baseDateFlg.equals(BaseDateFlg.SYSTEM_DATE)){
			approvalRootContentImport = approvalRootStateAdapter.getApprovalRootContent(companyID, employeeID, appType.value, appDate, appID, isCreate);
			baseDate = GeneralDate.today();
			return new ApprovalRootPattern(baseDate, approvalRootContentImport);
		}
		if(appDate == null){
			baseDate = GeneralDate.today();
			return new ApprovalRootPattern(baseDate, approvalRootContentImport);
		}
		approvalRootStateAdapter.getApprovalRootContent(companyID, employeeID, appType.value, appDate, appID, isCreate);
		baseDate = appDate;
		return new ApprovalRootPattern(baseDate, approvalRootContentImport);
	}

}
