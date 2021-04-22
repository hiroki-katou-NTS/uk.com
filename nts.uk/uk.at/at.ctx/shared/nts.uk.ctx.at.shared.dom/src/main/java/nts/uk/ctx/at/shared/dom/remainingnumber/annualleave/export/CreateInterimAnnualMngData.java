package nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.export;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.DailyInterimRemainMngData;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber.AnnualLeaveUsedDayNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.interim.TmpAnnualHolidayMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.interim.TmpAnnualLeaveMngWork;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemain;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.CreateAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainType;
import nts.uk.ctx.at.shared.dom.remainingnumber.work.DigestionHourlyTimeType;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.AttendanceTimeOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.annualleave.AnnualLeaveUsedNumber;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;

/**
 * 暫定年休管理データを作成する
 * @author shuichi_ishida
 */
public class CreateInterimAnnualMngData {

	/**
	 * 月別実績の勤怠時間からフレックス補填の暫定年休管理データを作成する
	 * @param timeMonth 月別実績の勤怠時間
	 * @return 暫定年休管理データ
	 */
	public static Optional<TmpAnnualHolidayMng> ofCompensFlex(AttendanceTimeOfMonthly timeMonth) {
		
		// 「暫定年休管理データ」を作成
		if (timeMonth == null) return Optional.empty();
		val deductDays = timeMonth.getMonthlyCalculation().getFlexTime().getFlexShortDeductTime().getAnnualLeaveDeductDays().v();
		
		TmpAnnualHolidayMng result = new TmpAnnualHolidayMng(IdentifierUtil.randomUniqueId(), timeMonth.getEmployeeId(),
				timeMonth.getDatePeriod().end(), CreateAtr.FLEXCOMPEN,
				new DigestionHourlyTimeType(), new WorkTypeCode("000"), 
				AnnualLeaveUsedNumber.of(Optional.of(new AnnualLeaveUsedDayNumber(deductDays)), Optional.empty()));
		
		// 「暫定年休管理データ」を返す
		return Optional.of(result);
	}

	/**
	 * 月別実績の勤怠時間からフレックス補填の暫定年休管理データを作成する
	 * @param timeMonth 月別実績の勤怠時間
	 * @param targetYmd 作成対象年月日
	 * @return 暫定残数データ
	 */
	/** 月別実績の勤怠時間からフレックス補填の暫定年休管理データを作成する */
	public static Optional<DailyInterimRemainMngData> ofCompensFlex(AttendanceTimeOfMonthly timeMonth, GeneralDate targetYmd) {
		
		val tmpAnnualHolidayMngOpt = ofCompensFlex(timeMonth);
		if (!tmpAnnualHolidayMngOpt.isPresent()) return Optional.empty();
		
		return Optional.of(new DailyInterimRemainMngData(
				targetYmd,
				Optional.empty(),
				new ArrayList<>(),
				Optional.empty(),
				Optional.empty(),
				Optional.of(tmpAnnualHolidayMngOpt.get()),
				Optional.empty(),
				Optional.empty(),
				new ArrayList<>()));
	}
	
	/**
	 * 月別実績の勤怠時間からフレックス補填の暫定年休管理データを作成する
	 * @param timeMonth 月別実績の勤怠時間
	 * @param targetYmd 作成対象年月日
	 * @return 暫定年休管理データWORK
	 */
	public static Optional<TmpAnnualLeaveMngWork> ofCompensFlexToWork(AttendanceTimeOfMonthly timeMonth, GeneralDate targetYmd) {
		
		val dailyInterimRemainMngDataOpt = ofCompensFlex(timeMonth, targetYmd);
		if (!dailyInterimRemainMngDataOpt.isPresent()) return Optional.empty();
		val mngData = dailyInterimRemainMngDataOpt.get();
		return Optional.of(TmpAnnualLeaveMngWork.of(
				mngData.getAnnualHolidayData().get()));
	}
}
