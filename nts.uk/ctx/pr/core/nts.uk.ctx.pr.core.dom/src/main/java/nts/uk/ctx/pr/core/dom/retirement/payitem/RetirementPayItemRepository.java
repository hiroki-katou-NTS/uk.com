package nts.uk.ctx.pr.core.dom.retirement.payitem;

import java.util.List;

import nts.uk.ctx.core.dom.company.CompanyCode;

/**
 * 
 * @author Doan Duy Hung
 *
 */
public interface RetirementPayItemRepository {
	
	void add(RetirementPayItem payItem);
	
	List<RetirementPayItem> findAll();
	
	List<RetirementPayItem> findByCompanyCode(CompanyCode companyCode);
	
	void update(RetirementPayItem payItem);
	
	void remove(RetirementPayItem payItem);
}
