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
}
