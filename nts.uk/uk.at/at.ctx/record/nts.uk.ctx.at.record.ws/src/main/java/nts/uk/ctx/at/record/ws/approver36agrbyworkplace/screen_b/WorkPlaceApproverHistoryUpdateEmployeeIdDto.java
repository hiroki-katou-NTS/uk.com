package nts.uk.ctx.at.record.ws.approver36agrbyworkplace.screen_b;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;

import java.util.List;
@Getter
@Setter
public class WorkPlaceApproverHistoryUpdateEmployeeIdDto {
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
     * 承認者リスト
     */
    private List<String> approvedList;
    /**
     * 確認者リスト
     */
    private List<String> confirmedList;
    /**
     * 更新前の履歴開始日
     */
    private GeneralDate startDateBeforeChange;
}
