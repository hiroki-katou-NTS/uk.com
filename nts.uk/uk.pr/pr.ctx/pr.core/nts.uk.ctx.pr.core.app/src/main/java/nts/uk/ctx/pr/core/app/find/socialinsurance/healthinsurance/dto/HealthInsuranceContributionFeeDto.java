package nts.uk.ctx.pr.core.app.find.socialinsurance.healthinsurance.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 各負担料
 */
@AllArgsConstructor
@Data
public class HealthInsuranceContributionFeeDto {
    /**
     * 健康保険料
     */
    private BigDecimal healthInsurancePremium;

    /**
     * 介護保険料
     */
    private BigDecimal nursingCare;

    /**
     * 特定保険料
     */
    private BigDecimal specInsurancePremium;

    /**
     * 基本保険料
     */
    private BigDecimal basicInsurancePremium;
}
