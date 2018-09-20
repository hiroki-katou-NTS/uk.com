package nts.uk.ctx.core.app.command.socialinsurance.salaryhealth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SalaryEmployeesPensionInsuranceRateDto {
	
	private String fIndividualBurdenRatio;
	private String mIndividualBurdenRatio;
	private String fEmployeeContributionRatio;
	private String mEmployeeContributionRatio;
	private String fIndividualExemptionRate;
	private String mIndividualExemptionRate;
	private String fEmployeeExemptionRate;
	private String mEmployeeExemptionRate;
	public SalaryEmployeesPensionInsuranceRateDto() {
		super();
	}
	
}
