package nts.uk.ctx.basic.dom.company;

import java.util.List;
import java.util.Optional;
/**
 * 
 * @author lanlt
 *
 */

public interface CompanyRepository {
	
	/**
	 * find a company by 
	 * @param companyCode
	 * @return
	 */
	Optional<Company> getCompanyDetail(String companyCode);
	/**
	 * find all company by company code
	 * @return
	 */
	List<Company> getAllCompanys();
	/**
	 * get a company before update
	 * @param companyCode
	 * @return
	 */
	List<Company> getHistoryBefore(String companyCode);
	/**
	 * Add a company
	 * @param company
	 */
	
	void add(Company company);
	/**
	 * Update a copany
	 * @param company
	 */
	void update(Company company);
	
}

