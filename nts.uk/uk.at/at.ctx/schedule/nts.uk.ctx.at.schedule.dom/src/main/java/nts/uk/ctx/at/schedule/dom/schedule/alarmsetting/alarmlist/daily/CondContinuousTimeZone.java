package nts.uk.ctx.at.schedule.dom.schedule.alarmsetting.alarmlist.daily;

import lombok.AllArgsConstructor;
import nts.uk.ctx.at.shared.dom.alarmList.primitivevalue.ContinuousPeriod;

import java.util.List;

/**
 * 連続時間帯の抽出条件
 */
@AllArgsConstructor
public class CondContinuousTimeZone implements  ScheduleCheckCond{
    // 対象とする就業時間帯
    TimeZoneTargetRange targetWrkHrs;
    // 勤務種類コード
    private List<String> wrkTypeCds;

    // 就業時間帯コード
    private List<String> wrkTimeCds;

    // 連続期間
    private ContinuousPeriod period;
}
