package nts.uk.ctx.at.schedule.pub.shift.businesscalendar.daycalendar;

import nts.arc.time.GeneralDate;
/**
 * 締切日を取得する
 * @author Doan Duy Hung
 *
 */
public interface ObtainDeadlineDatePub {
	
	/**
	 * RequestList #29
	 * 締切日を取得する
	 * @param targetDate
	 * @param specDayNo
	 * @param workplaceID
	 * @param companyID
	 * @return
	 */
	public GeneralDate obtainDeadlineDate(GeneralDate targetDate, Integer specDayNo, String workplaceID, String companyID);
	
}
