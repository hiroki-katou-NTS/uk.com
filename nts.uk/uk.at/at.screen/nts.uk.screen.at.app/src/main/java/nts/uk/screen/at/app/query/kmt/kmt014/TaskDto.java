package nts.uk.screen.at.app.query.kmt.kmt014;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;

@Data
@AllArgsConstructor
public class TaskDto {
    private String taskCode;
    private String taskName;
    private GeneralDate startDate;
    private GeneralDate endDate;
}
