package nts.uk.ctx.core.dom.socialinsurance.healthinsurance;

import java.util.Optional;

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
    Optional<HealthInsuranceFeeRateHistory> getHealthInsuranceFeeRateHistoryByCid(String companyId, String officeCode);

    void deleteByCidAndCode(String companyId, String officeCode);
    
    void add (HealthInsuranceFeeRateHistory domain);
    
    void update (HealthInsuranceFeeRateHistory domain);
    
    void remove (HealthInsuranceFeeRateHistory domain);
}
