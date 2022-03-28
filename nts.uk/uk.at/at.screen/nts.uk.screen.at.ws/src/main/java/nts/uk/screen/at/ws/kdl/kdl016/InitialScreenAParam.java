package nts.uk.screen.at.ws.kdl.kdl016;

import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;

import java.util.List;

@Data
public class InitialScreenAParam {
    private List<String> employeeIds;

    private String periodStart;

    private String periodEnd;

    private DatePeriod period;

    public DatePeriod getPeriod() {
        return new DatePeriod(GeneralDate.fromString(this.periodStart, "yyyy/MM/dd"), GeneralDate.fromString(this.periodEnd, "yyyy/MM/dd"));
    }
}
