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
	 * @return
	 */
	List<PersonResiTax> findAll(String companyCode, String personId, int yearKey);
	
	void update();
	
	void remove();
}
