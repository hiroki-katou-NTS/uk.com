package nts.uk.ctx.at.record.app.command.monthly.agreement.monthlyresult.specialprovision;

import lombok.Value;

/**
 * 36協定特別条項の適用申請の承認/否認を行う（36承認者）
 *
 * @author Le Huu Dat
 */
@Value
public class ApproveDenialAppSpecialProvisionApproverCommand {
    /**
     * 申請ID
     */
    private String applicantId;
    /**
     * 承認状態：承認
     */
    private int approvalStatus;
    /**
     * 承認者のコメント：承認コメント
     */
    private String approvalComment;
}
