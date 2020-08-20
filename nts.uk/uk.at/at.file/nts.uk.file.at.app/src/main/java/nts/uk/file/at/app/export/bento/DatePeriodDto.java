package nts.uk.file.at.app.export.bento;

import lombok.Builder;
import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;

@Data
@Builder
public class DatePeriodDto {
    private String start;

    private String end;

    public DatePeriod convertToDate(String format){
        return new DatePeriod(GeneralDate.fromString(this.start,format), GeneralDate.fromString(this.end,format));
    }
}
