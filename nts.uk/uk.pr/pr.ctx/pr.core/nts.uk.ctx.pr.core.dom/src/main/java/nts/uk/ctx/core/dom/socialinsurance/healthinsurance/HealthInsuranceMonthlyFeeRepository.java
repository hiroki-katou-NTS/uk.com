package nts.uk.ctx.core.dom.socialinsurance.healthinsurance;

import java.util.Optional;

import java.util.List;

/**
 * 健康保険月額保険料額
 */
public interface HealthInsuranceMonthlyFeeRepository {
    Optional<HealthInsuranceMonthlyFee> getHealthInsuranceMonthlyFeeById(String historyId);

	void deleteByHistoryIds(List<String> historyIds);
}