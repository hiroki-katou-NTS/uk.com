package nts.uk.ctx.at.record.dom.approvalmanagement.repository;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.approvalmanagement.ApprovalStatusOfDailyPerfor;

public interface ApprovalStatusOfDailyPerforRepository {
	
	void insert(ApprovalStatusOfDailyPerfor approvalStatusOfDailyPerfor);
	
	void delete(String employeeId, GeneralDate ymd);
	
	void deleteByListEmployeeId(List<String> employeeIds, List<GeneralDate> ymds);

}
