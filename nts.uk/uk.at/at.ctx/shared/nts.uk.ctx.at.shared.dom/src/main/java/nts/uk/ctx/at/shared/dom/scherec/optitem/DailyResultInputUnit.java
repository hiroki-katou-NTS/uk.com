package nts.uk.ctx.at.shared.dom.scherec.optitem;

import lombok.Value;

import java.util.Optional;

/**
 * 日別実績の入力単位
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared(勤務予定、勤務実績).任意項目.日別実績の入力単位
 */
@Value
public class DailyResultInputUnit {
    // 時間項目の入力単位
    private Optional<TimeItemInputUnit> timeItemInputUnit;

    // 回数項目の入力単位
    private Optional<NumberItemInputUnit> numberItemInputUnit;

    // 金額項目の入力単位
    private Optional<AmountItemInputUnit> amountItemInputUnit;
}
