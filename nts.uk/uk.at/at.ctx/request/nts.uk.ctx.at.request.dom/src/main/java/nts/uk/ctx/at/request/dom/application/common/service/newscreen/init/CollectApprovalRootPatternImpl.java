package nts.uk.ctx.at.request.dom.application.common.service.newscreen.init;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.EmploymentRootAtr;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.ApprovalRootStateAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalRootContentImport_New;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.init.output.ApprovalRootPattern;
import nts.uk.ctx.at.request.dom.setting.company.request.RequestSetting;
import nts.uk.ctx.at.request.dom.setting.company.request.RequestSettingRepository;
import nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.RecordDate;
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
	
	@Inject
	private RequestSettingRepository requestSettingRepository;
	
	@Override
	public ApprovalRootPattern getApprovalRootPatternService(String companyID, String employeeID, EmploymentRootAtr rootAtr,
			ApplicationType appType, GeneralDate appDate, String appID, Boolean isCreate) {
		GeneralDate baseDate = null;
		ApprovalRootContentImport_New approvalRootContentImport = null;
		// ドメインモデル「申請設定」．承認ルートの基準日をチェックする
		// 承認ルートの基準日
		RecordDate baseDateFlg = RecordDate.SYSTEM_DATE;
		RequestSetting requestSetting = requestSettingRepository.findByCompany(companyID).get();
		baseDateFlg = requestSetting.getApplicationSetting().getRecordDate();
		if(baseDateFlg.equals(RecordDate.SYSTEM_DATE)){
			baseDate = GeneralDate.today();
			approvalRootContentImport = approvalRootStateAdapter.getApprovalRootContent(companyID, employeeID, appType.value, baseDate, appID, isCreate);
			return new ApprovalRootPattern(baseDate, approvalRootContentImport);
		}
		if(appDate == null){
			baseDate = GeneralDate.today();
			return new ApprovalRootPattern(baseDate, approvalRootContentImport);
		}
		baseDate = appDate;
		approvalRootContentImport = approvalRootStateAdapter.getApprovalRootContent(companyID, employeeID, appType.value, baseDate, appID, isCreate);
		return new ApprovalRootPattern(baseDate, approvalRootContentImport);
	}

}
