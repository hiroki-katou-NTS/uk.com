package nts.uk.ctx.at.record.app.command.monthly.agreement.monthlyresult.specialprovision;

import lombok.Value;

/**
 * 36協定特別条項の適用申請の一括承認を行う（従業員代表）
 *
 * @author Le Huu Dat
 */
@Value
public class BulkApproveAppSpecialProvisionConfirmerCommand {
    /**
     * 申請ID
     */
    private String applicantId;
    /**
     * 確認者 (社員ID)
     */
    private String confirmerId;
}
