package nts.uk.ctx.at.shared.dom.employmentrules.organizationmanagement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.time.calendar.period.DatePeriod;

@Getter
@Setter
@AllArgsConstructor
public class LeavePeriod {
    private DatePeriod datePeriod;
    private String sid;
}

