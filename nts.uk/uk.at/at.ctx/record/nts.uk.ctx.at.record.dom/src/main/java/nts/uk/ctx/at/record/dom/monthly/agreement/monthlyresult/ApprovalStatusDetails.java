package nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;

import java.util.Optional;

/**
 * 承認状況詳細
 * @author quang.nh1
 */
@Getter
@Setter
@AllArgsConstructor
public class ApprovalStatusDetails {

    /** 確認状態 */
    private ApprovalStatus approvalStatus;

    /** 承認コメント */
    private Optional<AgreementApprovalComments> approvalComment;

    /** 承認日 */
    private Optional<GeneralDate> approvalDate;

    /** 承認者*/
    private Optional<String> approveSID;




}
