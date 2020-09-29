package nts.uk.ctx.at.record.ws.approver36agrbyworkplace.screen_d;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;

import javax.ejb.Stateless;
import java.util.List;

@Getter
@Setter
public class WorkPlaceApproveHistoryUpdateDateDto {
    /**
     * 職場ID
     */
    private String workPlaceId;

    /**
     * 期間
     */

    private GeneralDate startDate;

    private GeneralDate endDate;

    /**
     * 更新前の履歴開始日
     */
    private GeneralDate startDateBeforeChange;
}
