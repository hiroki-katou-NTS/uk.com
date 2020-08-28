package nts.uk.ctx.at.schedule.dom.shift.specificdayset.service;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.daycalendar.UseSet;

/**
 * カレンダー情報を取得する
 * @author Doan Duy Hung
 *
 */
public interface ICalendarInformationService {
	
	public CalendarInformationOutput getCalendarInformation(String companyID, String workplaceID, String classCD, GeneralDate date);
	
	public UseSet getWorkingDayAtr(String companyID, String workplaceID, String classCD, GeneralDate date);
	
}
