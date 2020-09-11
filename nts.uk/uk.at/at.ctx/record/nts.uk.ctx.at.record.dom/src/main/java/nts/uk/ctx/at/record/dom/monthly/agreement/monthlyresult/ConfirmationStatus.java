package nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult;

import nts.arc.time.GeneralDate;

import java.util.Optional;

/**
 * 承認状況詳細
 * @author quang.nh1
 */
public class ConfirmationStatus {

    /** 確認状態 */
    private ConfirmationStatusEnum confirmationStatusEnum;

    /** 承認コメント */
    private Optional<AgreementApprovalComments> approvalComment;

    /** 承認日 */
    private Optional<GeneralDate> approvalDate;

    /** 承認者*/
    private Optional<String> authorizerSID;




}
