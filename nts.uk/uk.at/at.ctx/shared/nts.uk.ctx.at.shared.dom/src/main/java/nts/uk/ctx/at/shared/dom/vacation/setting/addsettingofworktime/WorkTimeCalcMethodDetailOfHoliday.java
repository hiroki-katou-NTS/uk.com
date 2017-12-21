package nts.uk.ctx.at.shared.dom.vacation.setting.addsettingofworktime;

import nts.uk.ctx.at.shared.dom.attendance.UseSetting;
import nts.uk.ctx.at.shared.dom.worktime.commonsetting.lateleaveearly.GraceTimeSetting;

/**
 * 休暇の就業時間計算方法詳細
 * @author ken_takasu
 *
 */
public class WorkTimeCalcMethodDetailOfHoliday {
	private UseSetting deductLateLeaveEarly;
	
	/**
	 * 就業時間内時間帯から控除するか判断
	 * @param deductTime
	 * @param graceTimeSetting
	 * @return
	 */
	public boolean deductsFromWithinWorkTimeSheet(int deductTime, GraceTimeSetting graceTimeSetting) {
		if(this.deductLateLeaveEarly.isUse()) {//早退設定を控除項目にするかをチェックする
			if(deductTime > 0 || !graceTimeSetting.isIncludeInWorkingHours()) {
				return true;
			}
		}
		return false;	
	}
}
