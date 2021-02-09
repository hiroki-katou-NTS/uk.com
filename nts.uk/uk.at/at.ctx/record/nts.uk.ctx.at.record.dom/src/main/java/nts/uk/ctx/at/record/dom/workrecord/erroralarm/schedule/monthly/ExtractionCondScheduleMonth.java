package nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.monthly;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem.CheckedCondition;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.daily.NameAlarmExtractCond;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.erroralarm.ErrorAlarmMessage;

import java.util.Optional;

/**
 * スケジュール月次の任意抽出条件
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ExtractionCondScheduleMonth extends AggregateRoot {

    // ID
    private String errorAlarmId;

    // スケジュールチェック条件
    private ScheduleMonCheckCond scheCheckConditions;

    // チェック条件
    private CheckedCondition checkConditions;

    // チェック項目の種類
    private MonCheckItemType checkItemType;

    // 並び順
    private int sortOrder;

    // 使用区分
    private boolean isUse;

    // 名称
    private NameAlarmExtractCond name;

    // メッセージ
    private Optional<ErrorAlarmMessage> errorAlarmMessage;
}
