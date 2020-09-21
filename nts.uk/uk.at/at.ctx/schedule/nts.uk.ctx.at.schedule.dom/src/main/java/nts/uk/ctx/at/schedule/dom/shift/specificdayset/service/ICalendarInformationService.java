package nts.uk.ctx.at.schedule.dom.shift.specificdayset.service;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.BasicWorkSetting;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.WorkdayDivision;

/**
 * カレンダー情報を取得する
 * @author Doan Duy Hung
 *
 */
public interface ICalendarInformationService {
	
	public CalendarInformationOutput getCalendarInformation(String companyID, String workplaceID, String classCD, GeneralDate date);
	
	public WorkdayDivision getWorkingDayAtr(String companyID, String workplaceID, String classCD, GeneralDate date);
	
	public BasicWorkSetting getBasicWorkSetting (String companyID, String workplaceID, String classCD, Integer workingDayAtr);
	
}
