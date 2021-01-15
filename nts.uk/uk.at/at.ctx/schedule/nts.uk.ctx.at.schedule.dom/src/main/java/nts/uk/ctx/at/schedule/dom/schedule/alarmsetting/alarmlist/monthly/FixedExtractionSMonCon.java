package nts.uk.ctx.at.schedule.dom.schedule.alarmsetting.alarmlist.monthly;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.schedule.dom.schedule.alarmsetting.alarmlist.daily.ErrorAlarmMessage;

import java.util.Optional;

/**
 * AggregateRoot: スケジュール日次の固有抽出条件
 *
 */
@Getter
@AllArgsConstructor
public class FixedExtractionSMonCon extends AggregateRoot {
    /**
     * 職場のエラーアラームチェックID
     */
    private String errorAlarmWorkplaceId;

    /**
     * No
     */
    private FixedCheckSMonItems fixedCheckSMonItems;

    /**
     *メッセージ
     */
    private Optional<ErrorAlarmMessage> messageDisp;

    /**使用区分*/
    private boolean useAtr;

}
