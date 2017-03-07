package nts.uk.ctx.pr.core.app.command.rule.law.tax.residential.input;

import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
public class RemovePersonResiTaxCommand {
	private int yearKey;
//	private String residenceCode;
//	private BigDecimal residenceTaxBn;
//	private BigDecimal residenceTaxAve;
//	private List<ResidenceTaxCommand> residenceTax;
//	private int residenceTaxLumpAtr;
//	private int residenceTaxLumpYm;
//	private int residenceTaxLevyAtr;
	
//	public PersonResiTax toDomain(String companyCode, String personId){
//		PersonResiTax domain = PersonResiTax.createFromJavaType(companyCode, personId, 
//				this.yearKey,
//				this.residenceCode, 
//				this.residenceTaxBn,
//				this.residenceTaxAve,
//				this.residenceTaxLumpAtr,
//				this.residenceTaxLumpYm, 
//				this.residenceTaxLevyAtr);
//		domain.createResidenceTaxFromJavaType(residenceTax.get(0).month, residenceTax.get(0).getValue(),
//				residenceTax.get(1).month, residenceTax.get(1).getValue(),
//				residenceTax.get(2).month, residenceTax.get(2).getValue(),
//				residenceTax.get(3).month, residenceTax.get(3).getValue(),
//				residenceTax.get(4).month, residenceTax.get(4).getValue(),
//				residenceTax.get(5).month, residenceTax.get(5).getValue(),
//				residenceTax.get(6).month, residenceTax.get(6).getValue(), 
//				residenceTax.get(7).month, residenceTax.get(7).getValue(), 
//				residenceTax.get(8).month, residenceTax.get(8).getValue(), 
//				residenceTax.get(9).month, residenceTax.get(9).getValue(),
//				residenceTax.get(10).month, residenceTax.get(10).getValue(),
//				residenceTax.get(11).month, residenceTax.get(11).getValue());
//		return domain;
//	}
}
