package nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import org.eclipse.persistence.internal.xr.ValueObject;

import java.util.Optional;

/**
 * 承認状況詳細
 *
 * @author quang.nh1
 */
@Getter
@Setter
@AllArgsConstructor
public class ApprovalStatusDetails extends ValueObject {

    /**
     * 承認状態
     */
    private ApprovalStatus approvalStatus;

    /**
     * 承認者
     */
    private Optional<String> approveSID;

    /**
     * 承認コメント
     */
    private Optional<AgreementApprovalComments> approvalComment;

    /**
     * 承認日
     */
    private Optional<GeneralDate> approvalDate;

    /**
     * [C-1] 未承認
     */
    public static ApprovalStatusDetails create(ApprovalStatus status, Optional<String> approveSID, Optional<AgreementApprovalComments> approvalComment, Optional<GeneralDate> date) {

        return new ApprovalStatusDetails(status, approveSID, approvalComment, date);
    }

}
