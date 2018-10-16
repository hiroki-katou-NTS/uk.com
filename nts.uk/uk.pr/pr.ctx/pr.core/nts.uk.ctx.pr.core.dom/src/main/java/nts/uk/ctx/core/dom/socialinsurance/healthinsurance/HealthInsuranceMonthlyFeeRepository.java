package nts.uk.ctx.core.dom.socialinsurance.healthinsurance;

import nts.uk.shr.com.history.YearMonthHistoryItem;

import java.util.Optional;

import java.util.List;

/**
 * 健康保険月額保険料額
 */
public interface HealthInsuranceMonthlyFeeRepository {
    /**
     * Get HealthInsuranceMonthlyFee by history Id
     *
     * @param historyId historyId
     * @return HealthInsuranceMonthlyFee
     */
    Optional<HealthInsuranceMonthlyFee> getHealthInsuranceMonthlyFeeById(String historyId);

    void deleteByHistoryIds(List<String> historyIds);

    void add(HealthInsuranceMonthlyFee domain, String officeCode, YearMonthHistoryItem yearMonth);

    void update(HealthInsuranceMonthlyFee domain, String officeCode, YearMonthHistoryItem yearMonth);

    void delete(HealthInsuranceMonthlyFee domain, String officeCode, YearMonthHistoryItem yearMonth);
    
    void updateGraFee(HealthInsuranceMonthlyFee domain);
    
    void insertGraFee(HealthInsuranceMonthlyFee domain);
    
    void deleteHealthInsurancePerGradeByHistoryId (List<String> historyIds);

    void updatePreviousHistory(String officeCode, YearMonthHistoryItem history);
}