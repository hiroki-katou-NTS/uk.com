package nts.uk.ctx.at.shared.dom.adapter.employment.employwork.leaveinfo;

import java.util.List;

import nts.arc.time.calendar.period.DatePeriod;

/**
 * 社員の休職履歴Adapter
 * @author HieuLt
 *
 */
public interface EmpLeaveHistoryAdapter {

	//[1] 期間を指定して休職期間を取得する / [1]  Chỉ định period để  Get period "休職""nghỉ làm" 																												
	List<EmployeeLeaveJobPeriodImport> getLeaveBySpecifyingPeriod(List<String> listEmpId , DatePeriod datePeriod ); 
}
