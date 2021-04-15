package nts.uk.ctx.at.record.dom.monthly.vacation.absenceleave.export;

import java.util.List;
import java.util.Map;

import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.YearMonthPeriod;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.remainmerge.RemainMerge;

public interface MonthlyAbsenceleaveRemainExport {
	/**
	 * 	RequestList260 社員の月毎の確定済み振休を取得する
	 * @param employeeId
	 * @param startMonth
	 * @param endMonth
	 * @return
	 */
	List<AbsenceleaveCurrentMonthOfEmployee> getDataCurrentMonthOfEmployee(String employeeId, YearMonth startMonth, YearMonth endMonth);
	/**
	 * @author hoatt
	 * Doi ung response KDR001
	 * RequestList260 社員の月毎の確定済み振休を取得する - ver2
	 * @param employeeId
	 * @param startMonth
	 * @param endMonth
	 * @return
	 */
	List<AbsenceleaveCurrentMonthOfEmployee> getDataCurrMonOfEmpVer2(String employeeId, YearMonthPeriod period, Map<YearMonth, List<RemainMerge>> mapRemainMer);
}
