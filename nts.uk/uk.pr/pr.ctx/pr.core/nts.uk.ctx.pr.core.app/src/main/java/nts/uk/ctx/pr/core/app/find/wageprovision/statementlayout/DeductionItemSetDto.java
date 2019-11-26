package nts.uk.ctx.pr.core.app.find.wageprovision.statementlayout;

import lombok.Value;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.deductionitemset.DeductionItemSet;

/**
 * 控除項目設定
 */
@Value
public class DeductionItemSetDto {
    /**
     * 控除項目区分
     */
    private int deductionItemAtr;
    /**
     * 内訳項目利用区分
     */
    private int breakdownItemUseAtr;

    private DetailAmountErrorAlarmRangeSettingDto errorRangeSetting;

    private DetailAmountErrorAlarmRangeSettingDto alarmRangeSetting;

    public DeductionItemSetDto(DeductionItemSet domain) {
        this.deductionItemAtr = domain.getDeductionItemAtr().value;
        this.breakdownItemUseAtr = domain.getBreakdownItemUseAtr().value;
        this.errorRangeSetting = new DetailAmountErrorAlarmRangeSettingDto(domain.getErrorRangeSetting());
        this.alarmRangeSetting = new DetailAmountErrorAlarmRangeSettingDto(domain.getAlarmRangeSetting());
    }
}
