package nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.export;

import java.util.ArrayList;
import java.util.Optional;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.DailyInterimRemainMngData;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.interim.TempAnnualLeaveMngs;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.interim.TempAnnualLeaveUsedNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.interim.TmpDailyLeaveUsedDayNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.interim.TmpDailyLeaveUsedTime;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveUsedDayNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveUsedNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveUsedTime;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.CreateAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainType;
import nts.uk.ctx.at.shared.dom.remainingnumber.work.DigestionHourlyTimeType;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.AttendanceTimeOfMonthly;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.shr.com.context.AppContexts;

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
	public static Optional<TempAnnualLeaveMngs> ofCompensFlex(AttendanceTimeOfMonthly timeMonth) {

		/** 大塚モードかを確認する */
		if (!AppContexts.optionLicense().customize().ootsuka()) return Optional.empty();

		// 「暫定年休管理データ」を作成
		if (timeMonth == null) return Optional.empty();

		val flexTime = timeMonth.getMonthlyCalculation().getFlexTime();
		double deductDays = timeMonth.getMonthlyCalculation().getFlexTime().getFlexShortDeductTime().getAnnualLeaveDeductDays().v();
		double deductTimes = flexTime.getFlexShortDeductTime().getAbsenceDeductTime().v();
		String dataGuid = IdentifierUtil.randomUniqueId();
		TempAnnualLeaveMngs result = new TempAnnualLeaveMngs(
				dataGuid,
				timeMonth.getEmployeeId(),
				timeMonth.getYearMonth().lastGeneralDate(),
				CreateAtr.FLEXCOMPEN,
				RemainType.ANNUAL,
				new WorkTypeCode("000"),
				new TempAnnualLeaveUsedNumber(Optional.of(new TmpDailyLeaveUsedDayNumber(deductDays)),
				Optional.ofNullable(new TmpDailyLeaveUsedTime((int) deductTimes))),
				Optional.ofNullable(DigestionHourlyTimeType.of(false, Optional.empty()))
				);



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

		return Optional.of(DailyInterimRemainMngData.createEmpty(targetYmd));
	}

	/**
	 * 月別実績の勤怠時間からフレックス補填の暫定年休管理データを作成する
	 * @param timeMonth 月別実績の勤怠時間
	 * @param targetYmd 作成対象年月日
	 * @return 暫定年休管理データWORK
	 */
	public static Optional<TempAnnualLeaveMngs> ofCompensFlexToWork(AttendanceTimeOfMonthly timeMonth, GeneralDate targetYmd) {
		val dailyInterimRemainMngDataOpt = ofCompensFlex(timeMonth, targetYmd);
		if (!dailyInterimRemainMngDataOpt.isPresent()) return Optional.empty();
		val mngData = dailyInterimRemainMngDataOpt.get();
		return mngData.getAnnualHolidayData().stream().findFirst();
	}
}
