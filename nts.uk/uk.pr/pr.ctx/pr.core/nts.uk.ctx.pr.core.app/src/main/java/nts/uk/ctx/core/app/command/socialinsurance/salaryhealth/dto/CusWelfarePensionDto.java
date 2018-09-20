package nts.uk.ctx.core.app.command.socialinsurance.salaryhealth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CusWelfarePensionDto {
	
	private int welfarePensionGrade;
	private long standardMonthlyFee;
	private long rewardMonthlyLowerLimit;
	private long rewardMonthlyUpperLimit;
	private String fIndividualBurdenRatio;
	private String mIndividualBurdenRatio;
	private String fEmployeeContributionRatio;
	private String mEmployeeContributionRatio;
	private String fIndividualExemptionRate;
	private String mIndividualExemptionRate;
	private String fEmployeeExemptionRate;
	private String mEmployeeExemptionRate;
	
}
