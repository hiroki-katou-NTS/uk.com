package nts.uk.ctx.core.dom.socialinsurance.healthinsurance;

import java.util.Optional;

/**
 * 健康保険報酬月額範囲
 */
public interface MonthlyHealthInsuranceCompensationRepository {
    /**
     * Get 健康保険報酬月額範囲 by id
     *
     * @param targetStartYm 年月開始
     * @param targetEndYm   年月終了
     * @return Optional<MonthlyHealthInsuranceCompensation>
     */
    Optional<MonthlyHealthInsuranceCompensation> getMonthlyHealthInsuranceCompensationById(int targetStartYm, int targetEndYm);
    Optional<MonthlyHealthInsuranceCompensation> findByDate(int date);
}
