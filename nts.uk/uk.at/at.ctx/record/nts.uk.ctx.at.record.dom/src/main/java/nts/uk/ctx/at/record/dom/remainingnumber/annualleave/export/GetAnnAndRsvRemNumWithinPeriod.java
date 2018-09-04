package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.MonAggrCompanySettings;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.MonAggrEmployeeSettings;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.MonthlyCalculatingDailys;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.param.AggrResultOfAnnAndRsvLeave;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.param.AggrResultOfAnnualLeave;
import nts.uk.ctx.at.record.dom.remainingnumber.reserveleave.export.param.AggrResultOfReserveLeave;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.interim.TmpAnnualLeaveMngWork;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.interim.TmpReserveLeaveMngWork;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 期間中の年休積休残数を取得
 * @author shuichi_ishida
 */
public interface GetAnnAndRsvRemNumWithinPeriod {

	/**
	 * 期間中の年休積休残数を取得
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param aggrPeriod 集計期間
	 * @param mode モード
	 * @param criteriaDate 基準日
	 * @param isGetNextMonthData 翌月管理データ取得フラグ
	 * @param isCalcAttendanceRate 出勤率計算フラグ
	 * @param isOverWrite 上書きフラグ
	 * @param tempAnnDataforOverWriteList 上書き用の暫定年休管理データ
	 * @param tempRsvDataforOverWriteList 上書き用の暫定積休管理データ
	 * @param isOutputForShortage 不足分付与残数データ出力区分
	 * @param noCheckStartDate 集計開始日を締め開始日とする　（締め開始日を確認しない）
	 * @param prevAnnualLeave 前回の年休の集計結果
	 * @param prevReserveLeave 前回の積立年休の集計結果
	 * @return 年休積立年休の集計結果
	 */
	AggrResultOfAnnAndRsvLeave algorithm(
			String companyId, String employeeId, DatePeriod aggrPeriod, TempAnnualLeaveMngMode mode,
			GeneralDate criteriaDate, boolean isGetNextMonthData, boolean isCalcAttendanceRate,
			Optional<Boolean> isOverWrite,
			Optional<List<TmpAnnualLeaveMngWork>> tempAnnDataforOverWriteList,
			Optional<List<TmpReserveLeaveMngWork>> tempRsvDataforOverWriteList,
			Optional<Boolean> isOutputForShortage,
			Optional<Boolean> noCheckStartDate,
			Optional<AggrResultOfAnnualLeave> prevAnnualLeave,
			Optional<AggrResultOfReserveLeave> prevReserveLeave);

	/**
	 * 期間中の年休積休残数を取得　（月別集計用）
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param aggrPeriod 集計期間
	 * @param mode モード
	 * @param criteriaDate 基準日
	 * @param isGetNextMonthData 翌月管理データ取得フラグ
	 * @param isCalcAttendanceRate 出勤率計算フラグ
	 * @param isOverWrite 上書きフラグ
	 * @param tempAnnDataforOverWriteList 上書き用の暫定年休管理データ
	 * @param tempRsvDataforOverWriteList 上書き用の暫定積休管理データ
	 * @param isOutputForShortage 不足分付与残数データ出力区分
	 * @param noCheckStartDate 集計開始日を締め開始日とする　（締め開始日を確認しない）
	 * @param prevAnnualLeave 前回の年休の集計結果
	 * @param prevReserveLeave 前回の積立年休の集計結果
	 * @param companySets 月別集計で必要な会社別設定
	 * @param employeeSets 月別集計で必要な社員別設定
	 * @param monthlyCalcDailys 月の計算中の日別実績データ
	 * @return 年休積立年休の集計結果
	 */
	AggrResultOfAnnAndRsvLeave algorithm(
			String companyId, String employeeId, DatePeriod aggrPeriod, TempAnnualLeaveMngMode mode,
			GeneralDate criteriaDate, boolean isGetNextMonthData, boolean isCalcAttendanceRate,
			Optional<Boolean> isOverWrite,
			Optional<List<TmpAnnualLeaveMngWork>> tempAnnDataforOverWriteList,
			Optional<List<TmpReserveLeaveMngWork>> tempRsvDataforOverWriteList,
			Optional<Boolean> isOutputForShortage,
			Optional<Boolean> noCheckStartDate,
			Optional<AggrResultOfAnnualLeave> prevAnnualLeave,
			Optional<AggrResultOfReserveLeave> prevReserveLeave,
			Optional<MonAggrCompanySettings> companySets,
			Optional<MonAggrEmployeeSettings> employeeSets,
			Optional<MonthlyCalculatingDailys> monthlyCalcDailys);
}
