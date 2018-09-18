package nts.uk.ctx.core.app.command.socialinsurance.healthinsurance.command;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HealthInsuranceCommand {
    private BonusHealthInsuranceRateCommand bonusHealthInsuranceRateDto;
    private HealthInsuranceMonthlyFeeCommand healthInsuranceMonthlyFeeDto;
}