package nts.uk.ctx.at.record.app.command.approver36agrbycompany.screen_d;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;

@Getter
@Setter
@AllArgsConstructor
public class CompanyApproverHistoryDeleteDateCommand {
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
