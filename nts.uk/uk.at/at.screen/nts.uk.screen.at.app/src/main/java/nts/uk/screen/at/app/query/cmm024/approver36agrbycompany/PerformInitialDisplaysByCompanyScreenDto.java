package nts.uk.screen.at.app.query.cmm024.approver36agrbycompany;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.adapter.personempbasic.PersonEmpBasicInfoDto;
import nts.uk.ctx.at.record.dom.monthly.agreement.approver.Approver36AgrByCompany;

import java.util.List;
@AllArgsConstructor
@Getter
@Setter
public class PerformInitialDisplaysByCompanyScreenDto {
    /**
     * 会社ID
     */
    private String companyId;

    private String companyName;

    private List<PerformInitialDetail> scheduleHistory;


}
