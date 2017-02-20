package nts.uk.ctx.pr.core.app.command.rule.law.tax.residential;

import lombok.Getter;
import nts.uk.ctx.pr.core.dom.rule.law.tax.residential.ResidentalTax;
/**
 * 
 * @author lanlt
 *
 */
@Getter
public class UpdateResidentalTaxCommand {
	private String companyCode;
	private String resiTaxCode;
	private String companyAccountNo;
	private String companySpecifiedNo;
	private String cordinatePostalCode;
	private String cordinatePostOffice;
	private String memo;
	private String prefectureCode;
	private String registeredName;
	private String resiTaxAutonomy;
	private String resiTaxReportCode;
	/**
	 * Convert to domain object from command values
	 * @return
	 */
	public ResidentalTax toDomain(String companyCode,String resiTaxCode){
		return ResidentalTax.createFromJavaType(companyCode, companyAccountNo, companySpecifiedNo,
				cordinatePostOffice, cordinatePostalCode, memo,
				prefectureCode, registeredName, 
				resiTaxAutonomy, resiTaxCode, resiTaxReportCode);
	}	
}
