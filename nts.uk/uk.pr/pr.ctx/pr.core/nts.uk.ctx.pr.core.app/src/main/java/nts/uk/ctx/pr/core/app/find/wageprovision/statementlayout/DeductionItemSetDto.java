package nts.uk.ctx.pr.core.app.find.wageprovision.statementlayout;

import lombok.Value;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.deductionitemset.DeductionItemSet;

/**
 * 控除項目設定
 */
@Value
public class DeductionItemSetDto {

    private DetailAmountErrorAlarmRangeSettingDto errorRangeSetting;

    private DetailAmountErrorAlarmRangeSettingDto alarmRangeSetting;

    public DeductionItemSetDto(DeductionItemSet domain) {
        this.errorRangeSetting = new DetailAmountErrorAlarmRangeSettingDto(domain.getErrorRangeSetting());
        this.alarmRangeSetting = new DetailAmountErrorAlarmRangeSettingDto(domain.getAlarmRangeSetting());
    }
}
