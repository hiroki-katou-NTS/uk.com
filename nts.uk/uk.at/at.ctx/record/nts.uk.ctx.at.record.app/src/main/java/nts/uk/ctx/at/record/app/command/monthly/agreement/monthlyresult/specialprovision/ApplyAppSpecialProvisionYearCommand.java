package nts.uk.ctx.at.record.app.command.monthly.agreement.monthlyresult.specialprovision;

import lombok.Value;

/**
 * 36協定特別条項の適用申請を更新登録する（年間）
 *
 * @author Le Huu Dat
 */
@Value
public class ApplyAppSpecialProvisionYearCommand {
    /**
     * 申請ID
     */
    private String applicantId;
    /**
     * 新しい上限時間: 年間
     */
    private int agrOneYearTime;
    /**
     * 申請理由
     */
    private String reason;
}
