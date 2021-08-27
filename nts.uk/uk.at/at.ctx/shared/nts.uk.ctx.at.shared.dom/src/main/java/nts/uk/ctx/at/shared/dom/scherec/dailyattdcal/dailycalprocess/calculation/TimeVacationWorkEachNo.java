package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.HolidayAddtionSet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.TimevacationUseTimeOfDaily;
import nts.uk.ctx.at.shared.dom.worktime.predset.WorkNo;

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
	 * @param holidayAddSet 休暇加算時間設定
	 * @return 勤務NO毎の時間休暇WORK
	 */
	public TimeVacationWorkEachNo getValueForAddWorkTime(HolidayAddtionSet holidayAddSet){
		return TimeVacationWorkEachNo.of(
				this.workNo,
				this.late.getValueForAddWorkTime(holidayAddSet),
				this.leaveEarly.getValueForAddWorkTime(holidayAddSet));
	}
}
