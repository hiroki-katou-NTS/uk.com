package nts.uk.ctx.at.schedule.pub.shift.specificdayset;

import nts.arc.time.GeneralDate;

/**
 * カレンダー情報を取得する
 * @author Doan Duy Hung
 *
 */
public interface CalendarInformationPub {
	
	public CalendarInformationExport getCalendarInformation(String companyID, String workplaceID, String classCD, GeneralDate date);
	
	//RQ651：稼働日区分を取得する
	public Integer getWorkingDayAtr(String companyID, String workplaceID, String classCD, GeneralDate date);

	BasicWorkSettingExport getBasicWorkSetting(String companyID, String workplaceID, String classCD,
			Integer workingDayAtr);


}
