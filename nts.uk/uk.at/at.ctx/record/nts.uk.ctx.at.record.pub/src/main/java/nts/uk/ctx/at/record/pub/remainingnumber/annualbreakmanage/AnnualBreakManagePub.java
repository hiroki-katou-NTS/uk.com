package nts.uk.ctx.at.record.pub.remainingnumber.annualbreakmanage;

import java.util.List;

import nts.arc.time.GeneralDate;


public interface AnnualBreakManagePub {
	List<AnnualBreakManageExport> getEmployeeId(List<String> employeeId, GeneralDate startDate, GeneralDate endDate);
}
