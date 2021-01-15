package nts.uk.ctx.at.schedule.dom.schedule.alarmsetting.alarmlist.daily;

import nts.arc.time.calendar.period.DatePeriod;

import java.util.List;

/**
 * 時間のチェック条件
 */
public class CondTime implements  ScheduleCheckCond{
    // 連続時間のチェック条件
    // CheckCond

    // チェック種類
    private CheckTimeType checkTimeType;

    // 勤務種類コード
    private List<String> wrkTypeCds;
}
