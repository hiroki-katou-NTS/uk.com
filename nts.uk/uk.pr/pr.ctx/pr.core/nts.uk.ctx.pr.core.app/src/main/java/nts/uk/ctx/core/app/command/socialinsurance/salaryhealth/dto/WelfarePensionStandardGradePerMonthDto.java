package nts.uk.ctx.core.app.command.socialinsurance.salaryhealth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WelfarePensionStandardGradePerMonthDto {
	
	 private int welfarePensionGrade;
	 private long standardMonthlyFee;
	
}
