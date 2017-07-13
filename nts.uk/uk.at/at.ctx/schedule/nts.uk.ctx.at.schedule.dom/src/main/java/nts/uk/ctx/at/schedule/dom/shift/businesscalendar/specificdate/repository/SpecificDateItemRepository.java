package nts.uk.ctx.at.schedule.dom.shift.businesscalendar.specificdate.repository;

import java.util.List;

import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.specificdate.item.SpecificDateItem;

public interface SpecificDateItemRepository {
	
	/**
	 * Get all Specific Date Item 
	 * @param companyId
	 * @return
	 */
	List<SpecificDateItem> getAll(String companyId);
	
	/**
	 * get Specific Date is Use
	 * @param companyId
	 * @param useAtr
	 * @return
	 */
	List<SpecificDateItem> getSpecifiDateByUse(String companyId, int useAtr);
}
