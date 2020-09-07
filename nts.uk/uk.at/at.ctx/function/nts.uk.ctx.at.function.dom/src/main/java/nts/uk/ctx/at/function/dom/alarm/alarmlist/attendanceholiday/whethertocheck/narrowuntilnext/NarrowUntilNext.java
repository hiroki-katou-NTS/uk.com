package nts.uk.ctx.at.function.dom.alarm.alarmlist.attendanceholiday.whethertocheck.narrowuntilnext;


import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.dom.adapter.remainnumber.yearholiday.checkexistholidaygrant.CheckExistHolidayGrantAdapter;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.annualholiday.AnnualHolidayAlarmCondition;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.service.Period;

/**
 * 次回年休付与日までの期間の条件で絞り込む
 * @author tutk
 *
 */
@Stateless
public class NarrowUntilNext {
	
	@Inject
	private CheckExistHolidayGrantAdapter checkExistHolidayGrantAdapter;
	/**
	 * 次回年休付与日までの期間の条件で絞り込む
	 * @param employeeId
	 * @param annualHolidayAlarmCondition
	 * @return
	 */
	public boolean checkNarrowUntilNext(String employeeId,AnnualHolidayAlarmCondition annualHolidayAlarmCondition) {
		//「次回年休付与日までの期間の条件で絞り込む」をチェックする
		boolean check = annualHolidayAlarmCondition.getAlarmCheckSubConAgr().isNarrowUntilNext();
		if(!check)
			return true;
		Period period = new Period();
		//期間．開始日←システム日付
		period.setStartDate(GeneralDate.today());
		//期間．終了日←システム日付＋次回年休付与日までの期間(月数)
		int monthAdd = annualHolidayAlarmCondition.getAlarmCheckSubConAgr().getPeriodUntilNext().get().v();
		period.setEndDate(GeneralDate.today().addMonths(monthAdd));
		//次回年休付与日が指定期間内に存在するかチェック
		return checkExistHolidayGrantAdapter.checkExistHolidayGrantAdapter(employeeId, GeneralDate.today(), period);
	}
}
