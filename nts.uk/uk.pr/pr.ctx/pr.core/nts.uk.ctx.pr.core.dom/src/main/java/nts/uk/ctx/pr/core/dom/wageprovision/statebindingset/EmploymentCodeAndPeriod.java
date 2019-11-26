package nts.uk.ctx.pr.core.dom.wageprovision.statebindingset;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EmploymentCodeAndPeriod {
    private String historyID;
    private DatePeriod datePeriod;
    private String employmentCode;
}
