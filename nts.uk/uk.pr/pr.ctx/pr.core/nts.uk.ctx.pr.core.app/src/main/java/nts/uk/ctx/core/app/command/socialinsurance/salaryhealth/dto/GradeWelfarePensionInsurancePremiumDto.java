package nts.uk.ctx.core.app.command.socialinsurance.salaryhealth.dto;

import lombok.AllArgsConstructor;

import lombok.Data;

@Data
@AllArgsConstructor
public class GradeWelfarePensionInsurancePremiumDto {
	
	private int welfarePensionGrade;
	private String inMaleInsurancePremium;
	private String emMaleInsurancePremium;
	private String inMaleExemptionInsurance;
	private String emMaleExemptionInsurance;
	private String inFemaleInsurancePremium;
	private String emFemaleInsurancePremium;
	private String inFemaleExemptionInsurance;
	private String emFemaleExemptionInsurance;
	
}
