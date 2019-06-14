package nts.uk.ctx.pr.core.dom.wageprovision.wagetable;

import java.util.Optional;
import java.util.List;

/**
 * 賃金テーブル内容
 */
public interface WageTableContentRepository {

    List<WageTableContent> getAllWageTableContent();

    Optional<WageTableContent> getWageTableContentById(String historyId, String companyId, String wageTableCode);
    
    void addOrUpdate(WageTableContent domain, String companyId, String wageTableCode);

    void remove(String historyId, String companyId, String wageTableCode);

    List<WageTableQualification> getWageTableQualification(String historyId);

    List<WageTableQualification> getDefaultWageTableQualification();
    
    List<ElementsCombinationPaymentAmount> getWageTableContentByThirdDimension(String historyId, String companyId, String wageTableCode, String thirdMasterCode, Integer thirdFrameNumber);
    
    void updateListPayment(String historyId, String companyId, String wageTableCode, List<ElementsCombinationPaymentAmount> payments);
    
}
