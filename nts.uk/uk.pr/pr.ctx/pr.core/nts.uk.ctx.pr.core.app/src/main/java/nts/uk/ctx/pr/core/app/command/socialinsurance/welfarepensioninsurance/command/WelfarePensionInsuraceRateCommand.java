package nts.uk.ctx.pr.core.app.command.socialinsurance.welfarepensioninsurance.command;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WelfarePensionInsuraceRateCommand {
	private String officeCode;
	private BonusEmployeePensionInsuranceRateCommand bonusEmployeePensionInsuranceRate;
	private EmployeesPensionMonthlyInsuranceFeeCommand employeesPensionMonthlyInsuranceFee;
	private WelfarePensionInsuranceClassificationCommand welfarePensionInsuranceClassification;
	private YearMonthHistoryItemCommand yearMonthHistoryItem;
}
