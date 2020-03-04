package nts.uk.ctx.pr.core.dom.wageprovision.statementitem.timeitemset;

import lombok.Getter;

import java.math.BigDecimal;

/**
 * 明細時間回数エラーアラーム範囲設定
 */
@Getter
public class DetailTimeErrorAlarmRangeSetting {

    /**
     * 上限設定
     */
    private DetailTimeErrorAlarmValueSetting upperLimitSetting;

    /**
     * 下限設定
     */
    private DetailTimeErrorAlarmValueSetting lowerLimitSetting;

    public DetailTimeErrorAlarmRangeSetting(int upperValueSettingAtr, BigDecimal upperTimesValue, Integer upperTimeValue,
                                            int lowerValueSettingAtr, BigDecimal lowerTimesValue, Integer lowerTimeValue) {
        this.upperLimitSetting = new DetailTimeErrorAlarmValueSetting(upperValueSettingAtr, upperTimesValue, upperTimeValue);
        this.lowerLimitSetting = new DetailTimeErrorAlarmValueSetting(lowerValueSettingAtr, lowerTimesValue, lowerTimeValue);
    }
}
