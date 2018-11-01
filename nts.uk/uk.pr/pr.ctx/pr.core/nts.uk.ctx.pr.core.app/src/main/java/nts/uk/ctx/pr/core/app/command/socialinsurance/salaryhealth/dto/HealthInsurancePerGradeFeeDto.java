package nts.uk.ctx.pr.core.app.command.socialinsurance.salaryhealth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HealthInsurancePerGradeFeeDto {
	
	private int healthInsuranceGrade;
	private String  inHealthInsurancePremium;
	private String  inNursingCare;
	private String  inSpecInsurancePremium;
	private String  inBasicInsurancePremium;
	private String  emHealthInsurancePremium;
	private String  emNursingCare;
	private String  emSpecInsurancePremium;
	private String  emBasicInsurancePremium;
}
