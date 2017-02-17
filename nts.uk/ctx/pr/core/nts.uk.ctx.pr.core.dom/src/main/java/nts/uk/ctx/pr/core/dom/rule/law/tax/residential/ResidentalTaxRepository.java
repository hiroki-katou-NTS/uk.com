/**
 * 
 */
package nts.uk.ctx.pr.core.dom.rule.law.tax.residential;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.pr.core.dom.layout.LayoutMaster;

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
	void update(ResidentalTax residentalTax);
	void delele(String companyCode);
	

}
