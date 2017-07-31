package nts.uk.ctx.at.record.dom.dailyperformanceformat.repository;

import java.util.List;

import nts.uk.ctx.at.record.dom.dailyperformanceformat.BusinessType;

public interface BusinessTypesRepository {
	
	List<BusinessType> findAll(String companyId);

}
