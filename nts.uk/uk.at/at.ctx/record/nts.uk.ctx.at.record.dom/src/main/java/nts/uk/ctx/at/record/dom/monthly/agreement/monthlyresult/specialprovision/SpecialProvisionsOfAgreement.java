package nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.onemonth.OneMonthErrorAlarmTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.oneyear.OneYearErrorAlarmTime;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 36協定特別条項の適用申請
 *
 * @author quang.nh1
 */
@Getter
@Setter
@AllArgsConstructor
public class SpecialProvisionsOfAgreement extends AggregateRoot {

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
    private final GeneralDateTime inputDate;

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

    /**
     * [C-1] 新規申請作成
     */
    public static SpecialProvisionsOfAgreement create(String enteredPersonSID, String applicantsSID, ApplicationTime applicationTime, ReasonsForAgreement reasonsForAgreement,
                                                      List<String> listApproverSID, List<String> listConfirmSID, ScreenDisplayInfo screenDisplayInfo) {

        ApprovalStatusDetails approvalStatusDetails = ApprovalStatusDetails.create(ApprovalStatus.UNAPPROVED, Optional.empty(), Optional.empty(), Optional.empty());
        List<ConfirmationStatusDetails> confirmationStatusDetails = new ArrayList<>();
        listConfirmSID.forEach(sid -> {
            confirmationStatusDetails.add(ConfirmationStatusDetails.create(sid));
        });

        return new SpecialProvisionsOfAgreement(
                IdentifierUtil.randomUniqueId(),
                enteredPersonSID,
                GeneralDateTime.now(),
                applicantsSID,
                applicationTime,
                reasonsForAgreement,
                listApproverSID,
                approvalStatusDetails,
                confirmationStatusDetails,
                screenDisplayInfo
        );
    }


    /**
     * [prv-1] 承認確認状況をクリアする
     */
    private void clearApprovalConfirm() {
        if (this.approvalStatusDetails.getApprovalStatus() != ApprovalStatus.UNAPPROVED) {
            this.approvalStatusDetails.setApprovalStatus(ApprovalStatus.UNAPPROVED);
            this.confirmationStatusDetails.forEach(x -> {
                x.setConfirmationStatus(ConfirmationStatus.UNCONFIRMED);
                x.setConfirmDate(Optional.empty());
            });
        }
    }

    /**
     * [1] 申請を承認する
     */
    public void approveApplication(String authorizerSID, ApprovalStatus approvalStatus, Optional<AgreementApprovalComments> approvalComment) {
        this.approvalStatusDetails.setApprovalStatus(approvalStatus);
        this.approvalStatusDetails.setApproveSID(Optional.of(authorizerSID));
        this.approvalStatusDetails.setApprovalComment(approvalComment);
        this.approvalStatusDetails.setApprovalDate(Optional.of(GeneralDate.today()));
    }

    /**
     * [2] 申請を確認する
     */
    public void confirmApplication(String confirmerSID, ConfirmationStatus confirmationStatus) {
        this.confirmationStatusDetails.stream().filter(x -> x.getConfirmerSID().equals(confirmerSID)).forEach(c -> {
            c.setConfirmationStatus(confirmationStatus);
            c.setConfirmDate(Optional.of(GeneralDate.today()));
        });
    }

    /**
     * [3] 1ヶ月の申請時間を変更する
     */
    public void changeApplicationOneMonth(OneMonthErrorAlarmTime errorTimeInMonth, ReasonsForAgreement reasonsForAgreement) {
        this.reasonsForAgreement = reasonsForAgreement;
        if (this.applicationTime.getOneMonthTime().isPresent())
            this.applicationTime.getOneMonthTime().get().setErrorTimeInMonth(errorTimeInMonth);

        //[prv-1] 承認確認状況をクリアする
        clearApprovalConfirm();
    }

    /**
     * [4] 年間の申請時間を変更する
     */
    public void changeApplicationYear(OneYearErrorAlarmTime errorTimeInYear, ReasonsForAgreement reasonsForAgreement) {
        this.reasonsForAgreement = reasonsForAgreement;
        if (this.applicationTime.getOneYearTime().isPresent())
            this.applicationTime.getOneYearTime().get().setErrorTimeInYear(errorTimeInYear);

        //[prv-1] 承認確認状況をクリアする
        clearApprovalConfirm();
    }

}
