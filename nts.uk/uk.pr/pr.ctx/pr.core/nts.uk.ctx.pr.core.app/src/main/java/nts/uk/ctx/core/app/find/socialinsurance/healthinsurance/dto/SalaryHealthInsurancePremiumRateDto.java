package nts.uk.ctx.core.app.find.socialinsurance.healthinsurance.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 給与健康保険料率
 */
@AllArgsConstructor
@Data
public class SalaryHealthInsurancePremiumRateDto {
    /**
     * 事業主負担分計算方法
     */
    private Integer employeeShareAmountMethod;

    /**
     * 個人負担率
     */
    private HealthContributionRateDto individualBurdenRatio;

    /**
     * 事業主負担率
     */
    private HealthContributionRateDto employeeBurdenRatio;
}
