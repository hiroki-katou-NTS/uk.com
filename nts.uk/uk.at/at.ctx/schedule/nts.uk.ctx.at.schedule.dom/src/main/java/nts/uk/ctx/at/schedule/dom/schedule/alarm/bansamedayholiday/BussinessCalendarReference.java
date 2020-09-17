package nts.uk.ctx.at.schedule.dom.schedule.alarm.bansamedayholiday;

import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.WorkdayDivision;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.daycalendar.CalendarClass;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.daycalendar.CalendarCompany;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.daycalendar.CalendarWorkplace;
/**
 * 営業日カレンダーの参照先
 * @author lan_lt
 *
 */
public interface BussinessCalendarReference {
  
	// 参照している営業日カレンダーの種類を取得する
	BusinessDaysCalendarType getBusinessDaysCalendarType();

	// 稼働日区分を取得する
	Optional<WorkdayDivision> getWorkdayDivision(Require require, GeneralDate day);

	public  interface Require{
		// [R-1] 指定日の会社営業日カレンダーを取得する
		Optional<CalendarCompany> getCalendarCompanyByDay(String cid, GeneralDate day);
		
		// [R-2] 指定日の職場営業日カレンダーを取得する
		Optional<CalendarWorkplace> getCalendarWorkplaceByDay(String cid, String workplaceId, GeneralDate day);
		
		// [R-3] 指定日の分類営業日カレンダーを取得する
		Optional<CalendarClass> getCalendarClassByDay(String cid, String clssCode, GeneralDate day);
	}

}
