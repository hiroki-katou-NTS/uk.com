package nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision;

import nts.arc.time.GeneralDate;

import java.util.List;
import java.util.Optional;

public interface SpecialProvisionsOfAgreementRepo {

    /**
     * [1] Insert(36協定特別条項の適用申請)
     */
    void insert(SpecialProvisionsOfAgreement domain);

    /**
     * [2] Update(36協定特別条項の適用申請)
     */
    void update(SpecialProvisionsOfAgreement domain);

    /**
     * [3] Delete(36協定特別条項の適用申請)
     */
    void delete(SpecialProvisionsOfAgreement domain);

    /**
     * [4] get by approver
     */
    List<SpecialProvisionsOfAgreement> getByApproverSID(String approverSID, GeneralDate startDate, GeneralDate endDate, List<String> listApprove);

    /**
     * [4] get by confirmer
     */
    List<SpecialProvisionsOfAgreement> getByConfirmerSID(String confirmerSID, GeneralDate startDate, GeneralDate endDate, List<String> listApprove);

    /**
     * [4] get applicationID
     */
    Optional<SpecialProvisionsOfAgreement> getByAppId(String applicationID);

}
