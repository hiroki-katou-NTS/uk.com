package nts.uk.ctx.at.schedule.dom.schedule.alarmsetting.alarmlist.weekly;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.schedule.dom.schedule.alarmsetting.alarmlist.daily.ErrorAlarmMessage;
import nts.uk.ctx.at.schedule.dom.schedule.alarmsetting.alarmlist.daily.NameAlarmExtractCond;
import nts.uk.ctx.at.shared.dom.workrecord.alarm.attendanceitemconditions.CheckConditions;

import java.util.Optional;

/**
 * スケジュール年間の任意抽出条件
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ExtractionCondScheduleWeekly extends AggregateRoot {

    // ID
    private String errorAlarmId;

    // チェック条件
    private CheckConditions checkConditions;

    // チェック項目の種類
    private WeeklyCheckItemType checkItemType;

    // 並び順
    private int sortOrder;

    // 使用区分
    private boolean isUse;

    // 名称
    private NameAlarmExtractCond name;

    // メッセージ
    private Optional<ErrorAlarmMessage> errorAlarmMessage;

    // 連続期間
    private Optional<ContinuousPeriod> continuousPeriod;
}
