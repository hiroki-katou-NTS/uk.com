package nts.uk.ctx.at.record.app.command.approver36agrbyworkplace.screen_d;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;

@AllArgsConstructor
@Getter
@Setter
public class WorkPlaceApproverHistoryDeleteDateCommand  {
    /**
     * 職場ID
     */
    private String workPlaceId;

    /**
     * 期間
     */

    private GeneralDate startDate;

    private GeneralDate endDate;
}
