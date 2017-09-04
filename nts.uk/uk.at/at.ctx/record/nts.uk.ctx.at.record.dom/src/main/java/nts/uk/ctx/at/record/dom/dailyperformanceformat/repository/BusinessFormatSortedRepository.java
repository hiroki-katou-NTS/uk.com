package nts.uk.ctx.at.record.dom.dailyperformanceformat.repository;

import java.util.List;

import nts.uk.ctx.at.record.dom.dailyperformanceformat.BusinessTypeSorted;

public interface BusinessFormatSortedRepository {
	
	void add(List<BusinessTypeSorted> businessTypeSorteds);
	
	void update(BusinessTypeSorted businessTypeSorted);
	
	List<BusinessTypeSorted> find(String companyId, List<Integer> attendanceItemId);
	
	List<BusinessTypeSorted> findAll(String companyId);

}
