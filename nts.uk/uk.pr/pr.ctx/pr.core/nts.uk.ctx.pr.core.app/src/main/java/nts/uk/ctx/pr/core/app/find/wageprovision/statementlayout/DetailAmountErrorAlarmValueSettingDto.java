package nts.uk.ctx.pr.core.app.find.wageprovision.statementlayout;

import lombok.Value;
import nts.arc.primitive.PrimitiveValueBase;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.paymentitemset.DetailAmountErrorAlarmValueSetting;

import java.math.BigDecimal;

@Value
public class DetailAmountErrorAlarmValueSettingDto {
    /**
     * 値設定区分
     */
    private int valueSettingAtr;

    /**
     * 範囲値
     */
    private BigDecimal rangeValue;

    public DetailAmountErrorAlarmValueSettingDto(DetailAmountErrorAlarmValueSetting domain) {
        this.valueSettingAtr = domain.getValueSettingAtr().value;
        this.rangeValue = domain.getRangeValue().map(PrimitiveValueBase::v).orElse(null);
    }
}
