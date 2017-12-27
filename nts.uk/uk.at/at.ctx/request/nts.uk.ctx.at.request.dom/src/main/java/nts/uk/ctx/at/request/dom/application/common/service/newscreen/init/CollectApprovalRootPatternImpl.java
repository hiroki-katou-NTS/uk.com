package nts.uk.ctx.at.request.dom.application.common.service.newscreen.init;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.EmploymentRootAtr;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.CollectApprovalRootContentAdapter;
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
	private CollectApprovalRootContentAdapter collectApprovalRootContentAdapter;
	
	@Override
	public ApprovalRootPattern getApprovalRootPatternService(String companyID, String employeeID, EmploymentRootAtr rootAtr,
			ApplicationType appType, GeneralDate appDate, String appID) {
		GeneralDate baseDate = null;
		ApprovalRootContentImport_New approvalRootContentImport = null;
		// ドメインモデル「申請設定」．承認ルートの基準日をチェックする
		// 承認ルートの基準日
		BaseDateFlg baseDateFlg = BaseDateFlg.SYSTEM_DATE;
		
		if(baseDateFlg.equals(BaseDateFlg.SYSTEM_DATE)){
			approvalRootContentImport = collectApprovalRootContentAdapter.getApprovalRootContent(companyID, employeeID, appType.value, appDate, appID);
			baseDate = GeneralDate.today();
			return new ApprovalRootPattern(baseDate, approvalRootContentImport);
		}
		if(appDate == null){
			baseDate = GeneralDate.today();
			return new ApprovalRootPattern(baseDate, approvalRootContentImport);
		}
		collectApprovalRootContentAdapter.getApprovalRootContent(companyID, employeeID, appType.value, appDate, appID);
		baseDate = appDate;
		return new ApprovalRootPattern(baseDate, approvalRootContentImport);
	}

}
