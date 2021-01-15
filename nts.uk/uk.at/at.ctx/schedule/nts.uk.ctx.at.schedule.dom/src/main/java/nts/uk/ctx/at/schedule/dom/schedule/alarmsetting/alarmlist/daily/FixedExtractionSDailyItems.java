package nts.uk.ctx.at.schedule.dom.schedule.alarmsetting.alarmlist.daily;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;

import java.util.Optional;

/**
 * AggregateRoot: スケジュール日次の固有抽出項目
 *
 */
@Getter
@AllArgsConstructor
public class FixedExtractionSDailyItems extends AggregateRoot {
    /**
     * No
     */
    private FixedCheckSDailyItems fixedCheckDayItems;

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
