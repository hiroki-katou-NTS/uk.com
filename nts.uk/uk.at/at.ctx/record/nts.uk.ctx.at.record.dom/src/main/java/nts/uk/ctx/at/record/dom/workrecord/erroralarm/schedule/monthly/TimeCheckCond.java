package nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.monthly;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 時間チェック条件
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class TimeCheckCond implements ScheduleMonCheckCond {
    // チェックする時間
    private TypeOfTime typeOfTime;
}
