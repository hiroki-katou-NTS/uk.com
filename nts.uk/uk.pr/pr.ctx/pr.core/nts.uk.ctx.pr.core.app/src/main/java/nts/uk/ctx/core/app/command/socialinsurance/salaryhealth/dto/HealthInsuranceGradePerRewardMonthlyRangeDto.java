package nts.uk.ctx.core.app.command.socialinsurance.salaryhealth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HealthInsuranceGradePerRewardMonthlyRangeDto {
	
	private int healthInsuranceGrade;
	private long rewardMonthlyLowerLimit;
	private long rewardMonthlyUpperLimit;
	
}
