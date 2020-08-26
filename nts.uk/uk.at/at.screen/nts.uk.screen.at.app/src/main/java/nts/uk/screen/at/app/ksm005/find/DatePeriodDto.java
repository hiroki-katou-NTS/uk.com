package nts.uk.screen.at.app.ksm005.find;

import lombok.Builder;
import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;

@Data
@Builder
public class DatePeriodDto {
    private String startDate;

    private String endDate;

    public DatePeriod convertToDate(String format){
        return new DatePeriod(GeneralDate.fromString(this.startDate,format), GeneralDate.fromString(this.endDate,format));
    }
}
