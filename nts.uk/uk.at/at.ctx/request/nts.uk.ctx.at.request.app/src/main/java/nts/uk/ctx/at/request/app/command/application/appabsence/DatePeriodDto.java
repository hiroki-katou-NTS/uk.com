package nts.uk.ctx.at.request.app.command.application.appabsence;

import lombok.Builder;
import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;

/**
 * The Class DatePeriodDto.
 */
@Data
@Builder
public class DatePeriodDto {
    
    private final String DATE_FORMAT = "yyyy/MM/dd";

    /** The start date. */
    private String startDate;

    /** The end date. */
    private String endDate;
    
    public DatePeriod toDomain() {
        return new DatePeriod(GeneralDate.fromString(startDate, DATE_FORMAT), GeneralDate.fromString(endDate, DATE_FORMAT));
    }
}
