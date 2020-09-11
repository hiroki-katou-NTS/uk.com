package nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;

import java.util.List;

/**
 * 36協定特別条項の適用申請
 * @author quang.nh1
 */
@Getter
@AllArgsConstructor
public class SpecialProvisionsOfAgreement extends AggregateRoot {

    /** The company id. */
    private String companyId;

    /** 申請ID */
    private final String applicationID;

    /** 入力者*/
    private final String enteredPersonSID;

    /** 入力日付 */
    private final GeneralDate inputDate;

    /** 申請対象者*/
    private final String applicantsSID;

    /** 申請時間*/
    private final ApplicationTime applicationTime;

    /** 36協定申請理由*/
    private ReasonsForAgreement reasonsForAgreement;

    /** 承認者リスト*/
    private List<String> listApproverSID;

    /** 画面表示情報*/
    private ScreenDisplayInfo screenDisplayInfo;

    /** 確認状況詳細*/
    private List<ConfirmationStatus> confirmationStatus;

}
