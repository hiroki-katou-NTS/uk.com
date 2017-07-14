package nts.uk.ctx.at.schedule.dom.shift.businesscalendar.specificdate.repository;

import java.util.List;

import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.specificdate.item.SpecificDateItem;

public interface SpecificDateItemRepository {
	
	/**
	 * 
	 * @param companyId
	 * @return
	 */
	List<SpecificDateItem> getAll(String companyId);
	/**
	 * 
	 * @param companyId
	 * @return
	 */
	List<SpecificDateItem> getSpecifiDateByUse(String companyId,int useAtr);
	/**
	 * hoatt
	 * update list Specific Date Item
	 * @param lstSpecificDateItem
	 */
	void updateDivTime(List<SpecificDateItem> lstSpecificDateItem);

}
