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
	public Optional<Company> find(String companyCode);
	
	/**
	 * Find all companies.
	 * 
	 * @return companies
	 */
	public List<Company> findAll();
	
	/**
	 * Add new company.
	 * 
	 * @param company to be added
	 */
	public void add(Company company);
	
	/**
	 * Update company.
	 * 
	 * @param company to be updated
	 */
	public void update(Company company);
	
	/**
	 * Remove company.
	 * 
	 * @param companyCode code of company to be removed
	 */
	public void remove(CompanyCode companyCode);
}
