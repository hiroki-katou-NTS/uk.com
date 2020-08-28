package nts.uk.ctx.at.shared.dom.employmentrules.organizationmanagement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.time.calendar.period.DatePeriod;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class LeaveHolidayPeriod {

    public DatePeriod datePeriod;

    public String sid;

    public int frameNo;
}
