package nts.uk.ctx.at.shared.dom.remainmana.breakdayoffmana.export.query;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared.残数管理.休出代休管理.アルゴリズム.Query
 * @author du_dt
 *
 */
public interface BreakDayOffManagementQuery {
	/**
	 * RequestList269
	 * 月度別代休残数集計を取得
	 * @param employeeId
	 * @param baseDate
	 * @param startMonth
	 * @param endMonth
	 * @return
	 */
	public List<InterimRemainAggregateOutputData> getInterimRemainAggregate(String employeeId, GeneralDate baseDate, YearMonth startMonth, YearMonth endMonth);
	/**
	 * 期間内の代休発生数合計を取得
	 * @param employeeId
	 * @param dateData
	 * @return
	 */
	public Double getTotalOccurrenceDays(String employeeId, DatePeriod dateData);
	/**
	 * 期間内の代休使用数合計を取得
	 * @param employeeId
	 * @param dateData
	 * @return
	 */
	public Double getTotalUseDays(String employeeId, DatePeriod dateData);
	/**
	 * 当月の代休残数を集計する
	 * @param employeeId
	 * @return
	 */
	public InterimRemainAggregateOutputData aggregatedDayoffCurrentMonth(String employeeId);
}
