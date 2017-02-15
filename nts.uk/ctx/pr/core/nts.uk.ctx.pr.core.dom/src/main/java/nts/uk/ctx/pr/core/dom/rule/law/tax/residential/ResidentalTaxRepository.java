/**
 * 
 */
package nts.uk.ctx.pr.core.dom.rule.law.tax.residential;

import java.util.List;

/**
 * @author lanlt
 *
 */
public interface ResidentalTaxRepository {
	/**
	 * SEL_1
	 * get list residental by company code
	 * @param companyCode
	 * @return
	 */
	List<ResidentalTax> getAllResidentalTax(String companyCode);
	//Optional<ResidentalTax> getDetailResidentalTax(String);
	void add(ResidentalTax residentalTax);
	void update(String companyCode,String resiTaxCode);
	void delele(String companyCode,String resiTaxCode);
	

}
