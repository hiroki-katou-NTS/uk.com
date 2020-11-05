package nts.uk.ctx.at.schedule.dom.shift.management.shifttable;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.schedule.dom.shift.management.workavailability.WorkAvailabilityOfOneDay;

/**
 * シフト表の設定
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.シフト管理.シフト勤務.シフト表.シフト表の設定
 * @author hiroko_miura
 *
 */
public interface WorkAvailabilityRule {

	/**
	 * シフト勤務単位は
	 * @return
	 */
	WorkAvailabilityPeriodUnit getShiftPeriodUnit();
	
	/**
	 * 何日前に通知するかの最大日数
	 * @return
	 */
	int getMaxFromNoticeDays();
	
	/**
	 * 締切日を過ぎているか
	 * 過ぎる場合 return true
	 * @param availabilityDate 希望日
	 * @return
	 */
	boolean isOverDeadline(GeneralDate availabilityDate);
	
	/**
	 * 休日日数の上限日数を超えているか
	 * 超える場合 return true
	 * @param workAvailabilityList 一日の勤務希望リスト: 休日、シフト、時間帯の全部
	 * @return
	 */
	boolean isOverHolidayMaxDays(List<WorkAvailabilityOfOneDay> workAvailabilityList);
	
	/**
	 * 基準日に対応する締切日と期間を取得する
	 * @param baseDate 基準日
	 * @return
	 */
	DeadlineAndPeriodOfWorkAvailability getCorrespondingDeadlineAndPeriod(GeneralDate baseDate);
	
	/**
	 * 希望日を含める期間を取得する
	 * @param availabilityDate 希望日
	 * @return
	 */
	DatePeriod getPeriodWhichIncludeAvailabilityDate(GeneralDate availabilityDate);
	
}
