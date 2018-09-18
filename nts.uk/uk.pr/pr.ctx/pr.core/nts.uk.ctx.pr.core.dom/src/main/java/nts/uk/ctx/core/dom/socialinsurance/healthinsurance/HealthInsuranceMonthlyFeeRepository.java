package nts.uk.ctx.core.dom.socialinsurance.healthinsurance;

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

    /**
     * Add or update 健康保険月額保険料額
     * @param domain HealthInsuranceMonthlyFee
     */
    void addOrUpdate(HealthInsuranceMonthlyFee domain);

    void deleteByHistoryIds(List<String> historyIds);
}