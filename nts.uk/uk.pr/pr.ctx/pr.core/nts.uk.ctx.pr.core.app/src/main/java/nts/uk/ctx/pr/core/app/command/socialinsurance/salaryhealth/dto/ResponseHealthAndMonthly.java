package nts.uk.ctx.pr.core.app.command.socialinsurance.salaryhealth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseHealthAndMonthly {
	private String healthInsuranceGrade;
	private String standardMonthlyFee;
	private String rewardMonthlyLowerLimit;
	private String rewardMonthlyUpperLimit;
	
}
