package nts.uk.ctx.at.shared.dom.adapter.employment.employwork.leaveinfo;

import java.util.List;

import nts.arc.time.calendar.period.DatePeriod;

/**
 * 社員の休業履歴Adapter
 * @author HieuLt
 *
 */

public interface EmpLeaveWorkHistoryAdapter {
	//[1] 期間を指定して休業期間を取得する 									
	List<EmpLeaveWorkPeriodImport> getHolidayPeriod(List<String> lstEmpId, DatePeriod datePeriod);
	
}
