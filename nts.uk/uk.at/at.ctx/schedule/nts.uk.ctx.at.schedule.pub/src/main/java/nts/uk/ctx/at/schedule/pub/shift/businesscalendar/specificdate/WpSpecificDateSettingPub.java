package nts.uk.ctx.at.schedule.pub.shift.businesscalendar.specificdate;

import nts.arc.time.GeneralDate;
/**
 * 
 * @author Doan Duy Hung
 *
 */
public interface WpSpecificDateSettingPub {
	
	/**
	 * find specific date setting
	 * @param companyID company ID
	 * @param workPlaceID workplace ID
	 * @param date date
	 * @return
	 * // RequestList #21
	 */
	public WpSpecificDateSettingExport workplaceSpecificDateSettingService(String companyID, String workPlaceID, GeneralDate date);
	
}
