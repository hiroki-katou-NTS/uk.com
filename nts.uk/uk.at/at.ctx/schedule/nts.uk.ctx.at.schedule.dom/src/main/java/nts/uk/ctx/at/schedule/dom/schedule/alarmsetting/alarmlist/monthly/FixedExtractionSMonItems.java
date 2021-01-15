package nts.uk.ctx.at.schedule.dom.schedule.alarmsetting.alarmlist.monthly;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.schedule.dom.schedule.alarmsetting.alarmlist.daily.AlarmCheckClassification;
import nts.uk.ctx.at.schedule.dom.schedule.alarmsetting.alarmlist.daily.ErrorAlarmMessage;
import nts.uk.ctx.at.schedule.dom.schedule.alarmsetting.alarmlist.daily.NameAlarmExtractCond;

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
