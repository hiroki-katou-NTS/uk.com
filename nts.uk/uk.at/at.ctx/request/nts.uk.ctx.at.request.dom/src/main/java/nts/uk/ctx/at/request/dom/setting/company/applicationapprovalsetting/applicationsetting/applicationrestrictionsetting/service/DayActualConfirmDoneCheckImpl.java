package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationrestrictionsetting.service;

import java.util.Collections;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.workrecord.identificationstatus.IdentificationAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.ApprovalRootStateAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.ApprovalStatusForEmployeeImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.ApproveRootStatusForEmpImPort;

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
	public boolean check(boolean canAppAchievementConfirm, String companyID, String employeeID, GeneralDate appDate) {
		// INPUT．日別実績が確認済なら申請できないをチェックする
		if (!canAppAchievementConfirm) {
			// 承認ルート状況．上司確認をチェックする
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
			if(isConfirm){
				return true;
			}
			// 対象期間内で本人確認をした日をチェックする
			List<GeneralDate> identificationDateLst = identificationAdapter.getProcessingYMD(companyID, employeeID, new DatePeriod(appDate, appDate));
			if(!CollectionUtil.isEmpty(identificationDateLst)){
				return true;
			}
		}
		return false;
	}
}
