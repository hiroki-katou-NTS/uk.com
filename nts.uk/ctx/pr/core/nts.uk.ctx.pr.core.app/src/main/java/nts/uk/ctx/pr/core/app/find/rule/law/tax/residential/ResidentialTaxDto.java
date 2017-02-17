package nts.uk.ctx.pr.core.app.find.rule.law.tax.residential;

import lombok.Value;
import nts.uk.ctx.pr.core.dom.rule.law.tax.residential.ResidentalTax;

/**
 * 
 * @author lanlt
 *
 */
@Value
public class ResidentialTaxDto {
	private String companyCode;
	private String resiTaxCode;
	private String companyAccountNo;
	private String companySpecifiedNo;
	private String cordinatePostalCode;
	private String cordinatePostOffice;
	private String Memo;
	private String prefectureCode;
	private String registeredName;
	private String resiTaxAutonomy;
	private String resiReportCode;

	public static ResidentialTaxDto fromDomain(ResidentalTax domain) {
		return new ResidentialTaxDto(domain.getCompanyCode().v(), domain.getResiTaxCode().v(),
				domain.getCompanyAccountNo().v(), domain.getCompanySpecifiedNo().v(),
				domain.getCordinatePostalCode().v(), domain.getCordinatePostOffice().v(), domain.getMemo().v(),
				domain.getPrefectureCode().v(), domain.getRegisteredName().v(),
				domain.getResiTaxAutonomy().v(),
				domain.getResiTaxReportCode().v());

	}
}
