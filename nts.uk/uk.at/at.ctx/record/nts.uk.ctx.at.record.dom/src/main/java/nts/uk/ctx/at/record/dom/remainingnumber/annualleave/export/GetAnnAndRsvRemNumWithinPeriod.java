package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.param.AggrResultOfAnnAndRsvLeave;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.param.AnnualLeaveInfo;
import nts.uk.ctx.at.record.dom.remainingnumber.reserveleave.TempReserveLeaveManagement;
import nts.uk.ctx.at.record.dom.remainingnumber.reserveleave.export.param.ReserveLeaveInfo;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 期間中の年休積休残数を取得
 * @author shuichu_ishida
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
	 * @param annualLeaveInfo 年休情報
	 * @param reserveLeaveInfo 積立年休情報
	 * @return 年休積立年休の集計結果
	 */
	AggrResultOfAnnAndRsvLeave algorithm(
			String companyId, String employeeId, DatePeriod aggrPeriod, TempAnnualLeaveMngMode mode,
			GeneralDate criteriaDate, boolean isGetNextMonthData, boolean isCalcAttendanceRate,
			Optional<Boolean> isOverWrite,
			Optional<List<TempAnnualLeaveManagement>> tempAnnDataforOverWriteList,
			Optional<List<TempReserveLeaveManagement>> tempRsvDataforOverWriteList,
			Optional<AnnualLeaveInfo> annualLeaveInfo,
			Optional<ReserveLeaveInfo> reserveLeaveInfo);
}
