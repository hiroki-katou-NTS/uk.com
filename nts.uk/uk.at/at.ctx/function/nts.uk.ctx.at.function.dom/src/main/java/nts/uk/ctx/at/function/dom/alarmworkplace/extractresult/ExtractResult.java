package nts.uk.ctx.at.function.dom.alarmworkplace.extractresult;

import lombok.Getter;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.AlarmCheckConditionName;
import nts.uk.ctx.at.shared.dom.scherec.alarm.alarmlistactractionresult.AlarmValueDate;
import nts.uk.ctx.at.shared.dom.scherec.alarm.alarmlistactractionresult.AlarmValueMessage;
import nts.uk.ctx.at.shared.dom.scherec.alarm.alarmlistactractionresult.MessageDisplay;

import java.util.Optional;

/**
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.就業機能.アラーム_職場別.抽出結果
 * 抽出結果
 *
 * @author Le Huu Dat
 */
@Getter
public class ExtractResult {

    /**
     * アラーム値メッセージ
     */
    private AlarmValueMessage alarmValueMessage;

    /**
     * アラーム値日付
     */
    private AlarmValueDate alarmValueDate;

    /**
     * アラーム項目名
     */
    private AlarmCheckConditionName alarmItemName;

    /**
     * チェック対象値
     */
    private Optional<String> checkTargetValue;

    /**
     * コメント
     */
    private Optional<MessageDisplay> comment;

    /**
     * 職場ID
     */
    private String workplaceId;
}
