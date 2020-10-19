package nts.uk.ctx.at.function.dom.outputitemsofworkstatustable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.calendar.period.DatePeriod;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class StatusOfEmployee {
    private String employeeId;

    private List<DatePeriod> listPeriod;
}
