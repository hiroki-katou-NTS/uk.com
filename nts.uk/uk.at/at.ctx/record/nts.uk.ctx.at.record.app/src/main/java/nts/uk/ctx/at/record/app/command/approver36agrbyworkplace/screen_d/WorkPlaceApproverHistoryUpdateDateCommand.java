package nts.uk.ctx.at.record.app.command.approver36agrbyworkplace.screen_d;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;

@Setter
@Getter
public class WorkPlaceApproverHistoryUpdateDateCommand {
    /**
     * 職場ID
     */
    private String workPlaceId;

    /**
     * 期間
     */

    private DatePeriod period;

    /**
     * 更新前の履歴開始日
     */
    private GeneralDate startDateBeforeChange;
}
