package nts.uk.screen.com.app.command.cas012;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;


@Data
@NoArgsConstructor
public class Cas012AddOrUpdateCommand {

    private String uId;

    private DatePeriod validPeriod;

    public Cas012AddOrUpdateCommand(String uId, GeneralDate startDate, GeneralDate endDate) {
        this.uId = uId;
        this.validPeriod = new DatePeriod(startDate, endDate);
    }
}
