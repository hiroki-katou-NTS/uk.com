package nts.uk.ctx.at.shared.dom.vacation.setting.addsettingofworktime;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.attendance.UseSetting;
import nts.uk.ctx.at.shared.dom.workrule.addsettingofworktime.NotUseAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.GraceTimeSetting;

/**
 * 休暇の就業時間計算方法詳細
 * @author ken_takasu
 *
 */
public class WorkTimeCalcMethodDetailOfHoliday {
	@Getter
	private NotUseAtr deductLateLeaveEarly;//遅刻・早退を控除する
	@Getter
	private IncludeHolidaysWorkCalcDetailSet IncludeHolidaysWorkCalcDetailSet;
	
	public WorkTimeCalcMethodDetailOfHoliday(NotUseAtr deductLateLeaveEarly,
			IncludeHolidaysWorkCalcDetailSet includeHolidaysWorkCalcDetailSet) {
		super();
		this.deductLateLeaveEarly = deductLateLeaveEarly;
		IncludeHolidaysWorkCalcDetailSet = includeHolidaysWorkCalcDetailSet;
	}
	
	/**
	 * 就業時間内時間帯から控除するか判断
	 * @param deductTime
	 * @param graceTimeSetting
	 * @return
	 */
	public boolean deductsFromWithinWorkTimeSheet(int deductTime, GraceTimeSetting graceTimeSetting) {
		if(this.deductLateLeaveEarly.isUse()) {//早退設定を控除項目にするかをチェックする
			if(deductTime > 0 || !graceTimeSetting.isIncludeWorkingHour()) {
				return true;
			}
		}
		return false;	
	}
	
	/**
	 * 遅刻・早退を控除するか判断する
	 * @return
	 */
	public boolean isDeductLateLeaveEarly() {
		switch(this.deductLateLeaveEarly) {
			case To:
				return true;
			case Donot:
				return false;
			default:
				throw new RuntimeException("unknown DeductionAtr" + deductLateLeaveEarly);
		}	
	}




	
}
