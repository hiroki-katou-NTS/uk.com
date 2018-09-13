package nts.uk.ctx.core.dom.socialinsurance.healthinsurance;

import java.util.Optional;

/**
 * 健康保険標準月額
 */
public interface HealthInsuranceStandardMonthlyRepository {
    /**
     * Get 健康保険標準月額 by id
     *
     * @param targetStartYm 年月開始
     * @param targetEndYm   年月終了
     * @return Optional<HealthInsuranceStandardMonthly>
     */
    Optional<HealthInsuranceStandardMonthly> getHealthInsuranceStandardMonthlyById(int targetStartYm, int targetEndYm);
}
