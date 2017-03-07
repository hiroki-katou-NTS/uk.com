/**
 * 
 */
package nts.uk.ctx.pr.core.dom.rule.law.tax.residential;

import java.util.List;
import java.util.Optional;

/**
 * @author lanlt
 *
 */
public interface ResidentialTaxRepository {
	/**
	 * SEL_1
	 * get list residental by company code
	 * @param companyCode
	 * @return List<ResidentalTax>
	 */
	List<ResidentialTax> getAllResidentialTax(String companyCode);
	List<ResidentialTax> getAllResidentialTax(String companyCode, String resiTaxCode,  String  resiTaxReportCode);
	//SELECT_3
	Optional<ResidentialTax> getResidentialTax(String companyCode, String resiTaxCode);

	//Optional<ResidentalTax> getDetailResidentalTax(String);
	void add(ResidentialTax residentalTax);
	//void update(String companyCode,String resiTaxCode);
	void delele(String companyCode,String resiTaxCode);
	//void delete(String resiTaxCode, String resiTaxReportCode);
	void update(ResidentialTax residentalTax);
	

}
