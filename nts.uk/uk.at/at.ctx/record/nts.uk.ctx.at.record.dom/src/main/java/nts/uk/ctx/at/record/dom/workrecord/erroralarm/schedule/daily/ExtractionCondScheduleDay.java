package nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.daily;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.erroralarm.ErrorAlarmMessage;

import java.util.Optional;

/**
 * スケジュール日次の任意抽出条件
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ExtractionCondScheduleDay extends AggregateRoot {

    // ID
    private String errorAlarmId;

    // スケジュール日次のチェック条件
    private ScheduleCheckCond scheduleCheckCond;

    // チェック項目種類
    private DaiCheckItemType checkItemType;

    // 並び順
    private int sortOrder;

    // 使用区分
    private boolean isUse;

    // 名称
    private NameAlarmExtractCond name;

    // 対象とする勤務種類
    private RangeToCheck targetWrkType;

    // メッセージ
    private Optional<ErrorAlarmMessage> errorAlarmMessage;
}
