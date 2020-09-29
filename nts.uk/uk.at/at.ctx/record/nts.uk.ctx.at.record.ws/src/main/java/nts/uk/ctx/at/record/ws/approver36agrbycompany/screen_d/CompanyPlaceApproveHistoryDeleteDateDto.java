package nts.uk.ctx.at.record.ws.approver36agrbycompany.screen_d;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;

@Getter
@Setter
@AllArgsConstructor
public class CompanyPlaceApproveHistoryDeleteDateDto {
    /**
     * 職場ID
     */
    private String companyId;

    /**
     * 期間
     */

    private GeneralDate startDate;

    private GeneralDate endDate;

}
