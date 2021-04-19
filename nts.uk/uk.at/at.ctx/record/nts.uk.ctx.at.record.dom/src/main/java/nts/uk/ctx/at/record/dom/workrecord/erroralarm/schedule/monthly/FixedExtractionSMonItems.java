package nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.monthly;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.daily.AlarmCheckClassification;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.daily.NameAlarmExtractCond;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.erroralarm.ErrorAlarmMessage;

import java.util.Optional;

/**
 * AggregateRoot: スケジュール月次の固有抽出項目
 *
 */
@Getter
@AllArgsConstructor
public class FixedExtractionSMonItems extends AggregateRoot {
    /**
     * No
     */
    private FixedCheckSMonItems fixedCheckSMonItems;

    /**
     * アラームチェック区分
     */
    private AlarmCheckClassification alarmCheckCls;

    /**
     * 日別チェック名称
     */
    private NameAlarmExtractCond dailyCheckName;

    /**
     * 初期メッセージ
     */
    private Optional<ErrorAlarmMessage> initMsg;

}
