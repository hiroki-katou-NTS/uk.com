package nts.uk.ctx.at.schedule.pub.shift.businesscalendar.specificdate;

import java.util.List;

import nts.arc.time.GeneralDate;
/**
 * 
 * @author Doan Duy Hung
 *
 */
public interface WpSpecificDateSettingPub {
	
	/**
	 * RequestList21
	 * 
	 * find specific date setting
	 * @param companyID company ID
	 * @param workPlaceID workplace ID
	 * @param date date
	 * @return
	 * 
	 */
	public WpSpecificDateSettingExport workplaceSpecificDateSettingService(String companyID, String workPlaceID, GeneralDate date);
	
	/**
	 * getSpecifiDateByListCode
	 * @param companyId
	 * @param lstSpecificDateItem
	 * @return
	 */
	public List<SpecificDateItemExport> getSpecifiDateByListCode(String companyId, List<Integer> lstSpecificDateItem);
	
}
