package nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.daily;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.ContinuousPeriod;

import java.util.List;

/**
 * 連続勤務種類の抽出条件
 */
@AllArgsConstructor
@Getter
public class CondContinuousWrkType  implements  ScheduleCheckCond{
    // 勤務種類コード
    private List<String> wrkTypeCds;

    // 連続期間
    private ContinuousPeriod period;
}
