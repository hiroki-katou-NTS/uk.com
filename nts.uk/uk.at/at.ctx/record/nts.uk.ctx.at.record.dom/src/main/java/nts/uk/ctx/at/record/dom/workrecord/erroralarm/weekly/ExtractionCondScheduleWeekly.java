package nts.uk.ctx.at.record.dom.workrecord.erroralarm.weekly;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem.CheckedCondition;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.ErrorAlarmWorkRecordName;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.erroralarm.ErrorAlarmMessage;

import java.util.Optional;

/**
 * 週別実績の任意抽出条件
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ExtractionCondScheduleWeekly extends AggregateRoot {

    // ID
    private String errorAlarmId;

    // チェック条件
    private CheckedCondition checkConditions;

    // チェック項目の種類
    private WeeklyCheckItemType checkItemType;

    // 並び順
    private int sortOrder;

    // 使用区分
    private boolean isUse;

    // 名称
    private ErrorAlarmWorkRecordName name;

    // メッセージ
    private Optional<ErrorAlarmMessage> errorAlarmMessage;

    // 連続期間
    private Optional<ContinuousPeriod> continuousPeriod;
}
