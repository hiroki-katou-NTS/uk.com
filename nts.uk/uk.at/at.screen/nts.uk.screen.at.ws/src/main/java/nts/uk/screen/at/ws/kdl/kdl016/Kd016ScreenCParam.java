package nts.uk.screen.at.ws.kdl.kdl016;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;

@Data
@AllArgsConstructor
public class Kd016ScreenCParam {
    private String orgId;
    private int orgUnit;
    private String periodStart;
    private String periodEnd;

    public DatePeriod getPeriod() {
        return new DatePeriod(GeneralDate.fromString(this.periodStart, "yyyy/MM/dd"), GeneralDate.fromString(this.periodEnd, "yyyy/MM/dd"));
    }
}
