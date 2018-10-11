package nts.uk.ctx.core.dom.socialinsurance.healthinsurance;

import java.util.List;
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
    
    void deleteByHistoryIds(List<String> historyIds);
    
    void add(BonusHealthInsuranceRate domain);
    
    void update(BonusHealthInsuranceRate domain);
    
    void remove(BonusHealthInsuranceRate domain);
}
