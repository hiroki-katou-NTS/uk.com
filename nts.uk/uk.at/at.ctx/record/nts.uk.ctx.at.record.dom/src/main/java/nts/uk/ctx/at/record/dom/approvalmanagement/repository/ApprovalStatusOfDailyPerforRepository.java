package nts.uk.ctx.at.record.dom.approvalmanagement.repository;

import nts.arc.time.GeneralDate;

public interface ApprovalStatusOfDailyPerforRepository {
	
	void delete(String employeeId, GeneralDate ymd);

}
