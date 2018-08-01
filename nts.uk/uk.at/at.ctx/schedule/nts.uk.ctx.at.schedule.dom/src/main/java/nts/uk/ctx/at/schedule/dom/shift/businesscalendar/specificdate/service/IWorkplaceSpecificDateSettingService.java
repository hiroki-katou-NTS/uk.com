package nts.uk.ctx.at.schedule.dom.shift.businesscalendar.specificdate.service;

import java.util.List;

import nts.arc.time.GeneralDate;
/**
 * 
 * @author Doan Duy Hung
 *
 */
public interface IWorkplaceSpecificDateSettingService {
	
	public SpecificDateItemOutput workplaceSpecificDateSettingService(String companyID, String workPlaceID, GeneralDate date);
	
	/**
	 * RequestList #490
	 * 職場ID(List)に該当する特定日設定を取得する
	 * @param companyID
	 * @param workPlaceIDLst
	 * @param date
	 * @return
	 */
	public SpecificDateItemOutput workplaceSpecificDateSettingService(String companyID, List<String> workPlaceIDLst, GeneralDate date);
	
}
