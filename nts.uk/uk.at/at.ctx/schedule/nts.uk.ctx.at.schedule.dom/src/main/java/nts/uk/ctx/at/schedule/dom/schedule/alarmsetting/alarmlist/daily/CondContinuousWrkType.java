package nts.uk.ctx.at.schedule.dom.schedule.alarmsetting.alarmlist.daily;

import nts.arc.time.calendar.period.DatePeriod;

import java.util.List;

/**
 * 連続勤務種類の抽出条件
 */
public class CondContinuousWrkType  implements  ScheduleCheckCond{
    // 勤務種類コード
    private List<String> wrkTypeCds;

    // 連続期間
    private DatePeriod period;
}
