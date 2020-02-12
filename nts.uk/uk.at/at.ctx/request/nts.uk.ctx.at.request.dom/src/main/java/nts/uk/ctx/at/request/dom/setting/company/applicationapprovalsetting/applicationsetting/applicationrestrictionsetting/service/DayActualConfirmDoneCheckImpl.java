package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationrestrictionsetting.service;

import java.util.Collections;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.workrecord.identificationstatus.IdentificationAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.ApprovalRootStateAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.ApprovalStatusForEmployeeImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.ApproveRootStatusForEmpImPort;
import nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.applimitset.AppLimitSetting;
import nts.arc.time.calendar.period.DatePeriod;

/**
 * 日別確認済みかのチェック
 *
 */
@Stateless
public class DayActualConfirmDoneCheckImpl implements DayActualConfirmDoneCheck {

	@Inject
	private ApprovalRootStateAdapter approvalRootStateAdapter;
	
	@Inject
	private IdentificationAdapter identificationAdapter;

	@Override
	public boolean check(AppLimitSetting appLimitSetting, String companyID, String employeeID, GeneralDate appDate) {
		if (!appLimitSetting.getCanAppAchievementConfirm()) {
			List<ApproveRootStatusForEmpImPort> approveRootStatus = Collections.emptyList();
			try {
				approveRootStatus = this.approvalRootStateAdapter.getApprovalByEmplAndDate(appDate, appDate, employeeID, companyID, 1);
			} catch (Exception e) {
				approveRootStatus = Collections.emptyList();
			}
			if(CollectionUtil.isEmpty(approveRootStatus)){
				return false;
			}
			boolean isConfirm = false;
			for(ApproveRootStatusForEmpImPort approve : approveRootStatus){
				if(approve.getApprovalStatus().equals(ApprovalStatusForEmployeeImport.APPROVED)){
					isConfirm = true;
					break;
				}
			}
			//「Imported(申請承認)「実績確定状態」．日別実績が確認済をチェックする(check 「Imported(申請承認)「実績確定状態」．日別実績が確認済)
			if(isConfirm){
				return true;
			}
			List<GeneralDate> identificationDateLst = identificationAdapter.getProcessingYMD(companyID, employeeID, new DatePeriod(appDate, appDate));
			if(!CollectionUtil.isEmpty(identificationDateLst)){
				return true;
			}
		}
		return false;
	}
}
