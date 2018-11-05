package nts.uk.ctx.pr.core.app.find.wageprovision.statementlayout;

import lombok.Value;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.paymentitemset.PaymentItemSet;

@Value
public class PaymentItemSetDto {
    /**
     * 課税区分
     */
    private int taxAtr;
    /**
     * 限度金額設定.限度金額
     */
    private Long limitAmount;
    /**
     * 社会保険区分
     */
    private int socialInsuranceCategory;
    /**
     * 労働保険区分
     */
    private int laborInsuranceCategory;
    /**
     * 固定的賃金
     */
    private Integer everyoneEqualSet;
    /**
     * 平均賃金区分
     */
    private int averageWageAtr;

    private DetailAmountErrorAlarmRangeSettingDto errorRangeSetting;

    private DetailAmountErrorAlarmRangeSettingDto alarmRangeSetting;

    public PaymentItemSetDto(PaymentItemSet domain) {
        this.taxAtr = domain.getTaxAtr().value;
        this.limitAmount = domain.getLimitAmountSetting().getLimitAmount().map(x -> x.v()).orElse(null);
        this.socialInsuranceCategory = domain.getSocialInsuranceCategory().value;
        this.laborInsuranceCategory = domain.getLaborInsuranceCategory().value;
        this.everyoneEqualSet = domain.getFixedWage().getEveryoneEqualSet().map(x -> x.value).orElse(null);
        this.averageWageAtr = domain.getAverageWageAtr().value;
        this.errorRangeSetting = new DetailAmountErrorAlarmRangeSettingDto(domain.getErrorRangeSetting());
        this.alarmRangeSetting = new DetailAmountErrorAlarmRangeSettingDto(domain.getAlarmRangeSetting());
    }
}
