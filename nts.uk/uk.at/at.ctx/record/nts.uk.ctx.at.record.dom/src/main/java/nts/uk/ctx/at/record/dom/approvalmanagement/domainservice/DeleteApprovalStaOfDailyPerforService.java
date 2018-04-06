package nts.uk.ctx.at.record.dom.approvalmanagement.domainservice;

import nts.arc.time.GeneralDate;

public interface DeleteApprovalStaOfDailyPerforService {
	
	/**
	 * 日別確認の承認インスタンスを削除
	 * @param employeeID
	 * @param processingDate
	 */
	public void deleteApprovalStaOfDailyPerforService(String employeeID, GeneralDate processingDate);

}
