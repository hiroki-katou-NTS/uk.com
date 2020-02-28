package nts.uk.ctx.pr.core.app.find.socialinsurance.healthinsurance.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 等級毎健康保険料
 */
@AllArgsConstructor
@Data
public class HealthInsurancePerGradeFeeDto {
    /**
     * 健康保険等級
     */
    private int healthInsuranceGrade;

    /**
     * 事業主負担
     */
    private HealthInsuranceContributionFeeDto employeeBurden;

    /**
     * 被保険者負担
     */
    private HealthInsuranceContributionFeeDto insuredBurden;
}
