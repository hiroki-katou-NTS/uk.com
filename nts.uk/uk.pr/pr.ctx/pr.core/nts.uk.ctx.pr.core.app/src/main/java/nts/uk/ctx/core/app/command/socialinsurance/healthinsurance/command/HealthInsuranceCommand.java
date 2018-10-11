package nts.uk.ctx.core.app.command.socialinsurance.healthinsurance.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.core.app.command.socialinsurance.welfarepensioninsurance.command.YearMonthHistoryItemCommand;

@Data
@AllArgsConstructor
public class HealthInsuranceCommand {
	private String officeCode;
	private BonusHealthInsuranceRateCommand bonusHealthInsuranceRate;
    private HealthInsuranceMonthlyFeeCommand healthInsuranceMonthlyFee;
    private YearMonthHistoryItemCommand yearMonthHistoryItem;
}