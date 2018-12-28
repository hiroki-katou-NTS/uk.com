package nts.uk.ctx.pr.core.app.find.wageprovision.statementlayout;

import lombok.Getter;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.timeitemset.DetailTimeErrorAlarmRangeSetting;

import java.math.BigDecimal;

/**
 * 明細時間回数エラーアラーム範囲設定
 */
@Getter
public class DetailTimeErrorAlarmRangeSetDto {

    /**
     * 上限設定
     */
    private DetailTimeErrorAlarmValueSetDto upperLimitSetting;

    /**
     * 下限設定
     */
    private DetailTimeErrorAlarmValueSetDto lowerLimitSetting;

    public DetailTimeErrorAlarmRangeSetDto(DetailTimeErrorAlarmRangeSetting domain) {
        this.upperLimitSetting = new DetailTimeErrorAlarmValueSetDto(domain.getUpperLimitSetting());
        this.lowerLimitSetting = new DetailTimeErrorAlarmValueSetDto(domain.getLowerLimitSetting());
    }
}
