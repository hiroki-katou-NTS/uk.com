package nts.uk.ctx.core.app.command.socialinsurance.salaryhealth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SalaryHealthInsurancePremiumRateDto {
	
	private String inHealthInsuranceRate;
	private String inLongCareInsuranceRate;
	private String inSpecialInsuranceRate;
	private String inBasicInsuranceRate;
	private String emHealthInsuranceRate;
	private String emLongCareInsuranceRate;
	private String emSpecialInsuranceRate;
	private String emBasicInsuranceRate;
	public SalaryHealthInsurancePremiumRateDto() {
		super();
	}
	
}
