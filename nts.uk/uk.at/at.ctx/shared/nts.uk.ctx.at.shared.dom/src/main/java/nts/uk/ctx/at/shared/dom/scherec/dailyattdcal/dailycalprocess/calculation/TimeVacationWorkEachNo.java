package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation;

import java.util.Optional;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.PremiumAtr;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.HolidayAddtionSet;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.HolidayCalcMethodSet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.TimevacationUseTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.withinworkinghours.WithinWorkTimeFrame;
import nts.uk.ctx.at.shared.dom.worktime.IntegrationOfWorkTime;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSet;
import nts.uk.ctx.at.shared.dom.worktime.predset.WorkNo;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * 勤務NO毎の時間休暇WORK
 * @author shuichi_ishida
 */
@Getter
public class TimeVacationWorkEachNo implements Cloneable {

	/** 勤務NO */
	private WorkNo workNo;
	/** 遅刻 */
	private TimevacationUseTimeOfDaily late;
	/** 早退 */
	private TimevacationUseTimeOfDaily leaveEarly;
	
	private TimeVacationWorkEachNo(WorkNo workNo) {
		this.workNo = workNo;
		this.late = TimevacationUseTimeOfDaily.defaultValue();
		this.leaveEarly = TimevacationUseTimeOfDaily.defaultValue();
	}
	
	public static TimeVacationWorkEachNo of(
			WorkNo workNo,
			TimevacationUseTimeOfDaily late,
			TimevacationUseTimeOfDaily leaveEarly){
		
		TimeVacationWorkEachNo myclass = new TimeVacationWorkEachNo(workNo);
		myclass.late = late;
		myclass.leaveEarly = leaveEarly;
		return myclass;
	}
	
	@Override
	public TimeVacationWorkEachNo clone() {
		TimeVacationWorkEachNo clone = null;
		try {
			clone = (TimeVacationWorkEachNo)super.clone();
			clone.workNo = new WorkNo(this.workNo.v());
			clone.late = this.late.clone();
			clone.leaveEarly = this.leaveEarly.clone();
		}
		catch (Exception e){
			throw new RuntimeException("TimeVacationWorkEachNo clone error.");
		}
		return clone;
	}
	
	/**
	 * 就業時間に加算する時間のみ取得
	 * @param integrationOfWorkTime 統合就業時間帯
	 * @param premiumAtr 割増区分
	 * @param commonSetting 就業時間帯の共通設定
	 * @param holidayAddtionSet 休暇加算時間設定
	 * @param holidayCalcMethodSet 休暇の計算方法の設定
	 * @param offsetTime 相殺時間
	 * @param lateEarlyMinusAtr 強制的に遅刻早退控除する
	 * @return 勤務NO毎の時間休暇WORK
	 */
	public TimeVacationWorkEachNo getValueForAddWorkTime(
			Optional<IntegrationOfWorkTime> integrationOfWorkTime,
			PremiumAtr premiumAtr,
			Optional<WorkTimezoneCommonSet> commonSetting,
			HolidayAddtionSet holidayAddtionSet,
			HolidayCalcMethodSet holidayCalcMethodSet,
			TimeVacationWork offsetTime,
			NotUseAtr lateEarlyMinusAtr){
		
		TimevacationUseTimeOfDaily resultLate = this.late;			// 遅刻結果
		TimevacationUseTimeOfDaily resultEarly = this.leaveEarly;	// 早退結果
		// 遅刻早退を控除するかどうか判断
		if (!WithinWorkTimeFrame.isDeductLateLeaveEarly(
				integrationOfWorkTime, premiumAtr, holidayCalcMethodSet, commonSetting, lateEarlyMinusAtr)){
			// 控除しない時、該当する相殺時間を減算する
			Optional<TimevacationUseTimeOfDaily> offsetLate = offsetTime.getOffsetTimevacationForLate(this.workNo);
			if (offsetLate.isPresent()) resultLate = resultLate.minus(offsetLate.get());
			Optional<TimevacationUseTimeOfDaily> offsetEarly = offsetTime.getOffsetTimevacationForLeaveEarly(this.workNo);
			if (offsetEarly.isPresent()) resultEarly = resultEarly.minus(offsetEarly.get());
		}
		// 勤務NO毎の時間休暇WORKを返す
		return TimeVacationWorkEachNo.of(
				this.workNo,
				resultLate.getValueForAddWorkTime(holidayAddtionSet),
				resultEarly.getValueForAddWorkTime(holidayAddtionSet));
	}
}
