package nts.uk.ctx.at.record.dom.approvalmanagement.repository;

import java.util.List;

import nts.arc.time.GeneralDate;

public interface ApprovalStatusOfDailyPerforRepository {
	
	void delete(String employeeId, GeneralDate ymd);
	
	void deleteByListEmployeeId(List<String> employeeIds, List<GeneralDate> ymds);

}
