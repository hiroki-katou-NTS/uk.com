package nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.extractresult;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.shared.dom.scherec.alarm.alarmlistactractionresult.AlarmValueDate;
import nts.uk.ctx.at.shared.dom.scherec.alarm.alarmlistactractionresult.AlarmValueMessage;
import nts.uk.ctx.at.shared.dom.scherec.alarm.alarmlistactractionresult.MessageDisplay;

import java.util.Optional;

/**
 * 抽出結果
 *
 * @author Le Huu Dat
 */
@Getter
@AllArgsConstructor
public class ExtractResultDto {

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
    private String alarmItemName;

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
