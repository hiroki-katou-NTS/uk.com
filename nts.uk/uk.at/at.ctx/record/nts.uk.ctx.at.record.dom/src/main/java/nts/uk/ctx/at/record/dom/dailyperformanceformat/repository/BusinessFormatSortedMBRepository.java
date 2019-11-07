package nts.uk.ctx.at.record.dom.dailyperformanceformat.repository;

import java.util.List;

import nts.uk.ctx.at.record.dom.dailyperformanceformat.BusinessTypeSortedMobile;

/**
 * 
 * @author anhdt
 *
 */
public interface BusinessFormatSortedMBRepository {
	void add(List<BusinessTypeSortedMobile> businessTypeSorteds);
	
	void update(BusinessTypeSortedMobile businessTypeSorted);
	
	List<BusinessTypeSortedMobile> find(String companyId, List<Integer> attendanceItemId);
	
	List<BusinessTypeSortedMobile> findAll(String companyId);

}
