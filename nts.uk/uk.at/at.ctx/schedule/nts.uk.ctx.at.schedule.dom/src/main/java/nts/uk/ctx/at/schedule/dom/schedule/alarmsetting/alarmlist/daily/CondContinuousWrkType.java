package nts.uk.ctx.at.schedule.dom.schedule.alarmsetting.alarmlist.daily;

import lombok.AllArgsConstructor;
import nts.uk.ctx.at.shared.dom.alarmList.primitivevalue.ContinuousPeriod;

import java.util.List;

/**
 * 連続勤務種類の抽出条件
 */
@AllArgsConstructor
public class CondContinuousWrkType  implements  ScheduleCheckCond{
    // 勤務種類コード
    private List<String> wrkTypeCds;

    // 連続期間
    private ContinuousPeriod period;
}
