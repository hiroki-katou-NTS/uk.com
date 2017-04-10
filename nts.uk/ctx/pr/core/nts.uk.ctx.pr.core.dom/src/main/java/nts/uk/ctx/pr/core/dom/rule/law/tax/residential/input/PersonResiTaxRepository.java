package nts.uk.ctx.pr.core.dom.rule.law.tax.residential.input;

import java.util.List;

/**
 * 
 * @author sonnh1
 *
 */
public interface PersonResiTaxRepository {
	/**
	 * 
	 * @param companyCode
	 * @param personId
	 * @param yearKey
	 * @return
	 */
	List<PersonResiTax> findAll(String companyCode, String personId, int yearKey);
	
	/**
	 * 
	 * @param companyCode
	 * @param residenceCode
	 * @param yearKey
	 * @return
	 */
	List<PersonResiTax> findByResidenceCode(String companyCode, String residenceCode, int yearKey);
	/**
	 * 
	 * @param domain
	 */
	void update(PersonResiTax domain);
	/**
	 * 
	 * @param companyCode
	 * @param personId
	 * @param yearKey
	 */
	void remove(String companyCode, String personId, int yearKey);
}
