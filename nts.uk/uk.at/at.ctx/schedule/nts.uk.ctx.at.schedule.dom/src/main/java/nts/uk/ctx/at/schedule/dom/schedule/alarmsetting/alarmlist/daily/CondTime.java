package nts.uk.ctx.at.schedule.dom.schedule.alarmsetting.alarmlist.daily;

import nts.uk.ctx.at.shared.dom.alarmList.attendanceitem.CheckedCondition;

import java.util.List;

/**
 * 時間のチェック条件
 */
public class CondTime implements  ScheduleCheckCond{
    //チェック条件
    CheckedCondition checkedCondition;
    // チェック種類
    private CheckTimeType checkTimeType;

    // 勤務種類コード
    private List<String> wrkTypeCds;
}
