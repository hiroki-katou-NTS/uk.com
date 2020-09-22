package nts.uk.ctx.at.record.app.command.approver36agrbycompany.screen_d;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.calendar.period.DatePeriod;

@Getter
@Setter
public class CompanyApproverHistoryDeleteDateCommand {
    /**
     * 会社ID
     */
    private String companyId;

    /**
     * 期間
     */
    private DatePeriod period;


}
