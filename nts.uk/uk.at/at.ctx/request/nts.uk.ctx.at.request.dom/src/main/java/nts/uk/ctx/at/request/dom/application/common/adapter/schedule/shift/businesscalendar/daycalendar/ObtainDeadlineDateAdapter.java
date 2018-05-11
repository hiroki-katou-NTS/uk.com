package nts.uk.ctx.at.request.dom.application.common.adapter.schedule.shift.businesscalendar.daycalendar;

import nts.arc.time.GeneralDate;

/**
 * 
 * @author Doan Duy Hung
 *
 */
public interface ObtainDeadlineDateAdapter {
	
	/**
	 * 締切日を取得する
	 * @param targetDate
	 * @param specDayNo
	 * @param workplaceID
	 * @param companyID
	 * @return
	 */
	public GeneralDate obtainDeadlineDate(GeneralDate targetDate, Integer specDayNo, String workplaceID, String companyID);
	
}
