package nts.uk.ctx.pr.core.app.command.socialinsurance.salaryhealth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WelfarePensionGradePerRewardMonthlyRangeDto {
	
	private int welfarePensionGrade;
	private long rewardMonthlyLowerLimit;
	private long rewardMonthlyUpperLimit;
	
}
