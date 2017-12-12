package nts.uk.ctx.at.request.dom.application.common.adapter.schedule.shift.businesscalendar.specificdate;

import nts.arc.time.GeneralDate;
/**
 * 
 * @author Doan Duy Hung
 *
 */

public interface WpSpecificDateSettingAdapter {
	
	/**
	 * @param companyID
	 * @param workPlaceID
	 * @param date
	 * @return
	 */
	public WpSpecificDateSettingImport workplaceSpecificDateSettingService(String companyID, String workPlaceID, GeneralDate date);
	
}
