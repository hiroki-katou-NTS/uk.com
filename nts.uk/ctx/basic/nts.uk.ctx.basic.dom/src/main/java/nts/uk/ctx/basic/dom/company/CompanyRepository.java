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
	Optional<Company> getCompanyDetail(String companyCd);
	Optional<Company> getCompanyByUserKtSet(String companyCd, int use_Kt_Set);														

	/**
	 * find all company
	 * @return
	 */
	List<Company> getAllCompanys();
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
	/**
	 * Delete a company
	 * @param companyCode
	 */
	void delete(String companyCd);
}

