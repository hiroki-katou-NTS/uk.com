package nts.uk.ctx.at.shared.app.find.workrule.weekmanage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.calendar.DayOfWeek;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class WeekRuleManagementDto {
    /** 週開始 */
    private int dayOfWeek;

    /** 週割増時間を締め開始日から計算する */
    private boolean calcWeekPremFromClosureStart;
}
