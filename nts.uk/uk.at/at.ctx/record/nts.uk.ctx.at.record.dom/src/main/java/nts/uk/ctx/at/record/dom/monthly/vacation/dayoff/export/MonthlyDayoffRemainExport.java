package nts.uk.ctx.at.record.dom.monthly.vacation.dayoff.export;
/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.勤務実績.月別実績.月別実績の休暇情報.代休.月別残数データ.Export
 * @author do_dt
 *
 */

import java.util.List;
import java.util.Map;

import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.YearMonthPeriod;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.remainmerge.RemainMerge;

public interface MonthlyDayoffRemainExport {
	/**
	 * RequesList259 社員の月毎の確定済み代休を取得する
	 * @param employeeId 社員ID
	 * @param startMonth 年月期間
	 * @param endMonth
	 * @return
	 */
	public List<DayoffCurrentMonthOfEmployee> lstDayoffCurrentMonthOfEmployee(String employeeId, YearMonth startMonth, YearMonth endMonth);
	/**
	 * @author hoatt
	 * Doi ung response KDR001
	 * RequesList259 社員の月毎の確定済み代休を取得する - ver2
	 * @param employeeId 社員ID
	 * @param startMonth 年月期間
	 * @param endMonth
	 * @return
	 */
	public List<DayoffCurrentMonthOfEmployee> lstDayoffCurrentMonthOfEmpVer2(String employeeId, YearMonthPeriod period, Map<YearMonth, List<RemainMerge>> mapRemainMer);

}
