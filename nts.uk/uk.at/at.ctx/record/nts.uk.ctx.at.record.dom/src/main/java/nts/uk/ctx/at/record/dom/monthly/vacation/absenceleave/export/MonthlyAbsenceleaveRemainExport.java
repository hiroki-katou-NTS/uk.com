package nts.uk.ctx.at.record.dom.monthly.vacation.absenceleave.export;

import java.util.List;

import nts.arc.time.YearMonth;

public interface MonthlyAbsenceleaveRemainExport {
	/**
	 * 	RequestList260 社員の月毎の確定済み振休を取得する
	 * @param employeeId
	 * @param startMonth
	 * @param endMonth
	 * @return
	 */
	List<AbsenceleaveCurrentMonthOfEmployee> getDataCurrentMonthOfEmployee(String employeeId, YearMonth startMonth, YearMonth endMonth);
}
