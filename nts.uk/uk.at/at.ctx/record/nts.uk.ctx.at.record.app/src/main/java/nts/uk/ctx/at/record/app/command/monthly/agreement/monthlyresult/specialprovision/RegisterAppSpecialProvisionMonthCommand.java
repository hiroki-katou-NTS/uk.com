package nts.uk.ctx.at.record.app.command.monthly.agreement.monthlyresult.specialprovision;

import lombok.Value;

/**
 * 36協定特別条項の適用申請の登録を行う（1ヶ月）
 *
 * @author Le Huu Dat
 */
@Value
public class RegisterAppSpecialProvisionMonthCommand {

    /**
     * 申請内容
     */
    MonthlyAppContentCommand content;

    /**
     * 画面表示情報
     */
    ScreenDisplayInfoCommand screenInfo;

}
