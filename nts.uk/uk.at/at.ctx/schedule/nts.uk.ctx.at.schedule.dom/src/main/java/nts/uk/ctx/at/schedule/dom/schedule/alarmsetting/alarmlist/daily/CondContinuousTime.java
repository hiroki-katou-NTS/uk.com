package nts.uk.ctx.at.schedule.dom.schedule.alarmsetting.alarmlist.daily;

import nts.arc.time.calendar.period.DatePeriod;

import java.util.List;

/**
 * 連続時間のチェック条件
 */
public class CondContinuousTime implements  ScheduleCheckCond{
    // 連続時間のチェック条件
    // CheckCond

    // 勤務種類コード
    private List<String> wrkTypeCds;

    // 連続期間
    private DatePeriod period;
}
