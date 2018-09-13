package nts.uk.ctx.core.dom.socialinsurance.healthinsurance;

import java.util.List;

/**
 * 健康保険料率履歴
 */
public interface HealthInsuranceFeeRateHistoryRepository {

    /**
     * Get 健康保険料率履歴 by cID, officeCode
     *
     * @param companyId  会社ID
     * @param officeCode 社会保険事業所コード
     * @return HealthInsuranceFeeRateHistory
     */
    HealthInsuranceFeeRateHistory getHealthInsuranceFeeRateHistoryByCid(String companyId, String officeCode);
}
