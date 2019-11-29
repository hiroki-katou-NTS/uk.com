package nts.uk.ctx.pr.core.app.find.wageprovision.statementlayout;

import lombok.Value;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.paymentitemset.DetailAmountErrorAlarmRangeSetting;

@Value
public class DetailAmountErrorAlarmRangeSettingDto {
    private DetailAmountErrorAlarmValueSettingDto upperLimitSetting;
    private DetailAmountErrorAlarmValueSettingDto lowerLimitSetting;

    public DetailAmountErrorAlarmRangeSettingDto(DetailAmountErrorAlarmRangeSetting domain) {
        this.upperLimitSetting = new DetailAmountErrorAlarmValueSettingDto(domain.getUpperLimitSetting());
        this.lowerLimitSetting = new DetailAmountErrorAlarmValueSettingDto(domain.getLowerLimitSetting());
    }
}
