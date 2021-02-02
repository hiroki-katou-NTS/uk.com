package nts.uk.ctx.at.schedule.dom.schedule.alarmsetting.alarmlist.daily;

import nts.uk.ctx.at.shared.dom.alarmList.attendanceitem.CheckedCondition;
import nts.uk.ctx.at.shared.dom.alarmList.primitivevalue.ContinuousPeriod;

import java.util.List;

/**
 * 連続時間のチェック条件
 */
public class CondContinuousTime implements  ScheduleCheckCond{
    //チェック条件
    CheckedCondition checkedCondition;
    // 勤務種類コード
    private List<String> wrkTypeCds;

    // 連続期間
    private ContinuousPeriod period;
}
