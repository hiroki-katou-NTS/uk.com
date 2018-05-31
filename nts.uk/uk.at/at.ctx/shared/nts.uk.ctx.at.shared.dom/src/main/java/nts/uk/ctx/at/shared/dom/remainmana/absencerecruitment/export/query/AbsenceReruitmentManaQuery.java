package nts.uk.ctx.at.shared.dom.remainmana.absencerecruitment.export.query;
/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared.残数管理.振出振休管理.アルゴリズム.Query.月度別振休残数集計を取得.アルゴリズム.月度別振休残数集計を取得
 * @author do_dt
 *
 */

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.remainmana.breakdayoffmana.export.query.InterimRemainAggregateOutputData;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

public interface AbsenceReruitmentManaQuery {
	/**
	 * RequestList 270 月度別振休残数集計を取得
	 * @param employeeId
	 * @param baseDate
	 * @param startMonth
	 * @param endMonth
	 * @return
	 */
	List<InterimRemainAggregateOutputData> getAbsRecRemainAggregate(String employeeId, GeneralDate baseDate, YearMonth startMonth, YearMonth endMonth);
	/**
	 * 期間内の振休発生数合計を取得
	 * @param employeeId
	 * @param startMonth
	 * @param endMonth
	 * @return
	 */
	Double getTotalOccurrentDay(String employeeId, DatePeriod dateData);
	/**
	 * 期間内の振休使用数合計を取得
	 * @param employeeId
	 * @param dateData
	 * @return
	 */
	Double getUsedDays(String employeeId, DatePeriod dateData);
}
