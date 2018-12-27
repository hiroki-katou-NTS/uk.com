package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export;

import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthly;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.DailyInterimRemainMngData;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.interim.TmpAnnualHolidayMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.interim.TmpAnnualLeaveMngWork;

/**
 * 暫定年休管理データを作成する
 * @author shuichi_ishida
 */
public interface CreateInterimAnnualMngData {

	/**
	 * 月別実績の勤怠時間からフレックス補填の暫定年休管理データを作成する
	 * @param timeMonth 月別実績の勤怠時間
	 * @return 暫定年休管理データ
	 */
	Optional<TmpAnnualHolidayMng> ofCompensFlex(AttendanceTimeOfMonthly timeMonth);

	/**
	 * 月別実績の勤怠時間からフレックス補填の暫定年休管理データを作成する
	 * @param timeMonth 月別実績の勤怠時間
	 * @param targetYmd 作成対象年月日
	 * @return 暫定残数データ
	 */
	Optional<DailyInterimRemainMngData> ofCompensFlex(AttendanceTimeOfMonthly timeMonth, GeneralDate targetYmd);
	
	/**
	 * 月別実績の勤怠時間からフレックス補填の暫定年休管理データを作成する
	 * @param timeMonth 月別実績の勤怠時間
	 * @param targetYmd 作成対象年月日
	 * @return 暫定年休管理データWORK
	 */
	Optional<TmpAnnualLeaveMngWork> ofCompensFlexToWork(AttendanceTimeOfMonthly timeMonth, GeneralDate targetYmd);
}
