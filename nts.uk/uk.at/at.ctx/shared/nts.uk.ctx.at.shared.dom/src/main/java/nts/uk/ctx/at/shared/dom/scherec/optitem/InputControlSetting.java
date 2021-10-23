package nts.uk.ctx.at.shared.dom.scherec.optitem;

import lombok.Value;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.DomainObject;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * 入力制御設定
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared(勤務予定、勤務実績).任意項目.入力制御設定
 */
@Value
public class InputControlSetting extends DomainObject {
    // チェックボックスで入力する
    private boolean inputWithCheckbox;

    // 計算結果の範囲
    private CalcResultRange calcResultRange;

    // 日別実績の入力単位
    private Optional<DailyResultInputUnit> dailyInputUnit;

    @Override
    public void validate() {
        super.validate();
        if (this.inputWithCheckbox) {
            if (!this.calcResultRange.getNumberRange().isPresent()
                    || !this.calcResultRange.getNumberRange().get().getDailyTimesRange().isPresent()
                    || !this.calcResultRange.getNumberRange().get().getDailyTimesRange().get().getLowerLimit().isPresent()
                    || this.calcResultRange.getNumberRange().get().getDailyTimesRange().get().getLowerLimit().get().v().compareTo(BigDecimal.ZERO) != 0
                    || !this.calcResultRange.getNumberRange().get().getDailyTimesRange().get().getUpperLimit().isPresent()
                    || this.calcResultRange.getNumberRange().get().getDailyTimesRange().get().getUpperLimit().get().v().compareTo(BigDecimal.ONE) != 0) {
                throw new BusinessException("Msg_2308");
            }
        }
    }
}
