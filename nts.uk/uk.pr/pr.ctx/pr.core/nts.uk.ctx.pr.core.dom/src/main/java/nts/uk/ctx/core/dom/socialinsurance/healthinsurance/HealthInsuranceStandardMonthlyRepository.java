package nts.uk.ctx.core.dom.socialinsurance.healthinsurance;

import java.util.Optional;

/**
 * 健康保険標準月額
 */
public interface HealthInsuranceStandardMonthlyRepository {
    /**
     * Get 健康保険標準月額 by targetStartYm
     *
     * @param targetStartYm 年月開始
     * @return Optional<HealthInsuranceStandardMonthly>
     */
    Optional<HealthInsuranceStandardMonthly> getHealthInsuranceStandardMonthlyByStartYearMonth(int targetStartYm);
}
