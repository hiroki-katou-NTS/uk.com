package nts.uk.ctx.at.record.app.command.approver36agrbyworkplace.screen_b;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class WorkPlaceApproverHistoryAddEmployeeIdCommand {
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
}
