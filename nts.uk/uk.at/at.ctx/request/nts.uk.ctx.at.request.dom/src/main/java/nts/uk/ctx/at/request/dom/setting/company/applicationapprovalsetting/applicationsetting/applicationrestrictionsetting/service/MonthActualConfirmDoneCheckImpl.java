package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationrestrictionsetting.service;

import java.util.Collections;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.ApprovalRootStateAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.ApprovalStatusForEmployeeImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.ApproveRootStatusForEmpImPort;

/**
 * 月別確認済みかのチェックする
 *
 */
@Stateless
public class MonthActualConfirmDoneCheckImpl implements MonthActualConfirmDoneCheck {

	@Inject
	private ApprovalRootStateAdapter approvalRootStateAdapter;

	@Override
	public boolean check(boolean overtimeCheck, boolean canAppAchievementMonthConfirm, String companyID, String employeeID, GeneralDate appDate) {
		if (!canAppAchievementMonthConfirm) {
			if(overtimeCheck) {
				// 「Imported(申請承認)「実績確定状態」．月別実績が確認済をチェックする
				List<ApproveRootStatusForEmpImPort> approveRootStatus = Collections.emptyList();
				try {
					approveRootStatus = this.approvalRootStateAdapter.getApprovalByEmplAndDate(appDate, appDate, employeeID, companyID, 2);
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
				if (isConfirm) {
					return true;
				}
			} else {
				// 承認ルート状況．上司確認をチェックする
				List<ApproveRootStatusForEmpImPort> approveRootStatus = Collections.emptyList();
				try {
					approveRootStatus = this.approvalRootStateAdapter.getApprovalByEmplAndDate(appDate, appDate, employeeID, companyID, 2);
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
				if(isConfirm){
					return true;
				}
			}
		}
		return false;
	}
}
