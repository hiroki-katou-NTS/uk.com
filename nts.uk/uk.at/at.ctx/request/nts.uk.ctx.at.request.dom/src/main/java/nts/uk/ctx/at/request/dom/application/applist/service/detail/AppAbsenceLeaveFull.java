package nts.uk.ctx.at.request.dom.application.applist.service.detail;

import nts.uk.ctx.at.request.dom.application.holidayshipment.absenceleaveapp.AbsenceLeaveWorkingHour;

/**
 * 振休申請
 * @author hoatt
 *
 */
public class AppAbsenceLeaveFull {

	/**
	 * 申請ID
	 */
	private String appID;
	/**
	 * 勤務種類
	 */
	private String workTypeCD;
	/**
	 * 勤務時間1
	 */
	private AbsenceLeaveWorkingHour WorkTime1;
	/**
	 * 勤務時間2
	 */
	private AbsenceLeaveWorkingHour WorkTime2;
}
