package nts.uk.ctx.core.app.command.socialinsurance.healthinsurance;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.core.app.find.socialinsurance.healthinsurance.BonusHealthInsuranceRateDto;
import nts.uk.ctx.core.app.find.socialinsurance.healthinsurance.HealthInsuranceMonthlyFeeDto;

import java.time.YearMonth;

@Data
@AllArgsConstructor
public class AddHealthInsuranceCommand {
    private String officeCode;
    private int startYearMonth;
    private int endYearMonth;
    private BonusHealthInsuranceRateDto bonusHealthInsuranceRateDto;
    private HealthInsuranceMonthlyFeeDto healthInsuranceMonthlyFeeDto;
}