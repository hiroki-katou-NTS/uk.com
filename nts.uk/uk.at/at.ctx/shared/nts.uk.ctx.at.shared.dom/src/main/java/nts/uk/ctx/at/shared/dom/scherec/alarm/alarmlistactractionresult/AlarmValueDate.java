package nts.uk.ctx.at.shared.dom.scherec.alarm.alarmlistactractionresult;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;

/**
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.shared(勤務予定、勤務実績).アラーム.アラームリスト抽出結果
 * アラーム値日付
 *
 * @author Le Huu Dat
 */
@Getter
@AllArgsConstructor
public class AlarmValueDate {
    /**
     * 開始日
     */
    private int startDate;
    /**
     * 終了日
     */
    private Optional<Integer> endDate;
}
