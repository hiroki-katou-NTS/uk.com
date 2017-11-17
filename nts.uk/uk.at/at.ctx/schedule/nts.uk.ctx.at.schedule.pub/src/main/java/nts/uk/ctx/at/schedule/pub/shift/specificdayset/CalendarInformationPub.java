package nts.uk.ctx.at.schedule.pub.shift.specificdayset;

import java.util.List;

import nts.arc.time.GeneralDate;

/**
 * カレンダー情報を取得する
 * @author Doan Duy Hung
 *
 */
public interface CalendarInformationPub {
	
	public List<CalendarInformationExport> getCalendarInformation(String companyID, String workplaceID, String classCD, GeneralDate date);

}
