package nts.uk.ctx.at.schedule.dom.shift.specificdayset.service;

import nts.arc.time.GeneralDate;

/**
 * カレンダー情報を取得する
 * @author Doan Duy Hung
 *
 */
public interface ICalendarInformationService {
	
	public CalendarInformationOutput getCalendarInformation(String companyID, String workplaceID, String classCD, GeneralDate date);
	
}
