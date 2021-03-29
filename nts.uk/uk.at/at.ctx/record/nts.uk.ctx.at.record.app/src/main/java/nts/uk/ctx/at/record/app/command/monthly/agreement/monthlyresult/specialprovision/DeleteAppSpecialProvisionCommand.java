package nts.uk.ctx.at.record.app.command.monthly.agreement.monthlyresult.specialprovision;

import lombok.Value;

/**
 * 36協定特別条項の適用申請を削除する
 *
 * @author Le Huu Dat
 */
@Value
public class DeleteAppSpecialProvisionCommand {
    /**
     * 申請ID
     */
    private String applicantId;
}
