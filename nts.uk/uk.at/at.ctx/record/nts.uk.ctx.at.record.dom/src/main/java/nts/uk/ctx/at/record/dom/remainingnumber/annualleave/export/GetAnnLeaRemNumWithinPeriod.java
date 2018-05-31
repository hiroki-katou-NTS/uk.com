package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.param.AggrResultOfAnnualLeave;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 期間中の年休残数を取得
 * @author shuichu_ishida
 */
public interface GetAnnLeaRemNumWithinPeriod {

	/**
	 * 期間中の年休残数を取得
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param aggrPeriod 集計期間
	 * @param mode モード
	 * @param criteriaDate 基準日
	 * @param isGetNextMonthData 翌月管理データ取得フラグ
	 * @param isCalcAttendanceRate 出勤率計算フラグ
	 * @param isOverWrite 上書きフラグ
	 * @param forOverWriteList 上書き用の暫定年休管理データ
	 * @param prevAnnualLeave 前回の年休の集計結果
	 * @return 年休の集計結果
	 */
	Optional<AggrResultOfAnnualLeave> algorithm(
			String companyId, String employeeId, DatePeriod aggrPeriod, TempAnnualLeaveMngMode mode,
			GeneralDate criteriaDate, boolean isGetNextMonthData, boolean isCalcAttendanceRate,
			Optional<Boolean> isOverWrite, Optional<List<TempAnnualLeaveManagement>> forOverWriteList,
			Optional<AggrResultOfAnnualLeave> prevAnnualLeave);
}
