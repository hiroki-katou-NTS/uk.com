package nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.daily;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem.CheckedCondition;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.ContinuousPeriod;

import java.util.List;

/**
 * 連続時間のチェック条件
 */
@AllArgsConstructor
@Getter
public class CondContinuousTime implements  ScheduleCheckCond{
    //チェック条件
    private CheckedCondition checkedCondition;
    // 勤務種類コード
    private List<String> wrkTypeCds;

    // 連続期間
    private ContinuousPeriod period;
}
