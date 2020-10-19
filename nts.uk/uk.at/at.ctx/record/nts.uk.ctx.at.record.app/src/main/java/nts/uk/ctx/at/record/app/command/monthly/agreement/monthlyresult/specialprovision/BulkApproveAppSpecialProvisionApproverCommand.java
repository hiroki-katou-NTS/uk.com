package nts.uk.ctx.at.record.app.command.monthly.agreement.monthlyresult.specialprovision;

import lombok.Value;

/**
 * 36協定特別条項の適用申請の一括承認を行う（36承認者）
 *
 * @author Le Huu Dat
 */
@Value
public class BulkApproveAppSpecialProvisionApproverCommand {
    /**
     * 申請ID
     */
    private String applicantId;
    /**
     * 承認者：社員ID
     */
    private String approverId;
    /**
     * 承認者のコメント：承認コメント
     */
    private String approvalComment;
}
