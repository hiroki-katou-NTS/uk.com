package nts.uk.ctx.at.function.dom.alarm.alarmlist.attendanceholiday.whethertocheck.narrowlastdays;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.annualholiday.AnnualHolidayAlarmCondition;
import nts.uk.ctx.at.shared.dom.remainingnumber.yearholiday.employeeinfo.remaininggrantdata.checklastgrantnumber.CheckLastgrantNumber;
/**
 * 前回年休付与日数の条件で絞り込む
 * @author tutk
 *
 */
@Stateless
public class NarrowLastDays {

	@Inject
	private CheckLastgrantNumber checkLastgrantNumber;
	/**
	 * 前回年休付与日数の条件で絞り込む
	 * @param employeeId
	 * @param annualHolidayAlarmCondition
	 * @param baseDate
	 * @return
	 */
	public boolean checkNarrowLastDays(String employeeId,AnnualHolidayAlarmCondition annualHolidayAlarmCondition,GeneralDate baseDate) {
		//「前回年休付与日数の条件で絞り込む」をチェックする
		boolean check = annualHolidayAlarmCondition.getAlarmCheckSubConAgr().isNarrowLastDay();
		if(!check) {
			//比較結果←true
			return true;
		}
		//前回年休付与数をチェック
		boolean checkLastGantNumber = checkLastgrantNumber
				.checkLastgrantNumber(employeeId,Double.valueOf(annualHolidayAlarmCondition.getAlarmCheckSubConAgr().getNumberDayAward().get().v()),baseDate);
		
		return checkLastGantNumber;
	}
	
}
