package nts.uk.ctx.pr.core.app.command.wageprovision.individualwagecontract;

import lombok.Value;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.core.dom.wageprovision.individualwagecontract.GenericHistYMPeriod;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

@Value
public class GenericHistYMPeriodCommand {
    public String historyId;
    public Integer startMonth;
    public Integer endMonth;
    public static GenericHistYMPeriod fromCommandToDomain (GenericHistYMPeriodCommand command){
        return new GenericHistYMPeriod(command.getHistoryId(), new YearMonthPeriod(new YearMonth(command.getStartMonth()), new YearMonth(command.getEndMonth())));
    }
}
