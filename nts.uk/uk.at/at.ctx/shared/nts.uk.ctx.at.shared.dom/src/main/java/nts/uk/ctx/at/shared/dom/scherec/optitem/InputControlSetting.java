package nts.uk.ctx.at.shared.dom.scherec.optitem;

import lombok.Value;

import java.util.Optional;

/**
 * 入力制御設定
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared(勤務予定、勤務実績).任意項目.入力制御設定
 */
@Value
public class InputControlSetting {
    // チェックボックスで入力する
    private boolean inputWithCheckbox;

    // 計算結果の範囲
    private CalcResultRange calcResultRange;

    // 日別実績の入力単位
    private Optional<DailyResultInputUnit> dailyInputUnit;
}
