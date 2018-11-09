package nts.uk.ctx.pr.core.app.command.socialinsurance.salaryhealth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmployeesPensionContributionRateDto {
	
	private String mIndividualBurdenRatio;
	private String fIndividualBurdenRatio;
	private String mEmployeeContributionRatio;
	private String fEmployeeContributionRatio;
	private String mIndividualExemptionRate;
	private String fIndividualExemptionRate;
	private String mEmployeeExemptionRate;
	private String fEmployeeExemptionRate;
	
}
