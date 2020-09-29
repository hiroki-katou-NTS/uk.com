package nts.uk.ctx.at.schedule.dom.shift.management.shifttable;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.schedule.dom.shift.management.workexpect.WorkExpectationOfOneDay;

/**
 * シフト表の設定
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.シフト管理.シフト勤務.シフト表.シフト表の設定
 * @author hiroko_miura
 *
 */
public interface ShiftTableSetting {

	/**
	 * シフト勤務単位は
	 * @return
	 */
	ShiftPeriodUnit getShiftPeriodUnit();
	
	/**
	 * 締切日を過ぎているか
	 * @return
	 */
	boolean isOverDeadline(GeneralDate expectingDate);
	
	/**
	 * 休日日数の上限日数を超えているか
	 * @return
	 */
	boolean isOverHolidayMaxdays(List<WorkExpectationOfOneDay> workExpectList);
	
	/**
	 * 基準日に対応する締切日と期間を取得する
	 * @return
	 */
	ShiftTableRuleInfo getcorrespondingDeadlineAndPeriod(GeneralDate baseDate);
	
	/**
	* 希望日を含める期間を取得する
	* @return
	*/
	DatePeriod getPeriodWhichIncludeExpectingDate(GeneralDate expectingDate);
	
}
