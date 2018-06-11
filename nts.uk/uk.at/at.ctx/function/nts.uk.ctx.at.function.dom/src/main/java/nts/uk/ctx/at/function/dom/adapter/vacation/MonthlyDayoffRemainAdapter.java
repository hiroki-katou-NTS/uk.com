package nts.uk.ctx.at.function.dom.adapter.vacation;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;

public interface MonthlyDayoffRemainAdapter {
	
	/**
	 * RequesList259 社員の月毎の確定済み代休を取得する
	 * @param employeeId 社員ID
	 * @param startMonth 年月期間
	 * @param endMonth
	 * @return
	 */
	public List<StatusHolidayImported> lstDayoffCurrentMonthOfEmployee(String employeeId, YearMonth startMonth, YearMonth endMonth);
	
	/**
	 * RequestList269
	 * 月度別代休残数集計を取得
	 * @param employeeId
	 * @param baseDate
	 * @param startMonth
	 * @param endMonth
	 * @return
	 */
	public List<CurrentHolidayImported> getInterimRemainAggregate(String employeeId, GeneralDate baseDate, YearMonth startMonth, YearMonth endMonth);

}
