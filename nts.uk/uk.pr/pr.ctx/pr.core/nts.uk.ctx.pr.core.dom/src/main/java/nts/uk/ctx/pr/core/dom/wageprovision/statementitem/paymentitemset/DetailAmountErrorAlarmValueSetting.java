package nts.uk.ctx.pr.core.dom.wageprovision.statementitem.paymentitemset;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.pr.core.dom.monthlysalary.payrolldata.DetailAmountType;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * 明細金額エラーアラーム値設定
 */
@Getter
public class DetailAmountErrorAlarmValueSetting {

    /**
     * 値設定区分
     */
    private UseRangeAtr valueSettingAtr;

    /**
     * 範囲値
     */
    private Optional<DetailAmountType> rangeValue;

    public DetailAmountErrorAlarmValueSetting(int valueSettingAtr, BigDecimal rangeValue) {
        this.valueSettingAtr = EnumAdaptor.valueOf(valueSettingAtr, UseRangeAtr.class);
        this.rangeValue = rangeValue == null ? Optional.empty() : Optional.of(new DetailAmountType(rangeValue));
    }
}
