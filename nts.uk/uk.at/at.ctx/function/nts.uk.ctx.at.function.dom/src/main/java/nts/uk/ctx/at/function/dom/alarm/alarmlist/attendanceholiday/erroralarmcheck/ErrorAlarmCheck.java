package nts.uk.ctx.at.function.dom.alarm.alarmlist.attendanceholiday.erroralarmcheck;

import javax.ejb.Stateless;

import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber.AnnualLeaveUsedDayNumber;
/**
 * エラーアラームチェック
 * @author tutk
 *
 */
@Stateless
public class ErrorAlarmCheck {
	/**
	 * 
	 * @param ligedUseDays 年休使用義務日数 
	 * @param ligedAnnLeaUseService 年休使用数
	 * @return
	 */
	public boolean checkErrorAlarmCheck(AnnualLeaveUsedDayNumber ligedUseDays,AnnualLeaveUsedDayNumber ligedAnnLeaUseService) {
		if(ligedAnnLeaUseService.v() <ligedUseDays.v())
			return true;
		return false;
	}
}
