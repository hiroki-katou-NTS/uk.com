package nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision;

import nts.arc.time.GeneralDateTime;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.Year;

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
     * [4] 承認すべき申請を取得する
     */
    List<SpecialProvisionsOfAgreement> getByApproverSID(String approverSID, GeneralDateTime startDateTime, GeneralDateTime endDateTime, List<ApprovalStatus> listApprove);

    /**
     * [5] 確認すべき申請を取得する
     */
    List<SpecialProvisionsOfAgreement> getByConfirmerSID(String confirmerSID, GeneralDateTime startDateTime, GeneralDateTime endDateTime, List<ApprovalStatus> listApprove);

    /**
     * [6] get applicationID
     */
    Optional<SpecialProvisionsOfAgreement> getByAppId(String applicationID);

    /**
     * [7] 1ヶ月申請を取得する
     */
    Optional<SpecialProvisionsOfAgreement> getByYearMonth(String applicantsSID, YearMonth yearMonth);

    /**
     * [8] 1年間申請を取得する
     */
    Optional<SpecialProvisionsOfAgreement> getByYear(String applicantsSID, Year year);

    /**
     * [9] get
     */
    List<SpecialProvisionsOfAgreement> getByPersonSID(String enteredPersonSID, GeneralDateTime startDateTime, GeneralDateTime endDateTime, List<ApprovalStatus> listApprove);

    /**
     * [10] get
     */
    List<SpecialProvisionsOfAgreement> getBySID(String employeeId, GeneralDateTime startDateTime, GeneralDateTime endDateTime, List<ApprovalStatus> listApprove);

}
