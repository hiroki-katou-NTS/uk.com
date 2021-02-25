package nts.uk.ctx.at.record.app.command.monthly.agreement.monthlyresult.specialprovision;

import lombok.Value;

/**
 * 36協定特別条項の適用申請の登録を行う（年間）
 *
 * @author Le Huu Dat
 */
@Value
public class RegisterAppSpecialProvisionYearCommand {

    /**
     * 申請内容
     */
    AnnualAppContentCommand content;

    /**
     * 画面表示情報
     */
    ScreenDisplayInfoCommand screenInfo;

}
