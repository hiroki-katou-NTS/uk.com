package nts.uk.ctx.pr.core.app.find.wageprovision.statementlayout;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.paymentitemset.UseRangeAtr;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.timeitemset.DetailTimeErrorAlarmRangeSetting;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.timeitemset.DetailTimeErrorAlarmValueSetting;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.timeitemset.TimeValue;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.timeitemset.TimesValue;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * 明細時間回数エラーアラーム値設定
 */
@Getter
public class DetailTimeErrorAlarmValueSetDto {

    /**
     * 値設定区分
     */
    private int valueSettingAtr;

    /**
     * 明細範囲値（回数）
     */
    private BigDecimal timesValue;

    /**
     * 明細範囲値（時間）
     */
    private Integer timeValue;

    public DetailTimeErrorAlarmValueSetDto(DetailTimeErrorAlarmValueSetting domain) {
        this.valueSettingAtr = domain.getValueSettingAtr().value;
        this.timesValue = domain.getTimesValue().map(i->i.v()).orElse(null);
        this.timeValue = domain.getTimeValue().map(i->i.v()).orElse(null);
    }
}
