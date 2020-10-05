package nts.uk.ctx.at.record.ws.approver36agrbyworkplace.screen_b;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;

import java.util.List;
@Getter
@Setter
public class WorkPlaceApproveHistoryAddEmployeeIdDto {
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
    private List<String> approveList;
    /**
     * 確認者リスト
     */
    private List<String> confirmedList;
}
