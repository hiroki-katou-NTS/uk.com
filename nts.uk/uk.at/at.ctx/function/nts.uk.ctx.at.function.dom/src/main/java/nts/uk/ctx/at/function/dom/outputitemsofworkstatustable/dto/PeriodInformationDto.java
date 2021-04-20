package nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.calendar.period.DatePeriod;
import nts.arc.time.calendar.period.YearMonthPeriod;


@AllArgsConstructor
@Getter
public class PeriodInformationDto {
    private DatePeriod datePeriod;

    private YearMonthPeriod yearMonthPeriod;
}
