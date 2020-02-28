package nts.uk.ctx.pr.core.dom.wageprovision.statementitem.paymentitemset;

import lombok.Getter;

import java.math.BigDecimal;

/**
 * 明細金額エラーアラーム範囲設定
 */
@Getter
public class DetailAmountErrorAlarmRangeSetting {

    /**
     * 上限設定
     */
    private DetailAmountErrorAlarmValueSetting upperLimitSetting;

    /**
     * 下限設定
     */
    private DetailAmountErrorAlarmValueSetting lowerLimitSetting;

    public DetailAmountErrorAlarmRangeSetting(int upperValueSettingAtr, BigDecimal upperRangeValue,
                                              int lowerValueSettingAtr, BigDecimal lowerRangeValue) {
        this.upperLimitSetting = new DetailAmountErrorAlarmValueSetting(upperValueSettingAtr, upperRangeValue);
        this.lowerLimitSetting = new DetailAmountErrorAlarmValueSetting(lowerValueSettingAtr, lowerRangeValue);
    }
}
