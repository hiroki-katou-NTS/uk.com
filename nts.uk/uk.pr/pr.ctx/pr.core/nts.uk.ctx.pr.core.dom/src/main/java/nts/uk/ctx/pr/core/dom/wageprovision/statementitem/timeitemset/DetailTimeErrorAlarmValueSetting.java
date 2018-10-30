package nts.uk.ctx.pr.core.dom.wageprovision.statementitem.timeitemset;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.paymentitemset.UseRangeAtr;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * 明細時間回数エラーアラーム値設定
 */
@Getter
public class DetailTimeErrorAlarmValueSetting {

    /**
     * 値設定区分
     */
    private UseRangeAtr valueSettingAtr;

    /**
     * 明細範囲値（回数）
     */
    private Optional<TimesValue> timesValue;

    /**
     * 明細範囲値（時間）
     */
    private Optional<TimeValue> timeValue;

    public DetailTimeErrorAlarmValueSetting(int valueSettingAtr, BigDecimal timesValue, Integer timeValue) {
        this.valueSettingAtr = EnumAdaptor.valueOf(valueSettingAtr, UseRangeAtr.class);
        this.timesValue = timesValue == null ? Optional.empty() : Optional.of(new TimesValue(timesValue));
        this.timeValue = timeValue == null ? Optional.empty() : Optional.of(new TimeValue(timeValue));
    }
}
