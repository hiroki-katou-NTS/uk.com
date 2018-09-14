package nts.uk.ctx.core.app.find.socialinsurance.healthinsurance;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HealthInsuranceDto {
    private BonusHealthInsuranceRateDto bonusHealthInsuranceRateDto;
    private HealthInsuranceMonthlyFeeDto healthInsuranceMonthlyFeeDto;
}