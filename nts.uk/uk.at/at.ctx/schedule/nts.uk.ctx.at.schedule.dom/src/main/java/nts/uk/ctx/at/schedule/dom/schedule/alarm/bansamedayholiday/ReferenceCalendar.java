package nts.uk.ctx.at.schedule.dom.schedule.alarm.bansamedayholiday;

import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.WorkdayDivision;
/**
 * 営業日カレンダーの参照先
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.勤務予定.勤務予定のアラームチェック.同日休日禁止.営業日カレンダーの参照先
 *
 */
public interface ReferenceCalendar {
  
	// 参照している営業日カレンダーの種類を取得する
	BusinessDaysCalendarType getBusinessDaysCalendarType();

	// 稼働日区分を取得する
	Optional<WorkdayDivision> getWorkdayDivision(Require require, GeneralDate date);

	public static interface Require extends ReferenceCalendarClass.Require, ReferenceCalendarCompany.Require, ReferenceCalendarWorkplace.Require {
	
	}

}
