package nts.uk.ctx.at.schedule.dom.shift.businesscalendar.specificdate.repository;

import java.util.List;

import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.specificdate.item.SpecificDateItem;

public interface SpecificDateItemRepository {
	
	List<SpecificDateItem> getAll(String companyId);

}
