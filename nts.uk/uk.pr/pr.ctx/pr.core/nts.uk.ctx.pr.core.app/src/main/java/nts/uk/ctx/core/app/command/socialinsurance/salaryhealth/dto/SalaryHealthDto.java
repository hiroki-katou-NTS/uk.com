package nts.uk.ctx.core.app.command.socialinsurance.salaryhealth.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SalaryHealthDto {
	
	List<HealthInsuranceGradePerRewardMonthlyRangeDto> gradePerRewardMonthlyRange;
	List<HealthInsuranceStandardGradePerMonthDto> standardGradePerMonth;
	List<HealthInsurancePerGradeFeeDto> perGradeFee;
	SalaryHealthInsurancePremiumRateDto premiumRate;
	
}
