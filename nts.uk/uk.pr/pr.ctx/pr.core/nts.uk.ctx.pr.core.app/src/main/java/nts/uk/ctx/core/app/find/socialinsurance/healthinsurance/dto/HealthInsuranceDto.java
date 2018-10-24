package nts.uk.ctx.core.app.find.socialinsurance.healthinsurance.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HealthInsuranceDto {
    /**
     * 賞与健康保険料率
     */
    private BonusHealthInsuranceRateDto bonusHealthInsuranceRateDto;
    /**
     * 健康保険月額保険料額
     */
    private HealthInsuranceMonthlyFeeDto healthInsuranceMonthlyFeeDto;
}