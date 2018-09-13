package nts.uk.ctx.core.dom.socialinsurance.healthinsurance;

import java.util.Optional;

/**
 * 賞与健康保険料率
 */
public interface BonusHealthInsuranceRateRepository {
    /**
     * Get 賞与健康保険料率 by ID
     *
     * @param historyId 履歴ID
     * @return Optional<BonusHealthInsuranceRate>
     */
    Optional<BonusHealthInsuranceRate> getBonusHealthInsuranceRateById(String historyId);
}
