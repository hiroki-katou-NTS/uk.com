package nts.uk.ctx.at.record.app.command.monthly.agreement.monthlyresult.specialprovision;

import lombok.Value;

/**
 * 36協定特別条項の適用申請を更新登録する
 *
 * @author Le Huu Dat
 */
@Value
public class ApplyAppSpecialProvisionCommand {
    /**
     * ３６協定申請種類
     */
    private int typeAgreement;
    /**
     * 申請ID
     */
    private String applicantId;
    /**
     * １ヵ月時間OR年間時間
     */
    private int time;
    /**
     * 申請理由
     */
    private String reason;
}
