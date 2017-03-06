/**
 * 
 */
package nts.uk.ctx.pr.core.app.command.rule.law.tax.residential;

import lombok.Getter;
import nts.uk.ctx.pr.core.dom.rule.law.tax.residential.ResidentialTax;

/**
 * AddResidentalTaxCommand
 * @author lanlt
 *
 */
@Getter
public class AddResidentialTaxCommand {
	private String resiTaxCode;
	private String resiTaxAutonomy;
	private String prefectureCode;
	private String resiTaxReportCode;
	private String registeredName;
	private String companyAccountNo;
	private String companySpecifiedNo;
	private String cordinatePostalCode;
	private String cordinatePostOffice;
	private String memo;
	/**
	 * Convert to domain object from command values
	 * @return ResidentialTax
	 */
	public ResidentialTax toDomain(String companyCode){
		return ResidentialTax.createFromJavaType(companyCode, resiTaxCode,
				resiTaxAutonomy,prefectureCode,
				resiTaxReportCode, registeredName,
				companyAccountNo,companySpecifiedNo,
				cordinatePostOffice, cordinatePostalCode, memo);
	}	


}
