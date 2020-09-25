package nts.uk.ctx.at.schedule.dom.shift.management.shifttable;

import java.util.List;
import java.util.Optional;

import lombok.Value;
import nts.arc.layer.dom.objecttype.DomainValue;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.schedule.dom.shift.management.workexpect.AssignmentMethod;
import nts.uk.shr.com.enumcommon.NotUseAtr;

@Value
public class ShiftTableRule implements DomainValue {

	/**	公開運用区分 */
	private final NotUseAtr usePublicAtr;
	
	/** 勤務希望運用区分 */
	private final NotUseAtr useWorkAvailabilityAtr;
	
	/** シフト表の設定 */
	private final ShiftTableSetting shiftSetting;
	
	/** 勤務希望の指定できる方法 */
	private final List<AssignmentMethod> useAvailTypeList;
	
	/** 締切日の何日前に通知するかの日数 */
	private final Optional<FromNoticeDays> fromNoticeDays;
	
	
	/**
	 * 作る
	 * @param pubAtr 公開運用区分
	 * @param useavailAtr 勤務希望運用区分
	 * @param shiftSet シフト表の設定
	 * @param useAbailList 勤務希望の指定できる方法
	 * @param noticeDays 締切日の何日前に通知するかの日数
	 * @return
	 */
	public static ShiftTableRule create(NotUseAtr pubAtr, NotUseAtr useavailAtr
			, ShiftTableSetting shiftSet, List<AssignmentMethod> useAbailList, Optional<FromNoticeDays> noticeDays) {
		
		if (useavailAtr == NotUseAtr.USE) {
			
			if(!noticeDays.isPresent()) {
				throw new RuntimeException();
			}
			
			int maxNoticeDays = 0;

			switch (shiftSet.getShiftPeriodUnit()) {
				case WEEKLY:
					maxNoticeDays = 6;
				case MONTHLY:
					maxNoticeDays = 15;
			}
			
			if (noticeDays.get().v() > maxNoticeDays) {
				throw new RuntimeException("shiftunit out of range.");
			}
		}
		
		return new ShiftTableRule(pubAtr, useavailAtr, shiftSet, useAbailList, noticeDays);
	}
	
	/**
	 * 今日が通知をする日か
	 * @return
	 */
	public boolean isTodaytheNotify() {
		// ＄直近の締切日　＝　@シフト表の設定.直近の締切日は(システム日付)			
		GeneralDate deadline = this.shiftSetting.getMostRecentDeadlineDate(GeneralDate.today());
		
		// ＄通知日　＝　$直近の締切日.日を足す（-@締切日の何日前に通知するかの日数)
		GeneralDate noticeDate = deadline.addDays(this.fromNoticeDays.get().v() * -1);
		
		return GeneralDate.today().beforeOrEquals(noticeDate);
	}
	
	/**
	 * 希望提出可能な直近のシフト勤務期間を取得する
	 * @param date
	 * @return
	 */
	public DatePeriod getAvailabilityPeriod(GeneralDate date) {
		// ＄直近の締切日　＝　@シフト表の設定.直近の締切日は(対象日)
		GeneralDate deadline = this.shiftSetting.getMostRecentDeadlineDate(date);

		return this.shiftSetting.getAgainstDeadlinePeriod(deadline);
	}
}
