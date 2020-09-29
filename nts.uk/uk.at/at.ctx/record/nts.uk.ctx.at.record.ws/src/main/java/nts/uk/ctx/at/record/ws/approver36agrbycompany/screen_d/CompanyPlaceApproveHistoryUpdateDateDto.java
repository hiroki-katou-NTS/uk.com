package nts.uk.ctx.at.record.ws.approver36agrbycompany.screen_d;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;

@Getter
@Setter
public class CompanyPlaceApproveHistoryUpdateDateDto {
    /**
     * 職場ID
     */
    private String companyId;

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
