package nts.uk.ctx.at.function.dom.adapter.holidaysremaining;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
public interface AbsenceReruitmentManaAdapter {
	
	
	/**
	 * RequestList270 月度別振休残数集計を取得
	 * @param employeeId
	 * @param baseDate
	 * @param startMonth
	 * @param endMonth
	 * @return
	 */
	List<CurrentHolidayRemainImported> getAbsRecRemainAggregate(String employeeId, GeneralDate baseDate, YearMonth startMonth, YearMonth endMonth);
	
	
	/**
	 * 	RequestList260 社員の月毎の確定済み振休を取得する
	 * @param employeeId
	 * @param startMonth
	 * @param endMonth
	 * @return
	 */
	List<StatusOfHolidayImported> getDataCurrentMonthOfEmployee(String employeeId, YearMonth startMonth, YearMonth endMonth);

}
