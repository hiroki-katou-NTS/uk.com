package nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.daily;

import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem.CheckedCondition;

import java.util.List;

/**
 * 時間のチェック条件
 */
public class CondTime implements  ScheduleCheckCond{
    //チェック条件
    private CheckedCondition checkedCondition;
    // チェック種類
    private CheckTimeType checkTimeType;

    // 勤務種類コード
    private List<String> wrkTypeCds;
}
