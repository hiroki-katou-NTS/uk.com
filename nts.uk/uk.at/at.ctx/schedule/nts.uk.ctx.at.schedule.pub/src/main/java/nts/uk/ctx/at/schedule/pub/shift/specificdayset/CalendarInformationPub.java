package nts.uk.ctx.at.schedule.pub.shift.specificdayset;

import nts.arc.time.GeneralDate;

/**
 * カレンダー情報を取得する
 * @author Doan Duy Hung
 *
 */
public interface CalendarInformationPub {
	
	public CalendarInformationExport getCalendarInformation(String companyID, String workplaceID, String classCD, GeneralDate date);

}
