package nts.uk.ctx.core.dom.company;

import java.util.List;
import java.util.Optional;

/**
 * Repository for Company aggregate.
 */
public interface CompanyRepository {

	/**
	 * Find a company by code.
	 * 
	 * @param companyCode code
	 * @return company found
	 */
	Optional<Company> find(String companyCode);
	
	/**
	 * Find all companies.
	 * 
	 * @return companies
	 */
	List<Company> findAll();
	
	/**
	 * Add new company.
	 * 
	 * @param company to be added
	 */
	void add(Company company);
	
	/**
	 * Update company.
	 * 
	 * @param company to be updated
	 */
	void update(Company company);
	
	/**
	 * Remove company.
	 * 
	 * @param companyCode code of company to be removed
	 */
	void remove(CompanyCode companyCode);
}
