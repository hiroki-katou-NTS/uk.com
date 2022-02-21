package nts.uk.screen.at.app.query.kdl.kdl016.dto;

import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;

import java.util.List;

@Data
public class SupportInfoInput {
    private TargetOrgInfoDto targetOrg;
    private List<String> employeeIds;
    private String startDate;
    private String endDate;
    private int displayMode;

    public DatePeriod getPeriod() {
        return new DatePeriod(GeneralDate.fromString(this.startDate, "yyyy/MM/dd"), GeneralDate.fromString(this.endDate, "yyyy/MM/dd"));
    }
}
