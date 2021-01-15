package nts.uk.ctx.at.schedule.dom.schedule.alarmsetting.alarmlist.daily;

import nts.arc.time.calendar.period.DatePeriod;

import java.util.List;

/**
 * 連続時間のチェック条件
 */
public class CondContinuousTimeZone implements  ScheduleCheckCond{
    // 連続時間のチェック条件
    // CheckCond

    // 勤務種類コード
    private List<String> wrkTypeCds;

    // 就業時間帯コード
    private List<String> wrkTimeCds;

    // 連続期間
    private DatePeriod period;
}
