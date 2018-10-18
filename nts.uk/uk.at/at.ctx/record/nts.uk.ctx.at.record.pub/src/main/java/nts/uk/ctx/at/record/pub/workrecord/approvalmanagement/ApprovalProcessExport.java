package nts.uk.ctx.at.record.pub.workrecord.approvalmanagement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.ApprovalProcess;

@AllArgsConstructor
@Getter
public class ApprovalProcessExport {

	/**
	 * 会社ID
	 */
	private String cid;

	/**
	 * 職位ID
	 */
	private String jobTitleId;

	/**
	 * 日の承認者確認を利用する
	 */
	private int useDailyBossChk;

	/**
	 * 月の承認者確認を利用する
	 */
	private int useMonthBossChk;

	/**
	 * エラーがある場合の上司確認
	 */
	private Integer supervisorConfirmError;
	
	public static ApprovalProcessExport fromDomain(ApprovalProcess domain){
        return new ApprovalProcessExport(domain.getCid(), domain.getJobTitleId(), 
						        		domain.getUseDailyBossChk(), domain.getUseMonthBossChk(), 
						        		domain.getSupervisorConfirmError() == null ? null : domain.getSupervisorConfirmError().value);
    }
}
