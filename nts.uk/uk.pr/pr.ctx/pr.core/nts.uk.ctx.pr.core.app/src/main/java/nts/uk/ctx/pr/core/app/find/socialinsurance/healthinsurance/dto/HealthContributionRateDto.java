package nts.uk.ctx.pr.core.app.find.socialinsurance.healthinsurance.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class HealthContributionRateDto {
    /**
     * 介護保険料率
     */
    private BigDecimal longCareInsuranceRate;

    /**
     * 基本保険料率
     */
    private BigDecimal basicInsuranceRate;

    /**
     * 健康保険料率
     */
    private BigDecimal healthInsuranceRate;

    /**
     * 端数区分
     */
    private Integer fractionCls;

    /**
     * 特定保険料率
     */
    private BigDecimal specialInsuranceRate;
}
