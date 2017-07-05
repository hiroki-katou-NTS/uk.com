package nts.uk.ctx.at.shared.dom.specificdate.repository;

import java.util.List;

import nts.uk.ctx.at.shared.dom.specificdate.item.SpecificDateItem;

public interface SpecificDateItemRepository {
	
	List<SpecificDateItem> getAll(String companyId);

}
