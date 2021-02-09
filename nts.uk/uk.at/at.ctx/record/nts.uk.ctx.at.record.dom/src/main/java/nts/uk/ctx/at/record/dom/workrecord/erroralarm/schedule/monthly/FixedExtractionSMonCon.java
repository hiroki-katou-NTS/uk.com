package nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.monthly;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.erroralarm.ErrorAlarmMessage;

import java.util.Optional;

/**
 * AggregateRoot: スケジュール月次の固有抽出条件
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
