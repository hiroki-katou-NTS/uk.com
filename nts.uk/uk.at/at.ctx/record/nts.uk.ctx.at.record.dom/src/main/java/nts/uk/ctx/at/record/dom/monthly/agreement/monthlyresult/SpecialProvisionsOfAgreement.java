package nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.hourspermonth.ErrorTimeInMonth;
import nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.hoursperyear.ErrorTimeInYear;

import java.util.List;
import java.util.Optional;

/**
 * 36協定特別条項の適用申請
 *
 * @author quang.nh1
 */
@Getter
@AllArgsConstructor
public class SpecialProvisionsOfAgreement extends AggregateRoot {

    /**
     * The company id.
     */
    private String companyId;

    /**
     * 申請ID
     */
    private final String applicationID;

    /**
     * 入力者
     */
    private final String enteredPersonSID;

    /**
     * 入力日付
     */
    private final GeneralDate inputDate;

    /**
     * 申請対象者
     */
    private final String applicantsSID;

    /**
     * 申請時間
     */
    private final ApplicationTime applicationTime;

    /**
     * 36協定申請理由
     */
    private ReasonsForAgreement reasonsForAgreement;

    /**
     * 承認者リスト
     */
    private final List<String> listApproverSID;

    /**
     * 承認状況詳細
     */
    private ApprovalStatusDetails approvalStatusDetails;

    /**
     * 確認状況詳細
     */
    private List<ConfirmationStatusDetails> confirmationStatusDetails;

    /**
     * 画面表示情報
     */
    private ScreenDisplayInfo screenDisplayInfo;

    public SpecialProvisionsOfAgreement(String applicationID, String enteredPersonSID, GeneralDate inputDate,
                                        String applicantsSID, ApplicationTime applicationTime, ReasonsForAgreement reasonsForAgreement,
                                        List<String> listApproverSID, ApprovalStatusDetails confirmationStatus,
                                        List<ConfirmationStatusDetails> confirmationStatuses, ScreenDisplayInfo screenDisplayInfo) {

        this.applicationID = applicationID;
        this.enteredPersonSID = enteredPersonSID;
        this.inputDate = inputDate;
        this.applicantsSID = applicantsSID;
        this.applicationTime = applicationTime;
        this.reasonsForAgreement = reasonsForAgreement;
        this.listApproverSID = listApproverSID;
        this.approvalStatusDetails = confirmationStatus;
        this.confirmationStatusDetails = confirmationStatuses;
        this.screenDisplayInfo = screenDisplayInfo;
    }


    /**
     * [prv-1] 承認確認状況をクリアする
     */
    private void clearApprovalConfirm() {
        if (this.approvalStatusDetails.getApprovalStatus() != ApprovalStatus.UNAPPROVED)
            this.approvalStatusDetails.setApprovalStatus(ApprovalStatus.UNAPPROVED);
        this.confirmationStatusDetails.forEach(x -> {
            x.setConfirmationStatusEnum(ConfirmationStatus.UNCONFIRMED);
            x.setConfirmDate(Optional.empty());
        });
    }

    /**
     * [1] 申請を承認する
     */
    public void approveApplication(String authorizerSID, ApprovalStatus approvalStatus, Optional<AgreementApprovalComments> approvalComment) {
        this.approvalStatusDetails.setApprovalStatus(approvalStatus);
        this.approvalStatusDetails.setAuthorizerSID(Optional.of(authorizerSID));
        this.approvalStatusDetails.setApprovalComment(approvalComment);
        this.approvalStatusDetails.setApprovalDate(Optional.of(GeneralDate.today()));
    }

    /**
     * [2] 申請を確認する
     */
    public void confirmApplication(String confirmerSID, ConfirmationStatus confirmationStatusEnum) {
        this.confirmationStatusDetails.stream().filter(x -> x.getConfirmerSID().equals(confirmerSID)).forEach(c -> {
            c.setConfirmationStatusEnum(confirmationStatusEnum);
            c.setConfirmDate(Optional.of(GeneralDate.today()));
        });
    }

    /**
     * [3] 1ヶ月の申請時間を変更する
     */
    public void changeApplicationOneMonth(ErrorTimeInMonth errorTimeInMonth, ReasonsForAgreement reasonsForAgreement) {
        this.reasonsForAgreement = reasonsForAgreement;
        if (this.applicationTime.getOneMonthTime().isPresent())
            this.applicationTime.getOneMonthTime().get().setErrorTimeInMonth(errorTimeInMonth);
    }

    /**
     * [4] 年間の申請時間を変更する
     */
    public void changeApplicationYear(ErrorTimeInYear errorTimeInYear, ReasonsForAgreement reasonsForAgreement) {
        this.reasonsForAgreement = reasonsForAgreement;
        if (this.applicationTime.getOneYearTime().isPresent())
            this.applicationTime.getOneYearTime().get().setErrorTimeInYear(errorTimeInYear);
    }

}
