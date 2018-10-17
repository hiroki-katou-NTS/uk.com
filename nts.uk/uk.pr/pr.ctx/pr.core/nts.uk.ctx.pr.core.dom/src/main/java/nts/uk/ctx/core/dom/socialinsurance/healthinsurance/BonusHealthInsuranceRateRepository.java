package nts.uk.ctx.core.dom.socialinsurance.healthinsurance;

import nts.uk.shr.com.history.YearMonthHistoryItem;

import java.util.List;
import java.util.Optional;

/**
 * 賞与健康保険料率
 */
public interface BonusHealthInsuranceRateRepository {
    /**
     * Get 賞与健康保険料率 by ID
     *
     * @return Optional<BonusHealthInsuranceRate>
     */

    Optional<HealthInsuranceFeeRateHistory> getHealthInsuranceHistoryByOfficeCode(String officeCode);

    Optional<BonusHealthInsuranceRate> getBonusHealthInsuranceRateById(String historyId);
    
    void deleteByHistoryIds(List<String> historyIds);
    
    void add(BonusHealthInsuranceRate domain, String officeCode, YearMonthHistoryItem yearMonth);
    
    void update(BonusHealthInsuranceRate domain, String officeCode, YearMonthHistoryItem yearMonth);
    
    void remove(BonusHealthInsuranceRate domain, String officeCode, YearMonthHistoryItem yearMonth);

    void updateHistory(String officeCode, YearMonthHistoryItem history);
}
