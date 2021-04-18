package nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.daily;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.ContinuousPeriod;

import java.util.List;

/**
 * 連続時間帯の抽出条件
 */
@AllArgsConstructor
@Getter
public class CondContinuousTimeZone implements  ScheduleCheckCond{

    // 勤務種類コード
    private List<String> wrkTypeCds;

    // 就業時間帯コード
    private List<String> wrkTimeCds;

    // 連続期間
    private ContinuousPeriod period;
}
