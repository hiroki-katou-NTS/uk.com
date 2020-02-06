package nts.uk.ctx.at.record.dom.remainingnumber.annualleave;

import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.MonAggrCompanySettings;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.MonthlyCalculatingDailys;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.RepositoriesRequiredByMonthlyAggr;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.param.CalYearOffWorkAttendRate;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 出勤率計算用日数を取得する
 * @author shuichu_ishida
 */
public interface GetDaysForCalcAttdRate {

	/**
	 * 日別実績から出勤率計算用日数を取得
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param period 期間
	 * @return 年休出勤率計算結果
	 */
	CalYearOffWorkAttendRate algorithm(String companyId, String employeeId, DatePeriod period);

	/**
	 * 日別実績から出勤率計算用日数を取得　（月別集計用）
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param period 期間
	 * @param companySets 月別集計で必要な会社別設定
	 * @param monthlyCalcDailys 月の計算中の日別実績データ
	 * @param repositories 月別集計が必要とするリポジトリ
	 * @return 年休出勤率計算結果
	 */
	CalYearOffWorkAttendRate algorithm(String companyId, String employeeId, DatePeriod period,
			MonAggrCompanySettings companySets, MonthlyCalculatingDailys monthlyCalcDailys,
			RepositoriesRequiredByMonthlyAggr repositories);
}
