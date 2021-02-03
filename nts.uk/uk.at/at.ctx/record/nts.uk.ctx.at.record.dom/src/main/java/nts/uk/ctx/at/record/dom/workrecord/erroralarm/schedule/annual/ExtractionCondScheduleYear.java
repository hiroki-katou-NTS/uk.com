package nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.annual;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.daily.NameAlarmExtractCond;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.erroralarm.ErrorAlarmMessage;
import nts.uk.ctx.at.shared.dom.workrecord.alarm.attendanceitemconditions.CheckConditions;

import java.util.Optional;

/**
 * スケジュール年間の任意抽出条件
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ExtractionCondScheduleYear extends AggregateRoot {

    // ID
    private String errorAlarmId;

    // スケジュール年間チェック条件
    private ScheduleYearCheckCond scheCheckConditions;

    // チェック条件
    private CheckConditions checkConditions;

    // チェック項目の種類
    private YearCheckItemType checkItemType;

    // 並び順
    private int sortOrder;

    // 使用区分
    private boolean isUse;

    // 名称
    private NameAlarmExtractCond name;

    // メッセージ
    private Optional<ErrorAlarmMessage> errorAlarmMessage;
}
