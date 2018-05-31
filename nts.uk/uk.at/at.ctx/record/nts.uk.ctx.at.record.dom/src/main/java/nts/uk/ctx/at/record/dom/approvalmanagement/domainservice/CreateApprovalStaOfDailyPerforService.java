package nts.uk.ctx.at.record.dom.approvalmanagement.domainservice;

import nts.arc.time.GeneralDate;

public interface CreateApprovalStaOfDailyPerforService {
	
	/**
	 * 日別確認の承認インスタンスを作成
	 * @param approvalStatusOfDailyPerfor
	 */
	public void createApprovalStaOfDailyPerforService(String companyId, String employeeID, GeneralDate processingDate);

}
